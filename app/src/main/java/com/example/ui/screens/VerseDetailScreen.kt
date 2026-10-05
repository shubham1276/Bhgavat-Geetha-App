package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.GitaViewModel
import com.example.ui.components.ChapterProgressBar
import com.example.ui.components.SacredOmIcon
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.SaffronPrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VerseDetailScreen(
    verseId: String,
    viewModel: GitaViewModel,
    modifier: Modifier = Modifier
) {
    BackHandler {
        viewModel.navigateBack()
    }

    val context = LocalContext.current
    val verse = viewModel.getVerse(verseId)
    val settings by viewModel.settings.collectAsState()
    val isPlaying by viewModel.audioPlayer.isPlaying.collectAsState()
    val playingVerseId by viewModel.audioPlayer.currentVerseId.collectAsState()
    val repeatRemaining by viewModel.audioPlayer.repeatRemaining.collectAsState()
    val isDroneActive by viewModel.audioPlayer.isAmbientDroneActive.collectAsState()
    val readVerseIds by viewModel.readVerseIds.collectAsState()

    val bookmark = viewModel.getBookmark(verseId)
    val isFavorite = bookmark?.isFavorite == true
    val isRead = readVerseIds.contains(verseId)
    val chapterProgress = viewModel.getChapterProgress(verse?.chapterId ?: 1)

    var selectedRepeatCount by remember { mutableIntStateOf(1) }
    var reciteInEnglish by remember { mutableStateOf(false) }
    var showSpeedMenu by remember { mutableStateOf(false) }
    var showRepeatMenu by remember { mutableStateOf(false) }

    // Notes editing state
    var showNotesDialog by remember { mutableStateOf(false) }
    var userNoteText by remember { mutableStateOf(bookmark?.note ?: "") }

    val nextVerse = viewModel.getNextVerse(verseId)
    val prevVerse = viewModel.getPreviousVerse(verseId)

    if (verse == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Verse not found")
        }
        return
    }

    val scale = settings.fontSize.scale
    val isThisVersePlaying = isPlaying && playingVerseId == verse.id

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Verse ${verse.id}",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            if (isRead) {
                                Spacer(modifier = Modifier.width(6.dp))
                                Surface(
                                    color = SaffronPrimary.copy(alpha = 0.15f),
                                    shape = RoundedCornerShape(6.dp)
                                ) {
                                    Text(
                                        text = "Read ✓",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = SaffronPrimary,
                                            fontWeight = FontWeight.Bold
                                        ),
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                            }
                        }
                        Text(
                            text = "Chapter ${verse.chapterId} • ${(chapterProgress * 100).toInt()}% Chapter Progress",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = { viewModel.navigateBack() },
                        modifier = Modifier.testTag("verse_detail_back_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = SaffronPrimary
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { viewModel.toggleVerseRead(verse.id) },
                        modifier = Modifier.testTag("verse_read_toggle_button")
                    ) {
                        Icon(
                            imageVector = if (isRead) Icons.Filled.CheckCircle else Icons.Outlined.CheckCircle,
                            contentDescription = if (isRead) "Mark as unread" else "Mark as studied",
                            tint = if (isRead) SaffronPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    IconButton(
                        onClick = { showNotesDialog = true },
                        modifier = Modifier.testTag("verse_note_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.EditNote,
                            contentDescription = "Add Reflection Note",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    IconButton(
                        onClick = { viewModel.toggleFavorite(verse.id) },
                        modifier = Modifier.testTag("verse_favorite_button")
                    ) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                            contentDescription = "Bookmark",
                            tint = if (isFavorite) SaffronPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    IconButton(
                        onClick = { viewModel.openShareCard(verse.id) },
                        modifier = Modifier.testTag("verse_share_card_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Share Quote",
                            tint = SaffronPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        bottomBar = {
            Surface(
                color = MaterialTheme.colorScheme.surface,
                shadowElevation = 8.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Previous verse
                    IconButton(
                        onClick = {
                            if (prevVerse != null) {
                                viewModel.openVerseDetail(prevVerse.id)
                            }
                        },
                        enabled = prevVerse != null,
                        modifier = Modifier.testTag("prev_verse_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Previous Verse"
                        )
                    }

                    // Main Recite button
                    Button(
                        onClick = {
                            if (isThisVersePlaying) {
                                viewModel.stopRecitation()
                            } else {
                                viewModel.playVerseRecitation(
                                    verse = verse,
                                    repeatCount = selectedRepeatCount,
                                    reciteEnglish = reciteInEnglish
                                )
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isThisVersePlaying) MaterialTheme.colorScheme.error else SaffronPrimary
                        ),
                        modifier = Modifier.testTag("recite_audio_button")
                    ) {
                        Icon(
                            imageVector = if (isThisVersePlaying) Icons.Default.Stop else Icons.Default.PlayArrow,
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (isThisVersePlaying) {
                                if (repeatRemaining > 1) "Playing ($repeatRemaining left)" else "Stop"
                            } else {
                                if (selectedRepeatCount > 1) "Chant (${selectedRepeatCount}x)" else "Recite"
                            }
                        )
                    }

                    // Next verse
                    IconButton(
                        onClick = {
                            if (nextVerse != null) {
                                viewModel.openVerseDetail(nextVerse.id)
                            }
                        },
                        enabled = nextVerse != null,
                        modifier = Modifier.testTag("next_verse_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = "Next Verse"
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .testTag("verse_detail_content"),
            contentPadding = PaddingValues(top = 12.dp, bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Audio Controls Bar
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                    )
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Audio & Chanting Controls",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = SaffronPrimary
                                )
                            )

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                // Repeat count dropdown selector
                                Box {
                                    OutlinedButton(
                                        onClick = { showRepeatMenu = true },
                                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                                        modifier = Modifier.height(30.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Repeat,
                                            contentDescription = null,
                                            modifier = Modifier.size(14.dp)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("${selectedRepeatCount}x", fontSize = 11.sp)
                                    }
                                    DropdownMenu(
                                        expanded = showRepeatMenu,
                                        onDismissRequest = { showRepeatMenu = false }
                                    ) {
                                        listOf(1, 3, 11, 21, 108).forEach { count ->
                                            DropdownMenuItem(
                                                text = { Text("$count times (Japa)") },
                                                onClick = {
                                                    selectedRepeatCount = count
                                                    showRepeatMenu = false
                                                }
                                            )
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.width(6.dp))

                                // Ambient Drone toggle
                                IconButton(
                                    onClick = { viewModel.toggleAmbientDrone() },
                                    modifier = Modifier.size(32.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.GraphicEq,
                                        contentDescription = "Ambient Om Drone",
                                        tint = if (isDroneActive) GoldAccent else MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            // Sanskrit / English voice recitation mode toggle
                            OutlinedButton(
                                onClick = { reciteInEnglish = !reciteInEnglish },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(32.dp),
                                contentPadding = PaddingValues(horizontal = 4.dp, vertical = 0.dp)
                            ) {
                                Text(
                                    text = if (reciteInEnglish) "Voice: English" else "Voice: Sanskrit",
                                    fontSize = 12.sp
                                )
                            }

                            // Share text button
                            OutlinedButton(
                                onClick = { viewModel.shareVerseAsText(context, verse) },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(32.dp),
                                contentPadding = PaddingValues(horizontal = 4.dp, vertical = 0.dp)
                            ) {
                                Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Share Text", fontSize = 12.sp)
                            }
                        }
                    }
                }
            }

            // 1. Sanskrit Devanagari Card
            if (settings.showSanskrit) {
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                width = 1.dp,
                                color = GoldAccent.copy(alpha = 0.4f),
                                shape = RoundedCornerShape(16.dp)
                            ),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            SacredOmIcon(size = 36, color = SaffronPrimary)
                            Spacer(modifier = Modifier.height(12.dp))

                            Text(
                                text = verse.textSanskrit,
                                style = MaterialTheme.typography.headlineSmall.copy(
                                    fontSize = (22 * scale).sp,
                                    fontWeight = FontWeight.Bold,
                                    lineHeight = (34 * scale).sp,
                                    color = MaterialTheme.colorScheme.onSurface
                                ),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }

            // 2. Transliteration (IAST)
            if (settings.showTransliteration) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
                        )
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "TRANSLITERATION (IAST)",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = SaffronPrimary,
                                    letterSpacing = 1.sp
                                )
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = verse.textTransliteration,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontSize = (15 * scale).sp,
                                    fontStyle = FontStyle.Italic,
                                    lineHeight = (22 * scale).sp,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            )
                        }
                    }
                }
            }

            // 3. English Translation
            if (settings.showEnglish) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.35f)
                        )
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "ENGLISH TRANSLATION",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = SaffronPrimary,
                                    letterSpacing = 1.sp
                                )
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "\"${verse.textEnglish}\"",
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontSize = (16 * scale).sp,
                                    fontWeight = FontWeight.Medium,
                                    lineHeight = (24 * scale).sp,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            )
                        }
                    }
                }
            }

            // 4. Hindi Translation
            if (settings.showHindi && verse.textHindi.isNotBlank()) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "हिन्दी अनुवाद",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = SaffronPrimary,
                                    letterSpacing = 1.sp
                                )
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = verse.textHindi,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontSize = (15 * scale).sp,
                                    lineHeight = (23 * scale).sp,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            )
                        }
                    }
                }
            }

            // 5. Word-by-Word Meaning
            if (verse.wordMeanings.isNotBlank()) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "WORD-BY-WORD MEANING",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = SaffronPrimary,
                                    letterSpacing = 1.sp
                                )
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = verse.wordMeanings,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = (13 * scale).sp,
                                    lineHeight = (20 * scale).sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            )
                        }
                    }
                }
            }

            // 6. Spiritual Commentary & Practical Application
            if (settings.showCommentary) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "PHILOSOPHICAL COMMENTARY",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = SaffronPrimary,
                                    letterSpacing = 1.sp
                                )
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = verse.commentary,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontSize = (14 * scale).sp,
                                    lineHeight = (22 * scale).sp,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            )

                            if (verse.practicalApplication.isNotBlank()) {
                                Spacer(modifier = Modifier.height(14.dp))
                                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                                Spacer(modifier = Modifier.height(14.dp))

                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.AutoAwesome,
                                        contentDescription = null,
                                        tint = GoldAccent,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "Practical Life Application",
                                        style = MaterialTheme.typography.labelLarge.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = SaffronPrimary
                                        )
                                    )
                                }
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = verse.practicalApplication,
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontSize = (14 * scale).sp,
                                        lineHeight = (22 * scale).sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                )
                            }
                        }
                    }
                }
            }

            // Personal Reflection Note Card (if already saved)
            if (bookmark != null && bookmark.note.isNotBlank()) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = GoldAccent.copy(alpha = 0.1f)
                        ),
                        border = androidx.compose.foundation.BorderStroke(1.dp, GoldAccent.copy(alpha = 0.3f))
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Your Reflection Note",
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = SaffronPrimary
                                    )
                                )
                                IconButton(
                                    onClick = { showNotesDialog = true },
                                    modifier = Modifier.size(28.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.EditNote,
                                        contentDescription = "Edit Note",
                                        tint = SaffronPrimary
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = bookmark.note,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontStyle = FontStyle.Italic,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    // Note Editing Dialog
    if (showNotesDialog) {
        AlertDialog(
            onDismissRequest = { showNotesDialog = false },
            title = { Text("Personal Spiritual Reflection") },
            text = {
                Column {
                    Text(
                        text = "Write your reflections, insights, or personal prayer for Verse ${verse.id}:",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedTextField(
                        value = userNoteText,
                        onValueChange = { userNoteText = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(140.dp)
                            .testTag("reflection_note_input"),
                        placeholder = { Text("e.g. My reminder when feeling overwhelmed at work...") }
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.saveNote(verse.id, userNoteText)
                        showNotesDialog = false
                    },
                    modifier = Modifier.testTag("save_note_button")
                ) {
                    Text("Save Note")
                }
            },
            dismissButton = {
                TextButton(onClick = { showNotesDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}
