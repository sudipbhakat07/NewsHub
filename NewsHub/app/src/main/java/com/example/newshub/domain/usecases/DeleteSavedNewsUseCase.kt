package com.example.newshub.domain.usecases

import com.example.newshub.data.model.Article
import com.example.newshub.domain.repository.NewsRepository

class DeleteSavedNewsUseCase (private val newsRepository: NewsRepository){

    suspend fun execute(article: Article) = newsRepository.deleteSavedNewsFromDB(article)
}