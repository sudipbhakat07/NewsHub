package com.example.newshub.presentation.di

import com.example.newshub.data.api.NewsAPIService
import com.example.newshub.data.repository.DataResources.NewsRemoteDataSource
import com.example.newshub.data.repository.DataResourcesImpl.NewsRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent :: class)
class RemoteDataModule {

    @Singleton
    @Provides
    fun provideRemoteDataSource(newsAPIService: NewsAPIService) : NewsRemoteDataSource {
        return NewsRemoteDataSourceImpl(newsAPIService)
    }
}