package com.example.bookmark

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.bookmark.screen.BookmarkScreen
import com.example.bookmark.viewmodel.BookmarkViewModel
import com.example.data.entity.DocumentsEntity
import com.example.data.repository.BookmarkRepository
import com.example.data.usecase.ToggleBookmarkUseCase
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class BookmarkScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val bookmarkRepository: BookmarkRepository = mockk()
    private val toggleBookmarkUseCase: ToggleBookmarkUseCase = mockk()
    private lateinit var viewModel: BookmarkViewModel

    @Before
    fun setUp() {
        viewModel = BookmarkViewModel(bookmarkRepository, toggleBookmarkUseCase)
    }

    @Test
    fun bookmarkButtonTogglesState() = runTest {
        // Given
        val document = DocumentsEntity(
            doc_url = "1",
            collection = "Sample Collection",
            thumbnail_url = "https://example.com/image.jpg",
            image_url = "https://example.com/image.jpg",
            width = 100,
            height = 100,
            display_sitename = "Example",
            datetime = "2023-09-17"
        )
        val initialBookmarks = listOf(document)
        val bookmarkStateFlow = MutableStateFlow(initialBookmarks)

        coEvery { bookmarkRepository.getBookmark() } returns bookmarkStateFlow
        coEvery { toggleBookmarkUseCase(any()) } answers {
            bookmarkStateFlow.value = bookmarkStateFlow.value.filterNot { it == document }
        }

        composeTestRule.setContent {
            BookmarkScreen(viewModel = viewModel)
        }

        // Verify: Bookmark button is displayed
        composeTestRule.onNodeWithContentDescription("Bookmark Icon").assertExists()

        // When: Bookmark button is clicked
        composeTestRule.onNodeWithContentDescription("Bookmark Icon").performClick()

        // Then: Check state change
        composeTestRule.waitForIdle() // Wait for UI state to settle
        val bookmarks = viewModel.bookmarkState.value
        assert(bookmarks.isEmpty()) // Bookmark should be removed
    }

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.profilescreen.test", appContext.packageName)
    }
}