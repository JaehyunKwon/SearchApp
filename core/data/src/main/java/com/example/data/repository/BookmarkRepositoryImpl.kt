package com.example.data.repository

import com.example.data.dao.BookmarkDao
import com.example.data.entity.DocumentsEntity
import com.example.data.util.DocumentsMapper
import com.example.network.dto.DocumentsDto
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BookmarkRepositoryImpl @Inject constructor(
    private val dao: BookmarkDao
) : BookmarkRepository {
    override suspend fun saveBookmark(document: DocumentsDto) {
        val entity = DocumentsMapper.dtoToEntity(document)
        dao.insertBookmark(entity)
    }

    override suspend fun deleteBookmark(document: DocumentsDto) {
        val entity = DocumentsMapper.dtoToEntity(document)
        dao.deleteBookmark(entity)
    }

    override suspend fun isBookmarked(document: DocumentsDto): Boolean {
        val entity = DocumentsMapper.dtoToEntity(document)
        return dao.isBookmarked(entity.doc_url) != null
    }

    override suspend fun getBookmark(): Flow<List<DocumentsEntity>> {
        return dao.getBookmark()
    }
}