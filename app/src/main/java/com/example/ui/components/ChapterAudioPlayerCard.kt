package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FastForward
import androidx.compose.material.icons.filled.FastRewind
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audio.ChapterPlaybackStatus
import com.example.model.Chapter
import com.example.ui.GitaViewModel
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.SaffronPrimary

@Composable
fun ChapterAudioPlayerCard(
    chapter: Chapter,
    viewModel: GitaViewModel,
    modifier: Modifier = Modifier
) {
    val player = viewModel.chapterAudioPlayer
    val currentChapterId by player.currentChapterId.collectAsState()
    val playbackStatus by player.playbackStatus.collectAsState()
    val isPlaying by player.isPlaying.collectAsState()
    val positionMs by player.currentPositionMs.collectAsState()
    val durationMs by player.durationMs.collectAsState()
    val speed by player.playbackSpeed.collectAsState()
    val isRepeat by player.isRepeatMode.collectAsState()
    val errorMessage by player.errorMessage.collectAsState()

    val isThisChapterActive = currentChapterId == chapter.id
    val isThisChapterPlaying = isThisChapterActive && isPlaying

    var isUserSeeking by remember { mutableStateOf(false) }
    var seekPosition by remember { mutableFloatStateOf(0f) }
    var showSpeedMenu by remember { mutableStateOf(false) }

    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 0.94f,
        targetValue = 1.06f,
        animationSpec = infiniteRepeatable(
            animation = tween(900, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseScale"
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.2.dp,
                brush = Brush.horizontalGradient(
                    listOf(
                        SaffronPrimary.copy(alpha = 0.6f),
                        GoldAccent.copy(alpha = 0.7f),
                        SaffronPrimary.copy(alpha = 0.4f)
                    )
                ),
                shape = RoundedCornerShape(18.dp)
            )
            .testTag("chapter_media3_player_card"),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            // Header Row: Sanskrit Title & Media3 Badge
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(SaffronPrimary.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.GraphicEq,
                            contentDescription = null,
                            tint = SaffronPrimary,
                            modifier = Modifier
                                .size(20.dp)
                                .then(if (isThisChapterPlaying) Modifier.scale(pulseScale) else Modifier)
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Column {
                        Text(
                            text = "अध्याय ${chapter.id} संस्कृत पारायणम्",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = SaffronPrimary
                            )
                        )
                        Text(
                            text = "Chapter ${chapter.id} Sanskrit Recitation",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                    }
                }

                Surface(
                    color = GoldAccent.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "Media3 Audio",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = SaffronPrimary
                        ),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Subtitle & Chanting Details
            Text(
                text = "${chapter.nameSanskrit} (${chapter.nameTransliteration}) • Traditional Vedic Recitation (${chapter.versesCount} Shlokas)",
                style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )

            // Status message / Buffering indicator
            if (isThisChapterActive && playbackStatus == ChapterPlaybackStatus.BUFFERING) {
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(14.dp),
                        strokeWidth = 2.dp,
                        color = SaffronPrimary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Streaming chapter recitation...",
                        style = MaterialTheme.typography.labelSmall.copy(color = SaffronPrimary)
                    )
                }
            }

            // Error display with retry
            AnimatedVisibility(visible = isThisChapterActive && playbackStatus == ChapterPlaybackStatus.ERROR && errorMessage != null) {
                Column(modifier = Modifier.padding(vertical = 6.dp)) {
                    Text(
                        text = errorMessage ?: "Audio playback error",
                        style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.error)
                    )
                    TextButton(
                        onClick = { viewModel.playChapterAudio(chapter.id) },
                        colors = ButtonDefaults.textButtonColors(contentColor = SaffronPrimary)
                    ) {
                        Text("Retry Recitation Stream")
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Progress Slider
            val maxDuration = if (durationMs > 0L && isThisChapterActive) durationMs.toFloat() else 1f
            val currentPos = if (isUserSeeking) seekPosition else if (isThisChapterActive) positionMs.toFloat() else 0f
            val clampedSliderValue = (currentPos / maxDuration).coerceIn(0f, 1f)

            Slider(
                value = clampedSliderValue,
                onValueChange = { ratio ->
                    isUserSeeking = true
                    seekPosition = ratio * maxDuration
                },
                onValueChangeFinished = {
                    isUserSeeking = false
                    viewModel.seekChapterAudio(seekPosition.toLong())
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("chapter_audio_slider"),
                colors = SliderDefaults.colors(
                    thumbColor = SaffronPrimary,
                    activeTrackColor = SaffronPrimary,
                    inactiveTrackColor = MaterialTheme.colorScheme.surfaceVariant
                )
            )

            // Time stamps Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                val displayPosition = if (isThisChapterActive) {
                    if (isUserSeeking) seekPosition.toLong() else positionMs
                } else 0L

                val displayDuration = if (isThisChapterActive && durationMs > 0L) durationMs else 0L

                Text(
                    text = formatTimeMs(displayPosition),
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )

                Text(
                    text = if (displayDuration > 0L) formatTimeMs(displayDuration) else "--:--",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Audio Player Controls Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Repeat Mode Button
                IconButton(
                    onClick = { viewModel.toggleChapterAudioRepeat() },
                    modifier = Modifier.testTag("chapter_audio_repeat_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Repeat,
                        contentDescription = if (isRepeat) "Repeat Chapter On" else "Repeat Off",
                        tint = if (isRepeat) SaffronPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                // 10s Rewind
                IconButton(
                    onClick = { viewModel.seekChapterAudioBack() },
                    modifier = Modifier.testTag("chapter_audio_rewind_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.FastRewind,
                        contentDescription = "Rewind 10 seconds",
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.size(28.dp)
                    )
                }

                // Play / Pause Primary Button
                Surface(
                    modifier = Modifier
                        .size(54.dp)
                        .clip(CircleShape)
                        .testTag("chapter_audio_play_pause_button"),
                    color = SaffronPrimary,
                    shadowElevation = 4.dp
                ) {
                    IconButton(
                        onClick = { viewModel.toggleChapterAudio(chapter.id) }
                    ) {
                        Icon(
                            imageVector = if (isThisChapterPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                            contentDescription = if (isThisChapterPlaying) "Pause Sanskrit Recitation" else "Play Sanskrit Recitation",
                            tint = Color.White,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }

                // 10s Fast Forward
                IconButton(
                    onClick = { viewModel.seekChapterAudioForward() },
                    modifier = Modifier.testTag("chapter_audio_forward_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.FastForward,
                        contentDescription = "Forward 10 seconds",
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.size(28.dp)
                    )
                }

                // Playback Speed Button with Dropdown
                Box {
                    Surface(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.testTag("chapter_audio_speed_button")
                    ) {
                        IconButton(
                            onClick = { showSpeedMenu = true },
                            modifier = Modifier.size(38.dp)
                        ) {
                            Text(
                                text = "${speed}x",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            )
                        }
                    }

                    DropdownMenu(
                        expanded = showSpeedMenu,
                        onDismissRequest = { showSpeedMenu = false }
                    ) {
                        listOf(0.75f, 1.0f, 1.25f, 1.5f).forEach { s ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = "${s}x Speed",
                                        fontWeight = if (speed == s) FontWeight.Bold else FontWeight.Normal,
                                        color = if (speed == s) SaffronPrimary else MaterialTheme.colorScheme.onSurface
                                    )
                                },
                                onClick = {
                                    viewModel.setChapterAudioSpeed(s)
                                    showSpeedMenu = false
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun formatTimeMs(millis: Long): String {
    val totalSeconds = (millis / 1000).coerceAtLeast(0L)
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return String.format("%02d:%02d", minutes, seconds)
}
