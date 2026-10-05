package com.example.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Chapter
import com.example.ui.GitaViewModel
import com.example.ui.components.ChapterProgressBar
import com.example.ui.components.GitaTopAppBar
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.SaffronPrimary

@Composable
fun ChaptersScreen(
    viewModel: GitaViewModel,
    modifier: Modifier = Modifier
) {
    val allChapters = viewModel.getAllChapters()
    val readVerseIds by viewModel.readVerseIds.collectAsState()
    val completedChapterIds by viewModel.completedChapterIds.collectAsState()

    var selectedFilter by remember { mutableStateOf("All") }
    val filterOptions = listOf("All", "In Progress", "Completed", "Karma Yoga", "Jnana Yoga", "Bhakti Yoga")

    val filteredChapters = when (selectedFilter) {
        "In Progress" -> allChapters.filter {
            val prog = viewModel.getChapterProgress(it.id)
            prog > 0f && prog < 1f
        }
        "Completed" -> allChapters.filter {
            viewModel.isChapterCompleted(it.id) || viewModel.getChapterProgress(it.id) >= 1f
        }
        "Karma Yoga" -> allChapters.filter { it.yogaType.contains("Karma", ignoreCase = true) }
        "Jnana Yoga" -> allChapters.filter { it.yogaType.contains("Jnana", ignoreCase = true) }
        "Bhakti Yoga" -> allChapters.filter { it.yogaType.contains("Bhakti", ignoreCase = true) }
        else -> allChapters
    }

    val overallProgress = viewModel.getOverallProgress()
    val totalChaptersCompleted = allChapters.count { viewModel.isChapterCompleted(it.id) || viewModel.getChapterProgress(it.id) >= 1f }

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("chapters_screen")
    ) {
        GitaTopAppBar(
            title = "18 Sacred Chapters",
            subtitle = "$totalChaptersCompleted of 18 Chapters Completed"
        )

        // Overall Reading Progress Summary Banner
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 6.dp)
                .testTag("chapters_overall_progress_card"),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            )
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Total Gita Study Progress",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = SaffronPrimary
                        )
                    )
                    Text(
                        text = "${(overallProgress * 100).toInt()}% Read",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                ChapterProgressBar(
                    progress = overallProgress,
                    showPercent = false
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "$totalChaptersCompleted / 18 chapters completed • Continuous study elevates consciousness",
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            }
        }

        // Filter chips
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(filterOptions) { filter ->
                FilterChip(
                    selected = selectedFilter == filter,
                    onClick = { selectedFilter = filter },
                    label = { Text(filter) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = SaffronPrimary,
                        selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    modifier = Modifier.testTag("filter_chip_$filter")
                )
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(top = 8.dp, bottom = 96.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(filteredChapters) { chapter ->
                val progress = viewModel.getChapterProgress(chapter.id)
                val readCount = viewModel.getChapterReadCount(chapter.id)
                val totalChapterVerses = viewModel.getVersesForChapter(chapter.id).size

                ChapterCard(
                    chapter = chapter,
                    progress = progress,
                    readCount = readCount,
                    totalCoreVerses = totalChapterVerses,
                    onClick = { viewModel.openChapterDetail(chapter.id) }
                )
            }
        }
    }
}

@Composable
fun ChapterCard(
    chapter: Chapter,
    progress: Float,
    readCount: Int,
    totalCoreVerses: Int,
    onClick: () -> Unit
) {
    val isCompleted = progress >= 1.0f

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .testTag("chapter_card_${chapter.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    color = if (isCompleted) SaffronPrimary else SaffronPrimary.copy(alpha = 0.85f),
                    shape = CircleShape,
                    modifier = Modifier.size(44.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = if (isCompleted) "✓" else "${chapter.id}",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.width(14.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = chapter.nameSanskrit,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "(${chapter.nameTransliteration})",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    Text(
                        text = chapter.nameEnglish,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = SaffronPrimary,
                            fontWeight = FontWeight.Medium
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "View Chapter",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = chapter.summary,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    lineHeight = 18.sp
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Visual Progress Indicator for Chapter
            ChapterProgressBar(
                progress = progress,
                label = if (totalCoreVerses > 0) "$readCount of $totalCoreVerses core verses read" else "Total ${chapter.versesCount} verses",
                showPercent = true
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "${chapter.versesCount} Total Verses",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        ),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }

                Surface(
                    color = if (isCompleted) SaffronPrimary.copy(alpha = 0.15f) else GoldAccent.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = if (isCompleted) "Completed ✓" else chapter.yogaType,
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = SaffronPrimary
                        ),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
        }
    }
}
