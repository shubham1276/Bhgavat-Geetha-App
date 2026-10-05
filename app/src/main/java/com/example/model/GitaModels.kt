package com.example.model

enum class FontSizePreference(val title: String, val scale: Float) {
    SMALL("Small", 0.85f),
    MEDIUM("Medium", 1.0f),
    LARGE("Large", 1.2f),
    EXTRA_LARGE("Extra Large", 1.4f)
}

enum class ThemeMode(val title: String) {
    SYSTEM("System Default"),
    LIGHT("Light (Saffron & Gold)"),
    DARK("Dark (Spiritual Midnight)")
}

data class Chapter(
    val id: Int,
    val nameSanskrit: String,
    val nameTransliteration: String,
    val nameEnglish: String,
    val versesCount: Int,
    val yogaType: String,
    val summary: String,
    val keyTeachings: List<String>
)

data class Verse(
    val id: String, // e.g. "2.47"
    val chapterId: Int,
    val verseNumber: Int,
    val textSanskrit: String,
    val textTransliteration: String,
    val textEnglish: String,
    val textHindi: String,
    val wordMeanings: String,
    val commentary: String,
    val practicalApplication: String,
    val tags: List<String>
)

data class Bookmark(
    val verseId: String,
    val note: String = "",
    val timestamp: Long = System.currentTimeMillis(),
    val isFavorite: Boolean = true
)

data class UserSettings(
    val fontSize: FontSizePreference = FontSizePreference.MEDIUM,
    val themeMode: ThemeMode = ThemeMode.SYSTEM,
    val showSanskrit: Boolean = true,
    val showTransliteration: Boolean = true,
    val showEnglish: Boolean = true,
    val showHindi: Boolean = true,
    val showCommentary: Boolean = true,
    val ttsSpeed: Float = 0.9f,
    val ttsPitch: Float = 1.0f,
    val ambientChantEnabled: Boolean = false,
    val dailyNotificationEnabled: Boolean = true,
    val lastReadVerseId: String = "2.47"
)
