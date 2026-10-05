package com.example.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkRemove
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.GitaData
import com.example.model.Bookmark
import com.example.model.Verse
import com.example.ui.GitaViewModel
import com.example.ui.components.GitaTopAppBar
import com.example.ui.components.SacredOmIcon
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.SaffronPrimary

@Composable
fun FavoritesScreen(
    viewModel: GitaViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val bookmarks by viewModel.bookmarks.collectAsState()
    val isPlaying by viewModel.audioPlayer.isPlaying.collectAsState()
    val playingVerseId by viewModel.audioPlayer.currentVerseId.collectAsState()
    val readVerseIds by viewModel.readVerseIds.collectAsState()

    var filterNotesOnly by remember { mutableStateOf(false) }

    // Dialog state for editing a note
    var editingBookmark by remember { mutableStateOf<Bookmark?>(null) }
    var noteText by remember { mutableStateOf("") }

    val displayedBookmarks = if (filterNotesOnly) {
        bookmarks.filter { it.note.isNotBlank() }
    } else {
        bookmarks
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("favorites_screen")
    ) {
        GitaTopAppBar(
            title = "Saved Verses & Notes",
            subtitle = "${bookmarks.size} Bookmarked Shlokas"
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            FilterChip(
                selected = !filterNotesOnly,
                onClick = { filterNotesOnly = false },
                label = { Text("All Saved (${bookmarks.size})") },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = SaffronPrimary,
                    selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                ),
                modifier = Modifier.testTag("filter_all_saved")
            )

            FilterChip(
                selected = filterNotesOnly,
                onClick = { filterNotesOnly = true },
                label = { Text("With Notes (${bookmarks.count { it.note.isNotBlank() }})") },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = SaffronPrimary,
                    selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                ),
                modifier = Modifier.testTag("filter_with_notes")
            )
        }

        if (displayedBookmarks.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    SacredOmIcon(size = 54, color = GoldAccent)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = if (filterNotesOnly) "No Notes Yet" else "No Bookmarked Verses",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = if (filterNotesOnly) {
                            "Add personal reflections to your favorite verses to see them organized here."
                        } else {
                            "Tap the bookmark icon on any verse while reading to save it to your sacred collection."
                        },
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
                contentPadding = PaddingValues(top = 8.dp, bottom = 96.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(displayedBookmarks, key = { it.verseId }) { bookmark ->
                    val verse = GitaData.getVerse(bookmark.verseId)
                    if (verse != null) {
                        val isThisPlaying = isPlaying && playingVerseId == verse.id
                        val isRead = readVerseIds.contains(verse.id)

                        FavoriteVerseCard(
                            verse = verse,
                            bookmark = bookmark,
                            isPlaying = isThisPlaying,
                            isRead = isRead,
                            onToggleRead = { viewModel.toggleVerseRead(verse.id) },
                            onCardClick = { viewModel.openVerseDetail(verse.id) },
                            onPlayClick = {
                                if (isThisPlaying) {
                                    viewModel.stopRecitation()
                                } else {
                                    viewModel.playVerseRecitation(verse)
                                }
                            },
                            onEditNoteClick = {
                                editingBookmark = bookmark
                                noteText = bookmark.note
                            },
                            onRemoveClick = { viewModel.removeBookmark(verse.id) },
                            onShareClick = { viewModel.openShareCard(verse.id) }
                        )
                    }
                }
            }
        }
    }

    // Edit Note Dialog
    if (editingBookmark != null) {
        AlertDialog(
            onDismissRequest = { editingBookmark = null },
            title = { Text("Personal Reflection") },
            text = {
                Column {
                    Text(
                        text = "Verse ${editingBookmark?.verseId}:",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = SaffronPrimary
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = noteText,
                        onValueChange = { noteText = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(130.dp)
                            .testTag("dialog_note_input"),
                        placeholder = { Text("Enter your spiritual takeaways, thoughts, or reflections...") }
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val b = editingBookmark
                        if (b != null) {
                            viewModel.saveNote(b.verseId, noteText)
                        }
                        editingBookmark = null
                    }
                ) {
                    Text("Save")
                }
            },
            dismissButton = {
                TextButton(onClick = { editingBookmark = null }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun FavoriteVerseCard(
    verse: Verse,
    bookmark: Bookmark,
    isPlaying: Boolean,
    isRead: Boolean = false,
    onToggleRead: (() -> Unit)? = null,
    onCardClick: () -> Unit,
    onPlayClick: () -> Unit,
    onEditNoteClick: () -> Unit,
    onRemoveClick: () -> Unit,
    onShareClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCardClick() }
            .testTag("fav_card_${verse.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "Verse ${verse.id} • Ch ${verse.chapterId}",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        ),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (onToggleRead != null) {
                        IconButton(
                            onClick = onToggleRead,
                            modifier = Modifier
                                .size(34.dp)
                                .testTag("toggle_read_fav_${verse.id}")
                        ) {
                            Icon(
                                imageVector = if (isRead) Icons.Filled.CheckCircle else Icons.Outlined.CheckCircle,
                                contentDescription = if (isRead) "Mark unread" else "Mark studied",
                                tint = if (isRead) SaffronPrimary else MaterialTheme.colorScheme.outlineVariant
                            )
                        }
                    }

                    IconButton(
                        onClick = onPlayClick,
                        modifier = Modifier.size(34.dp)
                    ) {
                        Icon(
                            imageVector = if (isPlaying) Icons.Default.Stop else Icons.Default.PlayArrow,
                            contentDescription = if (isPlaying) "Stop" else "Listen",
                            tint = if (isPlaying) MaterialTheme.colorScheme.error else SaffronPrimary
                        )
                    }
                    IconButton(
                        onClick = onEditNoteClick,
                        modifier = Modifier.size(34.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.EditNote,
                            contentDescription = "Edit Note",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    IconButton(
                        onClick = onShareClick,
                        modifier = Modifier.size(34.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Share",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    IconButton(
                        onClick = onRemoveClick,
                        modifier = Modifier.size(34.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Remove Bookmark",
                            tint = MaterialTheme.colorScheme.error.copy(alpha = 0.8f)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Sanskrit preview
            Text(
                text = verse.textSanskrit,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(6.dp))

            // English preview
            Text(
                text = "\"${verse.textEnglish}\"",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontStyle = FontStyle.Italic,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            // Personal Note pill if present
            if (bookmark.note.isNotBlank()) {
                Spacer(modifier = Modifier.height(10.dp))
                Surface(
                    color = GoldAccent.copy(alpha = 0.12f),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.EditNote,
                            contentDescription = null,
                            tint = SaffronPrimary,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = bookmark.note,
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontStyle = FontStyle.Italic,
                                color = MaterialTheme.colorScheme.onSurface
                            ),
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }
}
