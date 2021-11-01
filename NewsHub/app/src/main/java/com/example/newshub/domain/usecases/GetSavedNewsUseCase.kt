package com.example.newshub.domain.usecases

import com.example.newshub.domain.repository.NewsRepository

class GetSavedNewsUseCase(private val newsRepository: NewsRepository){

     fun execute() = newsRepository.getSavedNewsFromDB()
}