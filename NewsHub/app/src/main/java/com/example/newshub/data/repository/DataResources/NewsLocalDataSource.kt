package com.example.newshub.data.repository.DataResources

import com.example.newshub.data.model.Article
import kotlinx.coroutines.flow.Flow

interface NewsLocalDataSource {
    suspend fun saveArticleToDB(article: Article)
    fun getSavedArticles() : Flow<List<Article>>
    suspend fun deleteArticle(article: Article)
}