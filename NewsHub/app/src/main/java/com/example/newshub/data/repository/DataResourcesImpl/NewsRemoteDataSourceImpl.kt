package com.example.newshub.data.repository.DataResourcesImpl

import android.util.Log
import com.example.newshub.data.api.NewsAPIService
import com.example.newshub.data.model.APIResponse
import com.example.newshub.data.repository.DataResources.NewsRemoteDataSource
import retrofit2.Response

class NewsRemoteDataSourceImpl(
    private val newsAPIService: NewsAPIService,
) : NewsRemoteDataSource {

    override suspend fun getTopHeadLines(country : String,
                                         page : Int): Response<APIResponse> {
        Log.i("MYTAG","Inside NewsRemoteDataSourceImpl")
        return newsAPIService.getTopHeadlinesService(country,page)
    }

    override suspend fun getSearchedHeadLines(
        country: String,
        page: Int,
        searchedNews: String,
    ): Response<APIResponse> {
        return newsAPIService.getSearchedHeadlinesService(country, page, searchedNews)
    }
}