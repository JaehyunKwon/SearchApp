package com.example.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.entity.DocumentsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BookmarkDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmark(document: DocumentsEntity)

    @Delete
    suspend fun deleteBookmark(document: DocumentsEntity)

    @Query("SELECT * FROM bookmarks WHERE doc_url = :doc_url LIMIT 1")
    suspend fun isBookmarked(doc_url: String): DocumentsEntity?

    @Query("SELECT * FROM bookmarks")
    suspend fun getBookmark(): Flow<List<DocumentsEntity>>
}