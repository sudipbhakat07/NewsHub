package com.example.newshub.domain.usecases

import com.example.newshub.data.model.APIResponse
import com.example.newshub.data.util.Resource
import com.example.newshub.domain.repository.NewsRepository

class GetSearchedNewsUseCase(private val newsRepository: NewsRepository){

    suspend fun execute(country: String, page: Int,searchQuery : String) : Resource<APIResponse> = newsRepository.getSearchedNews(country, page, searchQuery)
}