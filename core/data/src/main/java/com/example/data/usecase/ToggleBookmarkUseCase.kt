package com.example.data.usecase

import com.example.data.repository.BookmarkRepository
import com.example.network.dto.DocumentsDto
import javax.inject.Inject

class ToggleBookmarkUseCase @Inject constructor(
    private val bookmarkRepository: BookmarkRepository
) {
    suspend operator fun invoke(document: DocumentsDto) {
        if (bookmarkRepository.isBookmarked(document)) {
            bookmarkRepository.deleteBookmark(document)
        } else {
            bookmarkRepository.saveBookmark(document)
        }
    }
}