package com.example.newshub.data.repository.DataResources

import com.example.newshub.data.model.APIResponse
import retrofit2.Response

interface NewsRemoteDataSource {
    suspend fun getTopHeadLines(country : String,
                                page : Int) : Response<APIResponse>
    suspend fun getSearchedHeadLines(country : String,
                                page : Int,searchedNews : String) : Response<APIResponse>
}