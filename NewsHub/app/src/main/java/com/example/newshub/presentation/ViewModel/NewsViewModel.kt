package com.example.newshub.presentation.ViewModel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.*
import com.example.newshub.data.util.Resource
import com.example.newshub.data.model.APIResponse
import com.example.newshub.data.model.Article
import com.example.newshub.domain.usecases.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Exception

class NewsViewModel(
    private val app:Application,
    private val getNewsHeadlinesUseCase: GetNewsHeadLineUseCase,
    private val getSearchedNewsUseCase: GetSearchedNewsUseCase,
    private val saveNewsUseCase: SaveNewsUseCase,
    private val getSavedNewsUseCase: GetSavedNewsUseCase,
    private val deleteSavedNewsUseCase: DeleteSavedNewsUseCase
) : AndroidViewModel(app) {
    val newsHeadLines: MutableLiveData<Resource<APIResponse>> = MutableLiveData()

    fun getNewsHeadLines(country: String, page: Int) = viewModelScope.launch(Dispatchers.IO) {
        newsHeadLines.postValue(Resource.Loading())
        try{
            if(isNetworkAvailable(app)) {

                val apiResult = getNewsHeadlinesUseCase.execute(country, page)
                newsHeadLines.postValue(apiResult)
            }else{
                newsHeadLines.postValue(Resource.Error("Internet is not available"))
            }

        }catch (e:Exception){
            newsHeadLines.postValue(Resource.Error(e.message.toString()))
        }

    }

    private fun isNetworkAvailable(context: Context?):Boolean{
        if (context == null) return false
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            if (connectivityManager.isDefaultNetworkActive) {
                return true
            }
        }
        return false

    }
    val searchedNews : MutableLiveData<Resource<APIResponse>> = MutableLiveData()
    fun getSeachedNews(country: String, page: Int,searchQuery : String) = viewModelScope.launch(Dispatchers.IO) {
        searchedNews.postValue(Resource.Loading())
        try{
            if(isNetworkAvailable(app)) {

                val apiResult = getSearchedNewsUseCase.execute(country, page,searchQuery)
                searchedNews.postValue(apiResult)
            }else{
                searchedNews.postValue(Resource.Error("Internet is not available"))
            }

        }catch (e:Exception){
            searchedNews.postValue(Resource.Error(e.message.toString()))
        }

    }

        fun saveNewsToDB(article: Article) {
            viewModelScope.launch {
                saveNewsUseCase.execute(article)
            }
        }


    fun getSavedNewsFromDB() = liveData {
      val a =   getSavedNewsUseCase.execute().collect {
          emit(it)
      }
    }


        fun deleteArticle(article: Article) = viewModelScope.launch {
            deleteSavedNewsUseCase.execute(article)
        }

}














