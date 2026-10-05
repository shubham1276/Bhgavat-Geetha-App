package com.example.audio

import android.content.Context
import androidx.annotation.OptIn
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.PlaybackException
import androidx.media3.common.PlaybackParameters
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import com.example.data.GitaData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

enum class ChapterPlaybackStatus {
    IDLE,
    BUFFERING,
    READY,
    PLAYING,
    PAUSED,
    ENDED,
    ERROR
}

class ChapterMedia3AudioPlayer(private val context: Context) {

    private val scope = CoroutineScope(Dispatchers.Main)
    private var progressJob: Job? = null

    private var exoPlayer: ExoPlayer? = null

    private val _currentChapterId = MutableStateFlow<Int?>(null)
    val currentChapterId: StateFlow<Int?> = _currentChapterId.asStateFlow()

    private val _playbackStatus = MutableStateFlow(ChapterPlaybackStatus.IDLE)
    val playbackStatus: StateFlow<ChapterPlaybackStatus> = _playbackStatus.asStateFlow()

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()

    private val _currentPositionMs = MutableStateFlow(0L)
    val currentPositionMs: StateFlow<Long> = _currentPositionMs.asStateFlow()

    private val _durationMs = MutableStateFlow(0L)
    val durationMs: StateFlow<Long> = _durationMs.asStateFlow()

    private val _playbackSpeed = MutableStateFlow(1.0f)
    val playbackSpeed: StateFlow<Float> = _playbackSpeed.asStateFlow()

    private val _isRepeatMode = MutableStateFlow(false)
    val isRepeatMode: StateFlow<Boolean> = _isRepeatMode.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    init {
        initPlayer()
    }

    @OptIn(UnstableApi::class)
    private fun initPlayer() {
        if (exoPlayer != null) return

        exoPlayer = ExoPlayer.Builder(context).build().apply {
            addListener(object : Player.Listener {
                override fun onPlaybackStateChanged(playbackState: Int) {
                    when (playbackState) {
                        Player.STATE_IDLE -> {
                            _playbackStatus.value = ChapterPlaybackStatus.IDLE
                            stopProgressPolling()
                        }
                        Player.STATE_BUFFERING -> {
                            _playbackStatus.value = ChapterPlaybackStatus.BUFFERING
                            _errorMessage.value = null
                        }
                        Player.STATE_READY -> {
                            val dur = duration.coerceAtLeast(0L)
                            _durationMs.value = dur
                            if (playWhenReady) {
                                _playbackStatus.value = ChapterPlaybackStatus.PLAYING
                                _isPlaying.value = true
                                startProgressPolling()
                            } else {
                                _playbackStatus.value = ChapterPlaybackStatus.READY
                                _isPlaying.value = false
                                stopProgressPolling()
                            }
                        }
                        Player.STATE_ENDED -> {
                            _playbackStatus.value = ChapterPlaybackStatus.ENDED
                            _isPlaying.value = false
                            _currentPositionMs.value = _durationMs.value
                            stopProgressPolling()
                        }
                    }
                }

                override fun onIsPlayingChanged(playing: Boolean) {
                    _isPlaying.value = playing
                    if (playing) {
                        _playbackStatus.value = ChapterPlaybackStatus.PLAYING
                        startProgressPolling()
                    } else if (_playbackStatus.value != ChapterPlaybackStatus.BUFFERING &&
                        _playbackStatus.value != ChapterPlaybackStatus.ENDED
                    ) {
                        _playbackStatus.value = ChapterPlaybackStatus.PAUSED
                        stopProgressPolling()
                    }
                }

                override fun onPlayerError(error: PlaybackException) {
                    _playbackStatus.value = ChapterPlaybackStatus.ERROR
                    _isPlaying.value = false
                    _errorMessage.value = "Streaming error: ${error.localizedMessage ?: "Unable to stream chapter recitation"}"
                    stopProgressPolling()
                }
            })
        }
    }

    fun playChapter(chapterId: Int) {
        val player = exoPlayer ?: return
        val chapter = GitaData.getChapter(chapterId) ?: return

        _currentChapterId.value = chapterId
        _errorMessage.value = null

        val audioUrl = getChapterAudioUrl(chapterId)

        val metadata = MediaMetadata.Builder()
            .setTitle("Chapter $chapterId: ${chapter.nameSanskrit}")
            .setSubtitle(chapter.nameEnglish)
            .setArtist("Bhagavad Gita Recitation")
            .build()

        val mediaItem = MediaItem.Builder()
            .setUri(audioUrl)
            .setMediaMetadata(metadata)
            .build()

        player.setMediaItem(mediaItem)
        player.repeatMode = if (_isRepeatMode.value) Player.REPEAT_MODE_ONE else Player.REPEAT_MODE_OFF
        player.setPlaybackSpeed(_playbackSpeed.value)
        player.prepare()
        player.play()
        _playbackStatus.value = ChapterPlaybackStatus.BUFFERING
        _isPlaying.value = true
        startProgressPolling()
    }

    fun togglePlayPause(chapterId: Int) {
        val player = exoPlayer ?: return
        if (_currentChapterId.value == chapterId) {
            if (player.isPlaying) {
                player.pause()
            } else {
                player.play()
            }
        } else {
            playChapter(chapterId)
        }
    }

    fun pause() {
        exoPlayer?.pause()
    }

    fun resume() {
        exoPlayer?.play()
    }

    fun seekTo(positionMs: Long) {
        exoPlayer?.seekTo(positionMs.coerceIn(0L, _durationMs.value.coerceAtLeast(0L)))
        _currentPositionMs.value = positionMs
    }

    fun seekForward(deltaMs: Long = 10000L) {
        val current = exoPlayer?.currentPosition ?: 0L
        seekTo(current + deltaMs)
    }

    fun seekBack(deltaMs: Long = 10000L) {
        val current = exoPlayer?.currentPosition ?: 0L
        seekTo((current - deltaMs).coerceAtLeast(0L))
    }

    fun setSpeed(speed: Float) {
        _playbackSpeed.value = speed
        exoPlayer?.playbackParameters = PlaybackParameters(speed)
    }

    fun toggleRepeat() {
        val newRepeat = !_isRepeatMode.value
        _isRepeatMode.value = newRepeat
        exoPlayer?.repeatMode = if (newRepeat) Player.REPEAT_MODE_ONE else Player.REPEAT_MODE_OFF
    }

    fun stop() {
        exoPlayer?.stop()
        _isPlaying.value = false
        _playbackStatus.value = ChapterPlaybackStatus.IDLE
        _currentPositionMs.value = 0L
        stopProgressPolling()
    }

    private fun startProgressPolling() {
        stopProgressPolling()
        progressJob = scope.launch {
            while (isActive) {
                exoPlayer?.let { player ->
                    _currentPositionMs.value = player.currentPosition.coerceAtLeast(0L)
                    val dur = player.duration
                    if (dur > 0L) {
                        _durationMs.value = dur
                    }
                }
                delay(250L)
            }
        }
    }

    private fun stopProgressPolling() {
        progressJob?.cancel()
        progressJob = null
    }

    fun release() {
        stopProgressPolling()
        exoPlayer?.release()
        exoPlayer = null
    }

    private fun getChapterAudioUrl(chapterId: Int): String {
        val formattedChapter = String.format("%02d", chapterId)
        // Public domain traditional Sanskrit recitation of Bhagavad Gita by chapter
        return "https://archive.org/download/Bhagavad-Gita-Chanting-Swami-Brahmananda/Chapter$formattedChapter.mp3"
    }
}
