package com.example.data.repository

import com.example.data.entity.DocumentsEntity
import com.example.network.dto.DocumentsDto
import kotlinx.coroutines.flow.Flow


interface BookmarkRepository {
    suspend fun saveBookmark(document: DocumentsDto)
    suspend fun deleteBookmark(document: DocumentsDto)
    suspend fun isBookmarked(document: DocumentsDto): Boolean
    suspend fun getBookmark(): Flow<List<DocumentsEntity>>
}