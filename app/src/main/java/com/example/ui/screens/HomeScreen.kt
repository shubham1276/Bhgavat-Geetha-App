package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.GitaData
import com.example.ui.GitaViewModel
import com.example.ui.components.ChapterProgressBar
import com.example.ui.components.CircularProgressRing
import com.example.ui.components.SacredOmIcon
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.SaffronPrimary

@Composable
fun HomeScreen(
    viewModel: GitaViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val dailyVerse by viewModel.dailyVerse.collectAsState()
    val isPlaying by viewModel.audioPlayer.isPlaying.collectAsState()
    val playingVerseId by viewModel.audioPlayer.currentVerseId.collectAsState()
    val isDroneActive by viewModel.audioPlayer.isAmbientDroneActive.collectAsState()
    val bookmarks by viewModel.bookmarks.collectAsState()
    val settings by viewModel.settings.collectAsState()
    val readVerseIds by viewModel.readVerseIds.collectAsState()
    val completedChapterIds by viewModel.completedChapterIds.collectAsState()

    val lastReadVerse = GitaData.getVerse(settings.lastReadVerseId) ?: GitaData.keyVerses.first()
    val overallProgress = viewModel.getOverallProgress()
    val completedCount = viewModel.getAllChapters().count { viewModel.isChapterCompleted(it.id) || viewModel.getChapterProgress(it.id) >= 1f }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("home_screen_list"),
        contentPadding = PaddingValues(bottom = 96.dp)
    ) {
        // 1. Hero Banner with Sacred Art & Divine Title
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_hero_geetha),
                    contentDescription = "Lord Krishna and Arjuna at Kurukshetra",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                // Atmospheric sacred gradient
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Black.copy(alpha = 0.2f),
                                    Color.Black.copy(alpha = 0.5f),
                                    Color.Black.copy(alpha = 0.95f)
                                )
                            )
                        )
                )

                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(20.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        SacredOmIcon(size = 32, color = GoldAccent)
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "॥ श्रीमद्भगवद्गीता ॥",
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = GoldAccent,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Bhagavad Geetha Wisdom",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            color = Color.White,
                            fontWeight = FontWeight.ExtraBold
                        )
                    )

                    Text(
                        text = "Timeless counsel of Sri Krishna for modern living",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = Color.White.copy(alpha = 0.85f)
                        )
                    )
                }
            }
        }

        // 2. Visual Summary Dashboard with Circular Progress Ring & Total Verses Read
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .border(
                        width = 1.dp,
                        brush = Brush.horizontalGradient(
                            listOf(
                                SaffronPrimary.copy(alpha = 0.5f),
                                GoldAccent.copy(alpha = 0.6f),
                                SaffronPrimary.copy(alpha = 0.3f)
                            )
                        ),
                        shape = RoundedCornerShape(20.dp)
                    )
                    .testTag("home_visual_summary_dashboard"),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    // Header
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            SacredOmIcon(size = 28, color = SaffronPrimary)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Gita Study Dashboard",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            )
                        }

                        Surface(
                            color = GoldAccent.copy(alpha = 0.15f),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = "Sadhana Tracker",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = SaffronPrimary,
                                    fontWeight = FontWeight.Bold
                                ),
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Circular Progress Ring & Metrics Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val chapterCompletionRatio = (completedCount.toFloat() / 18f).coerceIn(0f, 1f)
                        val chapterPercent = (chapterCompletionRatio * 100).toInt()

                        // Circular Progress Ring for 18 Chapters
                        CircularProgressRing(
                            progress = chapterCompletionRatio,
                            size = 112.dp,
                            strokeWidth = 10.dp,
                            trackColor = MaterialTheme.colorScheme.surfaceVariant,
                            progressColors = listOf(SaffronPrimary, GoldAccent, SaffronPrimary),
                            modifier = Modifier.testTag("chapter_circular_progress_ring")
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "$completedCount/18",
                                    style = MaterialTheme.typography.titleLarge.copy(
                                        fontWeight = FontWeight.ExtraBold,
                                        color = SaffronPrimary
                                    )
                                )
                                Text(
                                    text = "Chapters",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontSize = 10.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                )
                                Text(
                                    text = if (chapterPercent >= 100) "Completed ✓" else "$chapterPercent%",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (chapterPercent >= 100) SaffronPrimary else GoldAccent
                                    )
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(18.dp))

                        // Verses Read & Milestone Details
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "VERSES READ",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 1.sp
                                )
                            )
                            Row(verticalAlignment = Alignment.Bottom) {
                                Text(
                                    text = "${readVerseIds.size}",
                                    style = MaterialTheme.typography.headlineMedium.copy(
                                        fontWeight = FontWeight.ExtraBold,
                                        color = MaterialTheme.colorScheme.primary
                                    ),
                                    modifier = Modifier.testTag("total_verses_read_text")
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "shlokas studied",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    ),
                                    modifier = Modifier.padding(bottom = 4.dp)
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            // Spiritual Stage Badge
                            val stageTitle = when {
                                completedCount >= 18 -> "Steadfast Sage (Sthitaprajna)"
                                completedCount >= 12 -> "Devoted Yogi (Bhakta)"
                                completedCount >= 6 -> "Practicing Seeker (Sadhaka)"
                                completedCount >= 1 -> "Dedicated Student (Abhyasi)"
                                else -> "New Seeker (Arambha)"
                            }

                            Surface(
                                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(
                                    text = stageTitle,
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        fontWeight = FontWeight.Medium
                                    ),
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Secondary Strip: Chapters left & navigate link
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (completedCount >= 18) "All 18 chapters completed! 🕉️" else "${18 - completedCount} chapters remaining to master",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                        Text(
                            text = "View Chapters →",
                            style = MaterialTheme.typography.labelMedium.copy(
                                color = SaffronPrimary,
                                fontWeight = FontWeight.Bold
                            ),
                            modifier = Modifier
                                .clickable { viewModel.selectTab(com.example.ui.NavigationTab.CHAPTERS) }
                                .testTag("dashboard_view_chapters_link")
                        )
                    }
                }
            }
        }

        // Quick Statistics Bar
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                StatCard(
                    title = "18",
                    subtitle = "Chapters",
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    title = "700",
                    subtitle = "Verses",
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    title = "$completedCount / 18",
                    subtitle = "Done",
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    title = "${bookmarks.size}",
                    subtitle = "Saved",
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // 3. Verse of the Day (Daily Quote)
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .border(
                        width = 1.5.dp,
                        brush = Brush.horizontalGradient(listOf(SaffronPrimary, GoldAccent)),
                        shape = RoundedCornerShape(18.dp)
                    )
                    .testTag("daily_quote_card"),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.AutoAwesome,
                                contentDescription = null,
                                tint = GoldAccent,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Verse of the Day",
                                style = MaterialTheme.typography.labelLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = SaffronPrimary
                                )
                            )
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconButton(
                                onClick = { viewModel.refreshDailyVerse() },
                                modifier = Modifier
                                    .size(32.dp)
                                    .testTag("refresh_daily_quote_button")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Refresh,
                                    contentDescription = "New Quote",
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            IconButton(
                                onClick = { viewModel.openShareCard(dailyVerse.id) },
                                modifier = Modifier
                                    .size(32.dp)
                                    .testTag("share_daily_quote_button")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Share,
                                    contentDescription = "Share",
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Chapter ${dailyVerse.chapterId}, Verse ${dailyVerse.verseNumber}",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontWeight = FontWeight.Medium
                        )
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    // Sanskrit
                    Text(
                        text = dailyVerse.textSanskrit,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurface,
                            lineHeight = 24.sp
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // English Translation
                    Text(
                        text = "\"${dailyVerse.textEnglish}\"",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontStyle = FontStyle.Italic,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            lineHeight = 22.sp
                        )
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        val isThisVersePlaying = isPlaying && playingVerseId == dailyVerse.id

                        Button(
                            onClick = {
                                if (isThisVersePlaying) {
                                    viewModel.stopRecitation()
                                } else {
                                    viewModel.playVerseRecitation(dailyVerse)
                                }
                            },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("play_daily_verse_button"),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isThisVersePlaying) MaterialTheme.colorScheme.error else SaffronPrimary
                            )
                        ) {
                            Icon(
                                imageVector = if (isThisVersePlaying) Icons.Default.Stop else Icons.Default.PlayArrow,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(if (isThisVersePlaying) "Pause" else "Recite Shloka")
                        }

                        OutlinedButton(
                            onClick = { viewModel.openVerseDetail(dailyVerse.id) },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("read_daily_verse_button")
                        ) {
                            Text("Read Detail")
                        }
                    }
                }
            }
        }

        // 4. Continue Reading Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp)
                    .clickable { viewModel.openVerseDetail(lastReadVerse.id) }
                    .testTag("continue_reading_card"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
                )
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(SaffronPrimary.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.MenuBook,
                            contentDescription = null,
                            tint = SaffronPrimary
                        )
                    }

                    Spacer(modifier = Modifier.width(14.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Continue Reading",
                            style = MaterialTheme.typography.labelMedium.copy(
                                color = SaffronPrimary,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Text(
                            text = "Verse ${lastReadVerse.id} • Chapter ${lastReadVerse.chapterId}",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Text(
                            text = lastReadVerse.textEnglish,
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Resume",
                        tint = SaffronPrimary
                    )
                }
            }
        }

        // 5. Sacred Meditative Drone Tile
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp)
                    .clickable { viewModel.toggleAmbientDrone() }
                    .testTag("meditative_drone_tile"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isDroneActive) SaffronPrimary.copy(alpha = 0.12f) else MaterialTheme.colorScheme.surface
                ),
                border = if (isDroneActive) borderStrokeGold() else null
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(if (isDroneActive) GoldAccent else MaterialTheme.colorScheme.surfaceVariant),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = if (isDroneActive) Icons.Default.GraphicEq else Icons.Default.MusicNote,
                            contentDescription = null,
                            tint = if (isDroneActive) Color.Black else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Sacred Meditative Drone (136.1 Hz Om)",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                        Text(
                            text = if (isDroneActive) "Playing soothing cosmic resonance in background..." else "Tap to enable soothing background Tanpura drone",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                    }

                    Text(
                        text = if (isDroneActive) "ON" else "OFF",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = if (isDroneActive) SaffronPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                        ),
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(if (isDroneActive) SaffronPrimary.copy(alpha = 0.15f) else MaterialTheme.colorScheme.surfaceVariant)
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
        }

        // 6. Practical Wisdom for Modern Life (Spiritual Topics)
        item {
            Column(modifier = Modifier.padding(top = 16.dp)) {
                Text(
                    text = "Guidance for Life's Challenges",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    ),
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Text(
                    text = "Timeless solutions to modern stress, doubt, and focus",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 2.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    val topics = listOf(
                        TopicGuidance("Facing Anxiety & Duty", "2.47", "Focus on the action, not outcome anxiety"),
                        TopicGuidance("Taming the Restless Mind", "6.26", "Gently bringing attention back to the Self"),
                        TopicGuidance("Overcoming Fear & Stress", "2.14", "Enduring transient changes with Titiksha"),
                        TopicGuidance("Finding Eternal Peace", "2.71", "Releasing possessiveness and ego"),
                        TopicGuidance("Divine Protection & Love", "9.22", "God preserves what you have and provides what you lack"),
                        TopicGuidance("Self-Elevation & Willpower", "6.5", "You are your own greatest friend or enemy"),
                        TopicGuidance("Ultimate Refuge", "18.66", "Abandon fear and surrender to divine grace")
                    )

                    items(topics) { topic ->
                        Card(
                            modifier = Modifier
                                .width(220.dp)
                                .clickable { viewModel.openVerseDetail(topic.verseId) }
                                .testTag("topic_${topic.verseId}"),
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surface
                            ),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Surface(
                                    color = SaffronPrimary.copy(alpha = 0.12f),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text(
                                        text = "Verse ${topic.verseId}",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = SaffronPrimary,
                                            fontWeight = FontWeight.Bold
                                        ),
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = topic.title,
                                    style = MaterialTheme.typography.titleSmall.copy(
                                        fontWeight = FontWeight.Bold
                                    ),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = topic.description,
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    ),
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }
                }
            }
        }

        // 7. Chapters Highlight
        item {
            Column(modifier = Modifier.padding(top = 22.dp, start = 16.dp, end = 16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "The 18 Sacred Chapters",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        text = "View All →",
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = SaffronPrimary,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.clickable {
                            viewModel.selectTab(com.example.ui.NavigationTab.CHAPTERS)
                        }
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Showcase first 3 chapters
                GitaData.chapters.take(3).forEach { chapter ->
                    val progress = viewModel.getChapterProgress(chapter.id)
                    ChapterMiniCard(
                        chapter = chapter,
                        progress = progress,
                        onClick = { viewModel.openChapterDetail(chapter.id) }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
fun borderStrokeGold() = androidx.compose.foundation.BorderStroke(
    1.dp,
    Brush.horizontalGradient(listOf(SaffronPrimary, GoldAccent))
)

@Composable
fun StatCard(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
    ) {
        Column(
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.ExtraBold,
                    color = SaffronPrimary
                )
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.labelSmall.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun ChapterMiniCard(
    chapter: com.example.model.Chapter,
    progress: Float = 0f,
    onClick: () -> Unit
) {
    val isCompleted = progress >= 1.0f

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .testTag("chapter_mini_${chapter.id}"),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    color = if (isCompleted) SaffronPrimary else MaterialTheme.colorScheme.primaryContainer,
                    shape = CircleShape,
                    modifier = Modifier.size(36.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = if (isCompleted) "✓" else "${chapter.id}",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = if (isCompleted) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.primary
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "${chapter.nameSanskrit} (${chapter.nameTransliteration})",
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.SemiBold
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = chapter.nameEnglish,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = if (isCompleted) "Completed ✓" else if (progress > 0f) "${(progress * 100).toInt()}%" else "${chapter.versesCount} verses",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = if (isCompleted) SaffronPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = if (isCompleted || progress > 0f) FontWeight.Bold else FontWeight.Normal
                    )
                )
            }

            if (progress > 0f) {
                Spacer(modifier = Modifier.height(8.dp))
                ChapterProgressBar(
                    progress = progress,
                    showPercent = false
                )
            }
        }
    }
}

data class TopicGuidance(
    val title: String,
    val verseId: String,
    val description: String
)
