package com.example.data.usecase

import com.example.data.repository.BookmarkRepository
import com.example.network.dto.DocumentsDto
import javax.inject.Inject

class IsBookmarkedUseCase @Inject constructor(
    private val bookmarkRepository: BookmarkRepository
) {
    suspend operator fun invoke(document: DocumentsDto): Boolean {
        return bookmarkRepository.isBookmarked(document)
    }
}
