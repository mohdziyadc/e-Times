package com.example.e_times.network

import com.example.e_times.Utils.Constants.Companion.API_KEY
import com.example.e_times.models.News
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {

    @GET("/v2/top-headlines")
    suspend fun getBreakingNews(
        @Query("country")
        countryCode:String,
        @Query("page")
        page:Int,
        @Query("apiKey")
        apiKey:String = API_KEY
    ): Response<News>

    @GET("/v2/everything")
    suspend fun getSearchNews(
        @Query("q")
        searchQuery:String,
        @Query("page")
        page:Int,
        @Query("apiKey")
        apiKey:String = API_KEY
    ): Response<News>

}

/*
This is what i was lacking earlier in Books App.
We can create another GET request in order to do searching.
 */