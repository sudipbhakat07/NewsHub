package com.example.newshub.data.api

import com.example.newshub.BuildConfig
import com.example.newshub.data.model.APIResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPIService {

    @GET("/v2/top-headlines")
    suspend fun getTopHeadlinesService(
        @Query("country")
        country : String,
        @Query("page")
        page : Int,
        @Query("apiKey")
        apiKey : String = BuildConfig.API_KEY
    ) : Response<APIResponse>

    @GET("/v2/top-headlines")
    suspend fun getSearchedHeadlinesService(
        @Query("country")
        country : String,
        @Query("page")
        page : Int,
        @Query("q")
        searchedNews : String,
        @Query("apiKey")
        apiKey : String = BuildConfig.API_KEY
    ) : Response<APIResponse>
}