package com.example.network.di

import com.example.network.repository.ImageRepository
import com.example.network.repository.ImageRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindImageRepository(imageRepositoryImpl: ImageRepositoryImpl): ImageRepository

}