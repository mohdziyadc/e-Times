package com.example.e_times

import com.example.e_times.database.NewsDatabase
import com.example.e_times.models.Article
import com.example.e_times.models.News
import com.example.e_times.network.RetrofitInstance
import retrofit2.Response

class NewsRepository(
    val database:NewsDatabase
) {

    suspend fun getBreakingNews(countryCode:String, pageNumber:Int): Response<News> {

        return RetrofitInstance.api.getBreakingNews(countryCode, pageNumber)
    }

    suspend fun getSearchNews(searchQuery: String, pageNumber: Int) =
        RetrofitInstance.api.getSearchNews(searchQuery, page = pageNumber)


    suspend fun saveArticle(article:Article) = database.getArticleDao().insertArticle(article)

    suspend fun deleteArticle(article:Article) = database.getArticleDao().deleteArticle(article)

    fun getAllFavoriteArticles() = database.getArticleDao().getAllFavoriteArticles()

}