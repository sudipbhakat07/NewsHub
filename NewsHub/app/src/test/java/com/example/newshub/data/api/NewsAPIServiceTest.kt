package com.example.newshub.data.api

import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.google.common.truth.Truth.assertThat

class NewsAPIServiceTest {

    private lateinit var service : NewsAPIService
    private  lateinit var server : MockWebServer

    @Before
    fun setUp() {
        server = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(server.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsAPIService :: class.java)
    }

    private fun enqueueMockResponse(file : String) {
        val inputStream = javaClass.classLoader!!.getResourceAsStream(file)
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        mockResponse.setBody(source.readString(Charsets.UTF_8))
        server.enqueue(mockResponse)
    }


    @Test
    fun getTopHeadLines_sentRequest_receivedExcepted() {
        runBlocking {
            enqueueMockResponse("newsResponse.json")
            val responseBody = service.getTopHeadlinesService("us",1).body()
            val request = server.takeRequest()
            assertThat(responseBody).isNotNull()
            assertThat(request.path).isEqualTo("/v2/top-headlines?country=us&page=1&apiKey=3ddd5bffb2164e45ba9da48351f5ed82")
        }
    }

    @Test
    fun getTopHeadLines_receivedResponse_correctPageSize() {
        runBlocking {
            enqueueMockResponse("newsResponse.json")
            val responseBody = service.getTopHeadlinesService("us",1).body()
            val articles = responseBody!!.articles
            assertThat(articles.size).isEqualTo(20)
        }
    }
    @Test
    fun getTopHeadLines_receivedResponse_correctContent() {
        runBlocking {
            enqueueMockResponse("newsResponse.json")
            val responseBody = service.getTopHeadlinesService("us",1).body()
            val author = responseBody!!.articles[0].author
            assertThat(author).isEqualTo("Joan E Greve, Hugo Lowell")
        }
    }


    @After
    fun tearDown() {
        server.shutdown()
    }
}