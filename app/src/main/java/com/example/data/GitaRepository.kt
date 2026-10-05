package com.example.data

import android.content.Context
import android.content.SharedPreferences
import com.example.model.Bookmark
import com.example.model.Chapter
import com.example.model.FontSizePreference
import com.example.model.ThemeMode
import com.example.model.UserSettings
import com.example.model.Verse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.json.JSONArray
import org.json.JSONObject
import java.util.Calendar

class GitaRepository(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("gita_wisdom_prefs", Context.MODE_PRIVATE)

    private val _settings = MutableStateFlow(loadSettings())
    val settings: StateFlow<UserSettings> = _settings.asStateFlow()

    private val _bookmarks = MutableStateFlow(loadBookmarks())
    val bookmarks: StateFlow<List<Bookmark>> = _bookmarks.asStateFlow()

    private val _dailyVerse = MutableStateFlow(calculateDailyVerse())
    val dailyVerse: StateFlow<Verse> = _dailyVerse.asStateFlow()

    private val _readVerseIds = MutableStateFlow(loadReadVerseIds())
    val readVerseIds: StateFlow<Set<String>> = _readVerseIds.asStateFlow()

    private val _completedChapterIds = MutableStateFlow(loadCompletedChapterIds())
    val completedChapterIds: StateFlow<Set<Int>> = _completedChapterIds.asStateFlow()

    fun getAllChapters(): List<Chapter> = GitaData.chapters

    fun getChapter(id: Int): Chapter? = GitaData.getChapter(id)

    fun getVersesForChapter(chapterId: Int): List<Verse> = GitaData.getVersesForChapter(chapterId)

    fun getVerse(id: String): Verse? = GitaData.getVerse(id)

    fun searchVerses(query: String): List<Verse> = GitaData.searchVerses(query)

    fun isFavorite(verseId: String): Boolean {
        return _bookmarks.value.any { it.verseId == verseId && it.isFavorite }
    }

    fun getBookmark(verseId: String): Bookmark? {
        return _bookmarks.value.find { it.verseId == verseId }
    }

    fun toggleFavorite(verseId: String, note: String = "") {
        val current = _bookmarks.value.toMutableList()
        val index = current.indexOfFirst { it.verseId == verseId }
        if (index >= 0) {
            val existing = current[index]
            if (existing.isFavorite && existing.note.isBlank() && note.isBlank()) {
                current.removeAt(index)
            } else {
                current[index] = existing.copy(
                    isFavorite = !existing.isFavorite,
                    note = if (note.isNotBlank()) note else existing.note,
                    timestamp = System.currentTimeMillis()
                )
            }
        } else {
            current.add(
                Bookmark(
                    verseId = verseId,
                    note = note,
                    timestamp = System.currentTimeMillis(),
                    isFavorite = true
                )
            )
        }
        _bookmarks.value = current
        saveBookmarks(current)
    }

    fun saveNote(verseId: String, note: String) {
        val current = _bookmarks.value.toMutableList()
        val index = current.indexOfFirst { it.verseId == verseId }
        if (index >= 0) {
            current[index] = current[index].copy(
                note = note,
                timestamp = System.currentTimeMillis()
            )
        } else {
            current.add(
                Bookmark(
                    verseId = verseId,
                    note = note,
                    timestamp = System.currentTimeMillis(),
                    isFavorite = true
                )
            )
        }
        _bookmarks.value = current
        saveBookmarks(current)
    }

    fun removeBookmark(verseId: String) {
        val current = _bookmarks.value.filterNot { it.verseId == verseId }
        _bookmarks.value = current
        saveBookmarks(current)
    }

    // Reading Progress Tracking
    fun isVerseRead(verseId: String): Boolean = _readVerseIds.value.contains(verseId)

    fun markVerseAsRead(verseId: String) {
        val updated = _readVerseIds.value.toMutableSet()
        if (updated.add(verseId)) {
            _readVerseIds.value = updated
            saveReadVerseIds(updated)
            checkChapterAutoCompletion(verseId)
        }
    }

    fun toggleVerseRead(verseId: String) {
        val updated = _readVerseIds.value.toMutableSet()
        if (updated.contains(verseId)) {
            updated.remove(verseId)
        } else {
            updated.add(verseId)
        }
        _readVerseIds.value = updated
        saveReadVerseIds(updated)
        checkChapterAutoCompletion(verseId)
    }

    private fun checkChapterAutoCompletion(verseId: String) {
        val verse = GitaData.getVerse(verseId) ?: return
        val chapterVerses = GitaData.getVersesForChapter(verse.chapterId)
        if (chapterVerses.isNotEmpty() && chapterVerses.all { _readVerseIds.value.contains(it.id) }) {
            markChapterCompleted(verse.chapterId, true)
        }
    }

    fun isChapterCompleted(chapterId: Int): Boolean = _completedChapterIds.value.contains(chapterId)

    fun markChapterCompleted(chapterId: Int, isCompleted: Boolean) {
        val chapters = _completedChapterIds.value.toMutableSet()
        val verses = _readVerseIds.value.toMutableSet()
        val chapterVerses = GitaData.getVersesForChapter(chapterId)

        if (isCompleted) {
            chapters.add(chapterId)
            chapterVerses.forEach { verses.add(it.id) }
        } else {
            chapters.remove(chapterId)
            chapterVerses.forEach { verses.remove(it.id) }
        }

        _completedChapterIds.value = chapters
        _readVerseIds.value = verses
        saveCompletedChapterIds(chapters)
        saveReadVerseIds(verses)
    }

    fun toggleChapterCompleted(chapterId: Int) {
        val current = isChapterCompleted(chapterId)
        markChapterCompleted(chapterId, !current)
    }

    fun getChapterProgress(chapterId: Int): Float {
        if (isChapterCompleted(chapterId)) return 1.0f
        val chapterVerses = GitaData.getVersesForChapter(chapterId)
        if (chapterVerses.isEmpty()) return 0.0f
        val readCount = chapterVerses.count { _readVerseIds.value.contains(it.id) }
        return (readCount.toFloat() / chapterVerses.size).coerceIn(0.0f, 1.0f)
    }

    fun getChapterReadCount(chapterId: Int): Int {
        val chapterVerses = GitaData.getVersesForChapter(chapterId)
        return chapterVerses.count { _readVerseIds.value.contains(it.id) }
    }

    fun getOverallProgress(): Float {
        val totalKeyVerses = GitaData.keyVerses.size
        if (totalKeyVerses == 0) return 0f
        val readCount = GitaData.keyVerses.count { _readVerseIds.value.contains(it.id) }
        return (readCount.toFloat() / totalKeyVerses).coerceIn(0.0f, 1.0f)
    }

    fun setLastReadVerse(verseId: String) {
        markVerseAsRead(verseId)
        val updated = _settings.value.copy(lastReadVerseId = verseId)
        updateSettings(updated)
    }

    fun updateSettings(newSettings: UserSettings) {
        _settings.value = newSettings
        prefs.edit().apply {
            putString("fontSize", newSettings.fontSize.name)
            putString("themeMode", newSettings.themeMode.name)
            putBoolean("showSanskrit", newSettings.showSanskrit)
            putBoolean("showTransliteration", newSettings.showTransliteration)
            putBoolean("showEnglish", newSettings.showEnglish)
            putBoolean("showHindi", newSettings.showHindi)
            putBoolean("showCommentary", newSettings.showCommentary)
            putFloat("ttsSpeed", newSettings.ttsSpeed)
            putFloat("ttsPitch", newSettings.ttsPitch)
            putBoolean("ambientChantEnabled", newSettings.ambientChantEnabled)
            putBoolean("dailyNotificationEnabled", newSettings.dailyNotificationEnabled)
            putString("lastReadVerseId", newSettings.lastReadVerseId)
            apply()
        }
    }

    fun refreshDailyVerse() {
        val current = _dailyVerse.value
        val pool = GitaData.keyVerses.filter { it.id != current.id }
        if (pool.isNotEmpty()) {
            _dailyVerse.value = pool.random()
        }
    }

    private fun calculateDailyVerse(): Verse {
        val dayOfYear = Calendar.getInstance().get(Calendar.DAY_OF_YEAR)
        val list = GitaData.keyVerses
        if (list.isEmpty()) return GitaData.keyVerses.first()
        val index = (dayOfYear + 7) % list.size
        return list[index]
    }

    private fun loadSettings(): UserSettings {
        val fontSizeStr = prefs.getString("fontSize", FontSizePreference.MEDIUM.name) ?: FontSizePreference.MEDIUM.name
        val themeModeStr = prefs.getString("themeMode", ThemeMode.SYSTEM.name) ?: ThemeMode.SYSTEM.name

        return UserSettings(
            fontSize = try { FontSizePreference.valueOf(fontSizeStr) } catch (_: Exception) { FontSizePreference.MEDIUM },
            themeMode = try { ThemeMode.valueOf(themeModeStr) } catch (_: Exception) { ThemeMode.SYSTEM },
            showSanskrit = prefs.getBoolean("showSanskrit", true),
            showTransliteration = prefs.getBoolean("showTransliteration", true),
            showEnglish = prefs.getBoolean("showEnglish", true),
            showHindi = prefs.getBoolean("showHindi", true),
            showCommentary = prefs.getBoolean("showCommentary", true),
            ttsSpeed = prefs.getFloat("ttsSpeed", 0.9f),
            ttsPitch = prefs.getFloat("ttsPitch", 1.0f),
            ambientChantEnabled = prefs.getBoolean("ambientChantEnabled", false),
            dailyNotificationEnabled = prefs.getBoolean("dailyNotificationEnabled", true),
            lastReadVerseId = prefs.getString("lastReadVerseId", "2.47") ?: "2.47"
        )
    }

    private fun loadBookmarks(): List<Bookmark> {
        val raw = prefs.getString("saved_bookmarks", null) ?: return emptyList()
        val list = mutableListOf<Bookmark>()
        try {
            val jsonArray = JSONArray(raw)
            for (i in 0 until jsonArray.length()) {
                val obj = jsonArray.getJSONObject(i)
                list.add(
                    Bookmark(
                        verseId = obj.getString("verseId"),
                        note = obj.optString("note", ""),
                        timestamp = obj.optLong("timestamp", System.currentTimeMillis()),
                        isFavorite = obj.optBoolean("isFavorite", true)
                    )
                )
            }
        } catch (_: Exception) {}
        return list
    }

    private fun saveBookmarks(bookmarks: List<Bookmark>) {
        val jsonArray = JSONArray()
        for (b in bookmarks) {
            val obj = JSONObject().apply {
                put("verseId", b.verseId)
                put("note", b.note)
                put("timestamp", b.timestamp)
                put("isFavorite", b.isFavorite)
            }
            jsonArray.put(obj)
        }
        prefs.edit().putString("saved_bookmarks", jsonArray.toString()).apply()
    }

    private fun loadReadVerseIds(): Set<String> {
        return prefs.getStringSet("read_verse_ids", emptySet()) ?: emptySet()
    }

    private fun saveReadVerseIds(ids: Set<String>) {
        prefs.edit().putStringSet("read_verse_ids", ids).apply()
    }

    private fun loadCompletedChapterIds(): Set<Int> {
        val set = prefs.getStringSet("completed_chapter_ids", emptySet()) ?: emptySet()
        return set.mapNotNull { it.toIntOrNull() }.toSet()
    }

    private fun saveCompletedChapterIds(ids: Set<Int>) {
        val set = ids.map { it.toString() }.toSet()
        prefs.edit().putStringSet("completed_chapter_ids", set).apply()
    }
}
