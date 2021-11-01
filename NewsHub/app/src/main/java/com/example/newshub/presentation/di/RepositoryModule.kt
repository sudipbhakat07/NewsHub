package com.example.newshub.presentation.di

import com.example.newshub.data.repository.DataResources.NewsLocalDataSource
import com.example.newshub.data.repository.DataResources.NewsRemoteDataSource
import com.example.newshub.data.repository.NewsRepositoryImpl
import com.example.newshub.domain.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent :: class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideNewsRepository(newsLocalDataSource: NewsLocalDataSource, newsRemoteDataSource: NewsRemoteDataSource) : NewsRepository {
        return NewsRepositoryImpl(newsLocalDataSource,newsRemoteDataSource)
    }
}