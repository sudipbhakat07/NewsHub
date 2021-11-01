package com.example.newshub.domain.repository

import com.example.newshub.data.util.Resource
import com.example.newshub.data.model.APIResponse
import com.example.newshub.data.model.Article
import kotlinx.coroutines.flow.Flow


interface NewsRepository {

    suspend fun getNewsHeadLineFromApi(country : String,
                                       page : Int) : Resource<APIResponse>
    suspend fun getSearchedNews(country: String, page: Int,searchQuery : String) : Resource<APIResponse>
    suspend fun saveNewsInDB(article : Article)
    suspend fun deleteSavedNewsFromDB(article: Article)
    fun getSavedNewsFromDB() : Flow<List<Article>>
}