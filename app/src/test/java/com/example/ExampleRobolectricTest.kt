package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.audio.ChapterMedia3AudioPlayer
import com.example.audio.ChapterPlaybackStatus
import com.example.data.GitaData
import com.example.data.GitaRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ExampleRobolectricTest {

    @Test
    fun `read string from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("Geetha Wisdom", appName)
    }

    @Test
    fun `verify all 18 chapters are present`() {
        assertEquals(18, GitaData.chapters.size)
        val chapter2 = GitaData.getChapter(2)
        assertNotNull(chapter2)
        assertEquals("साङ्ख्ययोग", chapter2?.nameSanskrit)
        assertEquals(72, chapter2?.versesCount)
    }

    @Test
    fun `verify key verses search functionality`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val repository = GitaRepository(context)

        val karmaVerses = repository.searchVerses("karma")
        assertTrue(karmaVerses.isNotEmpty())

        val peaceVerses = repository.searchVerses("peace")
        assertTrue(peaceVerses.isNotEmpty())
    }

    @Test
    fun `verify bookmarks toggle and persistence`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val repository = GitaRepository(context)

        repository.toggleFavorite("2.47", "My personal duty reflection")
        assertTrue(repository.isFavorite("2.47"))
        assertEquals("My personal duty reflection", repository.getBookmark("2.47")?.note)

        // Toggle off
        repository.removeBookmark("2.47")
        assertEquals(false, repository.isFavorite("2.47"))
    }

    @Test
    fun `verify chapter reading progress calculation and completion`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val repository = GitaRepository(context)

        // Mark a verse from Chapter 1 as read
        repository.markVerseAsRead("1.1")
        assertTrue(repository.isVerseRead("1.1"))

        val chapter1Verses = repository.getVersesForChapter(1)
        val progress = repository.getChapterProgress(1)
        assertTrue(progress > 0f)

        // Mark entire chapter complete
        repository.markChapterCompleted(1, true)
        assertTrue(repository.isChapterCompleted(1))
        assertEquals(1.0f, repository.getChapterProgress(1), 0.001f)

        // Toggle chapter complete off
        repository.markChapterCompleted(1, false)
        assertEquals(false, repository.isChapterCompleted(1))
    }

    @Test
    fun `verify chapter media3 audio player initialization and state`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val chapterPlayer = ChapterMedia3AudioPlayer(context)

        assertEquals(null, chapterPlayer.currentChapterId.value)
        assertEquals(ChapterPlaybackStatus.IDLE, chapterPlayer.playbackStatus.value)
        assertEquals(false, chapterPlayer.isPlaying.value)
        assertEquals(1.0f, chapterPlayer.playbackSpeed.value, 0.01f)
        assertEquals(false, chapterPlayer.isRepeatMode.value)

        chapterPlayer.setSpeed(1.25f)
        assertEquals(1.25f, chapterPlayer.playbackSpeed.value, 0.01f)

        chapterPlayer.toggleRepeat()
        assertTrue(chapterPlayer.isRepeatMode.value)

        chapterPlayer.release()
    }
}
