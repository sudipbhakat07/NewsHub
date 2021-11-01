package com.example.newshub.presentation.di

import com.example.newshub.domain.repository.NewsRepository
import com.example.newshub.domain.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent :: class)
class UseCaseModule {
    @Singleton
    @Provides
    fun provideGetNewsheadLinesUseCase(
        newsRepository: NewsRepository
    ):GetNewsHeadLineUseCase{
        return GetNewsHeadLineUseCase(newsRepository)
    }

    @Singleton
    @Provides
    fun provideGetSearchedNewsUseCase(
        newsRepository: NewsRepository
    ):GetSearchedNewsUseCase{
        return GetSearchedNewsUseCase(newsRepository)
    }

    @Singleton
    @Provides
    fun provideSaveNewsUseCase(
        newsRepository: NewsRepository
    ):SaveNewsUseCase{
        return SaveNewsUseCase(newsRepository)
    }

    @Singleton
    @Provides
    fun provideGetSavedNewsUseCase(
        newsRepository: NewsRepository
    ): GetSavedNewsUseCase {
        return GetSavedNewsUseCase(newsRepository)
    }

    @Singleton
    @Provides
    fun provideDeleteSavedNewsUseCase(
        newsRepository: NewsRepository
    ):DeleteSavedNewsUseCase{
        return DeleteSavedNewsUseCase(newsRepository)
    }
}


















