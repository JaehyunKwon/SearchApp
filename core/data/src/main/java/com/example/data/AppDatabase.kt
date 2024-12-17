package com.example.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.dao.BookmarkDao
import com.example.data.entity.DocumentsEntity

@Database(entities = [DocumentsEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bookmarkDao(): BookmarkDao
}