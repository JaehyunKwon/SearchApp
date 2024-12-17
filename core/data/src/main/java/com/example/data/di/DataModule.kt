package com.example.data.di

import com.example.data.repository.BookmarkRepository
import com.example.data.repository.BookmarkRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    abstract fun provideBookmarkRepository(repo: BookmarkRepositoryImpl): BookmarkRepository
}