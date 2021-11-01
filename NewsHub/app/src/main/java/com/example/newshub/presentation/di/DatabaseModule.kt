package com.example.newshub.presentation.di

import android.app.Application
import androidx.room.Room
import com.example.newshub.data.db.ArticleDAO
import com.example.newshub.data.db.ArticleDAO_Impl
import com.example.newshub.data.db.ArticleDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent :: class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideNewsDatabase(app: Application): ArticleDatabase {
        return Room.databaseBuilder(app, ArticleDatabase::class.java, "news_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideDAO(database: ArticleDatabase) : ArticleDAO {
        return  database.getArticleDAO()
    }
}