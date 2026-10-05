package com.example.ui

import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.audio.ChapterMedia3AudioPlayer
import com.example.audio.GitaAudioPlayer
import com.example.data.GitaRepository
import com.example.model.Bookmark
import com.example.model.Chapter
import com.example.model.FontSizePreference
import com.example.model.ThemeMode
import com.example.model.UserSettings
import com.example.model.Verse
import com.example.notification.DailyWisdomNotifier
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed class Screen {
    data object MainTabs : Screen()
    data class ChapterDetail(val chapterId: Int) : Screen()
    data class VerseDetail(val verseId: String) : Screen()
    data object AboutGita : Screen()
    data class ShareQuoteCard(val verseId: String) : Screen()
}

enum class NavigationTab(val title: String) {
    HOME("Home"),
    CHAPTERS("Chapters"),
    SEARCH("Search"),
    FAVORITES("Favorites"),
    SETTINGS("Settings")
}

class GitaViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = GitaRepository(application)
    val audioPlayer = GitaAudioPlayer(application)
    val chapterAudioPlayer = ChapterMedia3AudioPlayer(application)

    val settings: StateFlow<UserSettings> = repository.settings
    val bookmarks: StateFlow<List<Bookmark>> = repository.bookmarks
    val dailyVerse: StateFlow<Verse> = repository.dailyVerse
    val readVerseIds: StateFlow<Set<String>> = repository.readVerseIds
    val completedChapterIds: StateFlow<Set<Int>> = repository.completedChapterIds

    // Navigation Stack
    private val _screenStack = MutableStateFlow<List<Screen>>(listOf(Screen.MainTabs))
    val currentScreen: StateFlow<Screen> = MutableStateFlow(Screen.MainTabs)

    private val _selectedTab = MutableStateFlow(NavigationTab.HOME)
    val selectedTab: StateFlow<NavigationTab> = _selectedTab.asStateFlow()

    // Search state
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedSearchTag = MutableStateFlow<String?>(null)
    val selectedSearchTag: StateFlow<String?> = _selectedSearchTag.asStateFlow()

    val searchResults: StateFlow<List<Verse>> = combine(_searchQuery, _selectedSearchTag) { query, tag ->
        val results = if (query.isNotBlank()) {
            repository.searchVerses(query)
        } else if (tag != null) {
            repository.searchVerses(tag)
        } else {
            emptyList()
        }
        results
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    // Active Note Editing dialog
    private val _noteEditingVerseId = MutableStateFlow<String?>(null)
    val noteEditingVerseId: StateFlow<String?> = _noteEditingVerseId.asStateFlow()

    init {
        DailyWisdomNotifier.createNotificationChannel(application)
    }

    fun selectTab(tab: NavigationTab) {
        _selectedTab.value = tab
        _screenStack.value = listOf(Screen.MainTabs)
        updateCurrentScreen()
    }

    fun navigateTo(screen: Screen) {
        val current = _screenStack.value.toMutableList()
        current.add(screen)
        _screenStack.value = current
        updateCurrentScreen()
    }

    fun navigateBack(): Boolean {
        val current = _screenStack.value.toMutableList()
        return if (current.size > 1) {
            current.removeAt(current.size - 1)
            _screenStack.value = current
            updateCurrentScreen()
            true
        } else {
            false
        }
    }

    private fun updateCurrentScreen() {
        (currentScreen as MutableStateFlow).value = _screenStack.value.lastOrNull() ?: Screen.MainTabs
    }

    fun openVerseDetail(verseId: String) {
        repository.setLastReadVerse(verseId)
        navigateTo(Screen.VerseDetail(verseId))
    }

    fun openChapterDetail(chapterId: Int) {
        navigateTo(Screen.ChapterDetail(chapterId))
    }

    fun openShareCard(verseId: String) {
        navigateTo(Screen.ShareQuoteCard(verseId))
    }

    fun openAbout() {
        navigateTo(Screen.AboutGita)
    }

    fun setSearchQuery(q: String) {
        _searchQuery.value = q
        _selectedSearchTag.value = null
    }

    fun selectSearchTag(tag: String?) {
        if (_selectedSearchTag.value == tag) {
            _selectedSearchTag.value = null
        } else {
            _selectedSearchTag.value = tag
            _searchQuery.value = ""
        }
    }

    fun toggleFavorite(verseId: String) {
        repository.toggleFavorite(verseId)
    }

    fun isFavorite(verseId: String): Boolean = repository.isFavorite(verseId)

    fun getBookmark(verseId: String): Bookmark? = repository.getBookmark(verseId)

    fun startEditingNote(verseId: String) {
        _noteEditingVerseId.value = verseId
    }

    fun dismissNoteDialog() {
        _noteEditingVerseId.value = null
    }

    fun saveNote(verseId: String, note: String) {
        repository.saveNote(verseId, note)
        dismissNoteDialog()
    }

    fun removeBookmark(verseId: String) {
        repository.removeBookmark(verseId)
    }

    fun isVerseRead(verseId: String): Boolean = repository.isVerseRead(verseId)

    fun toggleVerseRead(verseId: String) {
        repository.toggleVerseRead(verseId)
    }

    fun toggleChapterCompleted(chapterId: Int) {
        repository.toggleChapterCompleted(chapterId)
    }

    fun isChapterCompleted(chapterId: Int): Boolean = repository.isChapterCompleted(chapterId)

    fun getChapterProgress(chapterId: Int): Float = repository.getChapterProgress(chapterId)

    fun getChapterReadCount(chapterId: Int): Int = repository.getChapterReadCount(chapterId)

    fun getOverallProgress(): Float = repository.getOverallProgress()

    fun getChapter(chapterId: Int): Chapter? = repository.getChapter(chapterId)

    fun getAllChapters(): List<Chapter> = repository.getAllChapters()

    fun getVersesForChapter(chapterId: Int): List<Verse> = repository.getVersesForChapter(chapterId)

    fun getVerse(verseId: String): Verse? = repository.getVerse(verseId)

    fun getNextVerse(currentVerseId: String): Verse? {
        val allVerses = com.example.data.GitaData.keyVerses
        val currentIndex = allVerses.indexOfFirst { it.id == currentVerseId }
        return if (currentIndex >= 0 && currentIndex < allVerses.size - 1) {
            allVerses[currentIndex + 1]
        } else null
    }

    fun getPreviousVerse(currentVerseId: String): Verse? {
        val allVerses = com.example.data.GitaData.keyVerses
        val currentIndex = allVerses.indexOfFirst { it.id == currentVerseId }
        return if (currentIndex > 0) {
            allVerses[currentIndex - 1]
        } else null
    }

    fun playVerseRecitation(verse: Verse, repeatCount: Int = 1, reciteEnglish: Boolean = false) {
        val currentSettings = settings.value
        audioPlayer.playVerse(
            verse = verse,
            repeatCount = repeatCount,
            speed = currentSettings.ttsSpeed,
            pitch = currentSettings.ttsPitch,
            reciteEnglish = reciteEnglish
        )
    }

    fun stopRecitation() {
        audioPlayer.pauseOrStop()
    }

    fun toggleAmbientDrone() {
        audioPlayer.toggleAmbientDrone()
    }

    fun refreshDailyVerse() {
        repository.refreshDailyVerse()
    }

    fun sendDailyVerseNotification(context: Context) {
        val verse = dailyVerse.value
        DailyWisdomNotifier.showDailyVerseNotification(context, verse)
    }

    fun updateFontSize(fontSize: FontSizePreference) {
        repository.updateSettings(settings.value.copy(fontSize = fontSize))
    }

    fun updateThemeMode(mode: ThemeMode) {
        repository.updateSettings(settings.value.copy(themeMode = mode))
    }

    fun toggleSetting(key: String) {
        val cur = settings.value
        val updated = when (key) {
            "sanskrit" -> cur.copy(showSanskrit = !cur.showSanskrit)
            "transliteration" -> cur.copy(showTransliteration = !cur.showTransliteration)
            "english" -> cur.copy(showEnglish = !cur.showEnglish)
            "hindi" -> cur.copy(showHindi = !cur.showHindi)
            "commentary" -> cur.copy(showCommentary = !cur.showCommentary)
            "ambient" -> {
                val next = !cur.ambientChantEnabled
                if (next) audioPlayer.startAmbientDrone() else audioPlayer.stopAmbientDrone()
                cur.copy(ambientChantEnabled = next)
            }
            "notification" -> cur.copy(dailyNotificationEnabled = !cur.dailyNotificationEnabled)
            else -> cur
        }
        repository.updateSettings(updated)
    }

    fun updateTtsSpeed(speed: Float) {
        repository.updateSettings(settings.value.copy(ttsSpeed = speed))
    }

    fun shareVerseAsText(context: Context, verse: Verse) {
        val shareBody = buildString {
            append("॥ श्रीमद्भगवद्गीता ॥\n")
            append("Chapter ${verse.chapterId}, Verse ${verse.verseNumber}\n\n")
            append("${verse.textSanskrit}\n\n")
            append("${verse.textTransliteration}\n\n")
            append("Translation:\n\"${verse.textEnglish}\"\n\n")
            append("Hindi:\n${verse.textHindi}\n\n")
            append("Shared from Bhagavad Geetha Wisdom")
        }

        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, shareBody)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, "Share Sacred Verse")
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(shareIntent)
    }

    fun playChapterAudio(chapterId: Int) {
        chapterAudioPlayer.playChapter(chapterId)
    }

    fun toggleChapterAudio(chapterId: Int) {
        chapterAudioPlayer.togglePlayPause(chapterId)
    }

    fun pauseChapterAudio() {
        chapterAudioPlayer.pause()
    }

    fun resumeChapterAudio() {
        chapterAudioPlayer.resume()
    }

    fun seekChapterAudio(positionMs: Long) {
        chapterAudioPlayer.seekTo(positionMs)
    }

    fun seekChapterAudioForward() {
        chapterAudioPlayer.seekForward(10000L)
    }

    fun seekChapterAudioBack() {
        chapterAudioPlayer.seekBack(10000L)
    }

    fun setChapterAudioSpeed(speed: Float) {
        chapterAudioPlayer.setSpeed(speed)
    }

    fun toggleChapterAudioRepeat() {
        chapterAudioPlayer.toggleRepeat()
    }

    fun stopChapterAudio() {
        chapterAudioPlayer.stop()
    }

    override fun onCleared() {
        super.onCleared()
        audioPlayer.release()
        chapterAudioPlayer.release()
    }
}
