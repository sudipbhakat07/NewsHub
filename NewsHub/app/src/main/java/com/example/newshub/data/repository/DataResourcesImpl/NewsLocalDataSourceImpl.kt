package com.example.newshub.data.repository.DataResourcesImpl

import com.example.newshub.data.db.ArticleDAO
import com.example.newshub.data.model.Article
import com.example.newshub.data.repository.DataResources.NewsLocalDataSource
import kotlinx.coroutines.flow.Flow

class NewsLocalDataSourceImpl(private val articleDAO: ArticleDAO) : NewsLocalDataSource {
    override suspend fun saveArticleToDB(article: Article) {
        articleDAO.insert(article)
    }

    override fun getSavedArticles(): Flow<List<Article>> {
       return articleDAO.getAllArticles()
    }

    override suspend fun deleteArticle(article: Article) {
        articleDAO.delete(article)
    }
}