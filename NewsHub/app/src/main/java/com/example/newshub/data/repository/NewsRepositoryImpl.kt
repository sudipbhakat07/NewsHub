package com.example.newshub.data.repository

import android.util.Log
import com.example.newshub.data.util.Resource
import com.example.newshub.data.model.APIResponse
import com.example.newshub.data.model.Article
import com.example.newshub.data.repository.DataResources.NewsLocalDataSource
import com.example.newshub.data.repository.DataResources.NewsRemoteDataSource
import com.example.newshub.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class NewsRepositoryImpl(
    private val newsLocalDataSource: NewsLocalDataSource,
    private val newsRemoteDataSource: NewsRemoteDataSource
) : NewsRepository {

    override suspend fun getNewsHeadLineFromApi(country: String, page: Int): Resource<APIResponse> {
        Log.i("MYTAG","Inside NewsRepoImpl")
        Log.i("MYTAG","Inside getNewsHeadLinesUseCase " )
        return responseToResource(newsRemoteDataSource.getTopHeadLines(country,page))
    }
    private fun responseToResource(response:Response<APIResponse>): Resource<APIResponse> {
        if(response.isSuccessful){
            response.body()?.let {result->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }



    override suspend fun getSearchedNews(country: String, page: Int,searchQuery: String): Resource<APIResponse> {
        return responseToResource(newsRemoteDataSource.getSearchedHeadLines(country, page,searchQuery))
    }

    override suspend fun saveNewsInDB(article: Article) {
        newsLocalDataSource.saveArticleToDB(article)
    }

    override suspend fun deleteSavedNewsFromDB(article: Article) {
        newsLocalDataSource.deleteArticle(article)
    }

    override fun getSavedNewsFromDB(): Flow<List<Article>> {
        return newsLocalDataSource.getSavedArticles()
    }
}