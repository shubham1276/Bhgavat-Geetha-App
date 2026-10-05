package com.example.audio

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import com.example.model.Verse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.util.Locale
import kotlin.math.PI
import kotlin.math.sin

class GitaAudioPlayer(context: Context) : TextToSpeech.OnInitListener {

    private var tts: TextToSpeech? = TextToSpeech(context.applicationContext, this)
    private var isTtsInitialized = false

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()

    private val _currentVerseId = MutableStateFlow<String?>(null)
    val currentVerseId: StateFlow<String?> = _currentVerseId.asStateFlow()

    private val _repeatRemaining = MutableStateFlow(1)
    val repeatRemaining: StateFlow<Int> = _repeatRemaining.asStateFlow()

    private val _isAmbientDroneActive = MutableStateFlow(false)
    val isAmbientDroneActive: StateFlow<Boolean> = _isAmbientDroneActive.asStateFlow()

    private var currentVerse: Verse? = null
    private var droneJob: Job? = null
    private var audioTrack: AudioTrack? = null
    private val scope = CoroutineScope(Dispatchers.Default)

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            isTtsInitialized = true
            // Attempt Sanskrit or Hindi locale, fallback to English
            val sanskritLocale = Locale("sa", "IN")
            val hindiLocale = Locale("hi", "IN")
            val result = tts?.setLanguage(sanskritLocale)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                val hindiResult = tts?.setLanguage(hindiLocale)
                if (hindiResult == TextToSpeech.LANG_MISSING_DATA || hindiResult == TextToSpeech.LANG_NOT_SUPPORTED) {
                    tts?.language = Locale.ENGLISH
                }
            }

            tts?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                override fun onStart(utteranceId: String?) {
                    _isPlaying.value = true
                }

                override fun onDone(utteranceId: String?) {
                    val remaining = _repeatRemaining.value - 1
                    if (remaining > 0 && currentVerse != null) {
                        _repeatRemaining.value = remaining
                        speakVerse(currentVerse!!)
                    } else {
                        _isPlaying.value = false
                        _repeatRemaining.value = 1
                    }
                }

                @Deprecated("Deprecated in Java")
                override fun onError(utteranceId: String?) {
                    _isPlaying.value = false
                }
            })
        }
    }

    fun playVerse(
        verse: Verse,
        repeatCount: Int = 1,
        speed: Float = 0.85f,
        pitch: Float = 1.0f,
        reciteEnglish: Boolean = false
    ) {
        currentVerse = verse
        _currentVerseId.value = verse.id
        _repeatRemaining.value = repeatCount
        tts?.setSpeechRate(speed)
        tts?.setPitch(pitch)
        speakVerse(verse, reciteEnglish)
    }

    private fun speakVerse(verse: Verse, reciteEnglish: Boolean = false) {
        if (!isTtsInitialized) return
        val textToSpeak = if (reciteEnglish) {
            "Chapter ${verse.chapterId}, Verse ${verse.verseNumber}. ${verse.textEnglish}"
        } else {
            // Clean danda and line breaks for smooth chanting
            val cleanSanskrit = verse.textSanskrit
                .replace("।", " , ")
                .replace("॥", " . ")
            cleanSanskrit
        }
        val utteranceId = "verse_${verse.id}_${System.currentTimeMillis()}"
        tts?.speak(textToSpeak, TextToSpeech.QUEUE_FLUSH, null, utteranceId)
    }

    fun pauseOrStop() {
        tts?.stop()
        _isPlaying.value = false
        _repeatRemaining.value = 1
    }

    fun toggleAmbientDrone() {
        if (_isAmbientDroneActive.value) {
            stopAmbientDrone()
        } else {
            startAmbientDrone()
        }
    }

    fun startAmbientDrone() {
        if (_isAmbientDroneActive.value) return
        _isAmbientDroneActive.value = true
        droneJob = scope.launch {
            try {
                val sampleRate = 44100
                val bufferSize = AudioTrack.getMinBufferSize(
                    sampleRate,
                    AudioFormat.CHANNEL_OUT_MONO,
                    AudioFormat.ENCODING_PCM_16BIT
                )
                audioTrack = AudioTrack.Builder()
                    .setAudioAttributes(
                        AudioAttributes.Builder()
                            .setUsage(AudioAttributes.USAGE_MEDIA)
                            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                            .build()
                    )
                    .setAudioFormat(
                        AudioFormat.Builder()
                            .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                            .setSampleRate(sampleRate)
                            .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                            .build()
                    )
                    .setBufferSizeInBytes(bufferSize)
                    .build()

                audioTrack?.play()

                // Generate harmonious meditative tanpura drone (136.1 Hz Sacred Om fundamental + 204.15 Hz Pa fifth)
                val fundamentalFreq = 136.1
                val fifthFreq = 204.15
                val buffer = ShortArray(bufferSize)
                var phase1 = 0.0
                var phase2 = 0.0
                val inc1 = 2.0 * PI * fundamentalFreq / sampleRate
                val inc2 = 2.0 * PI * fifthFreq / sampleRate

                while (isActive && _isAmbientDroneActive.value) {
                    for (i in buffer.indices) {
                        val sample1 = sin(phase1)
                        val sample2 = 0.5 * sin(phase2)
                        val mixed = (sample1 + sample2) * 0.25 // Gentle warm meditation volume
                        buffer[i] = (mixed * Short.MAX_VALUE).toInt().toShort()

                        phase1 += inc1
                        if (phase1 > 2.0 * PI) phase1 -= 2.0 * PI
                        phase2 += inc2
                        if (phase2 > 2.0 * PI) phase2 -= 2.0 * PI
                    }
                    audioTrack?.write(buffer, 0, buffer.size)
                }
            } catch (_: Exception) {
            } finally {
                try {
                    audioTrack?.stop()
                    audioTrack?.release()
                } catch (_: Exception) {}
                audioTrack = null
            }
        }
    }

    fun stopAmbientDrone() {
        _isAmbientDroneActive.value = false
        droneJob?.cancel()
        droneJob = null
        try {
            audioTrack?.stop()
            audioTrack?.release()
        } catch (_: Exception) {}
        audioTrack = null
    }

    fun release() {
        pauseOrStop()
        stopAmbientDrone()
        tts?.shutdown()
        tts = null
    }
}
