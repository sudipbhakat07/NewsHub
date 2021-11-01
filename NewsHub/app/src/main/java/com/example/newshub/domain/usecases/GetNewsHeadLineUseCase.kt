package com.example.newshub.domain.usecases

import android.util.Log
import com.example.newshub.data.util.Resource
import com.example.newshub.data.model.APIResponse
import com.example.newshub.domain.repository.NewsRepository

class GetNewsHeadLineUseCase(private val newsRepository: NewsRepository){

    suspend fun execute(country : String,
                        page : Int) : Resource<APIResponse> {
        Log.i("MYTAG","Inside getNewsHeadLinesUsecaseExecute()")
        return newsRepository.getNewsHeadLineFromApi(country, page)
    }
}