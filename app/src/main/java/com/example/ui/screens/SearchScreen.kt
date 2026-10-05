package com.example.ui.screens

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.GitaData
import com.example.ui.GitaViewModel
import com.example.ui.components.GitaTopAppBar
import com.example.ui.components.SacredOmIcon
import com.example.ui.components.VerseItemCard
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.SaffronPrimary

@Composable
fun SearchScreen(
    viewModel: GitaViewModel,
    modifier: Modifier = Modifier
) {
    val query by viewModel.searchQuery.collectAsState()
    val selectedTag by viewModel.selectedSearchTag.collectAsState()
    val results by viewModel.searchResults.collectAsState()
    val bookmarks by viewModel.bookmarks.collectAsState()
    val isPlaying by viewModel.audioPlayer.isPlaying.collectAsState()
    val playingVerseId by viewModel.audioPlayer.currentVerseId.collectAsState()
    val readVerseIds by viewModel.readVerseIds.collectAsState()

    val topicTags = listOf(
        "Karma", "Duty", "Mind", "Peace", "Meditation",
        "Fear", "Devotion", "Surrender", "Soul", "Dharma", "Wisdom"
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("search_screen")
    ) {
        GitaTopAppBar(
            title = "Search Shlokas",
            subtitle = "Find wisdom across verses and concepts"
        )

        // Search Input Bar
        OutlinedTextField(
            value = query,
            onValueChange = { viewModel.setSearchQuery(it) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 6.dp)
                .testTag("search_text_input"),
            placeholder = { Text("Search by word, e.g. duty, peace, mind, karma...") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = SaffronPrimary
                )
            },
            trailingIcon = {
                if (query.isNotEmpty() || selectedTag != null) {
                    IconButton(
                        onClick = {
                            viewModel.setSearchQuery("")
                            viewModel.selectSearchTag(null)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Clear search"
                        )
                    }
                }
            },
            singleLine = true
        )

        // Topic chips
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(topicTags) { tag ->
                FilterChip(
                    selected = selectedTag == tag,
                    onClick = { viewModel.selectSearchTag(tag) },
                    label = { Text(tag) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = SaffronPrimary,
                        selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    modifier = Modifier.testTag("search_tag_$tag")
                )
            }
        }

        // Search Results / Suggested Verses
        val displayVerses = if (query.isNotBlank() || selectedTag != null) {
            results
        } else {
            // Show all key verses when no active query
            GitaData.keyVerses
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (query.isNotBlank() || selectedTag != null) {
                    "Found ${displayVerses.size} Verses"
                } else {
                    "All Curated Core Verses (${displayVerses.size})"
                },
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
        }

        if (displayVerses.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    SacredOmIcon(size = 48, color = GoldAccent)
                    Spacer(modifier = Modifier.height(14.dp))
                    Text(
                        text = "No Shlokas Found",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Try searching for terms like 'duty', 'mind', 'yoga', 'peace', or choose a topic tag above.",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        ),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                contentPadding = PaddingValues(top = 4.dp, bottom = 96.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(displayVerses) { verse ->
                    val isFav = bookmarks.any { it.verseId == verse.id && it.isFavorite }
                    val isThisPlaying = isPlaying && playingVerseId == verse.id
                    val isRead = readVerseIds.contains(verse.id)

                    VerseItemCard(
                        verse = verse,
                        isFavorite = isFav,
                        isPlaying = isThisPlaying,
                        isRead = isRead,
                        onToggleRead = { viewModel.toggleVerseRead(verse.id) },
                        onVerseClick = { viewModel.openVerseDetail(verse.id) },
                        onFavoriteToggle = { viewModel.toggleFavorite(verse.id) },
                        onPlayAudio = {
                            if (isThisPlaying) {
                                viewModel.stopRecitation()
                            } else {
                                viewModel.playVerseRecitation(verse)
                            }
                        }
                    )
                }
            }
        }
    }
}
