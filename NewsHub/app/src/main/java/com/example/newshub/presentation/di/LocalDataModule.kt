package com.example.newshub.presentation.di

import com.example.newshub.data.db.ArticleDAO
import com.example.newshub.data.repository.DataResources.NewsLocalDataSource
import com.example.newshub.data.repository.DataResourcesImpl.NewsLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent :: class)
class LocalDataModule {

    @Singleton
    @Provides
    fun provideLocalDataSource(articleDAO: ArticleDAO): NewsLocalDataSource {
        return NewsLocalDataSourceImpl(articleDAO)
    }
}