package com.example.e_times

import android.app.Application
import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.e_times.Utils.Constants.Companion.PAGE_SIZE
import com.example.e_times.Utils.Resource
import com.example.e_times.database.NewsDatabase
import com.example.e_times.models.Article
import com.example.e_times.models.News
import com.example.e_times.network.pagination.NewsSource
import com.example.e_times.network.pagination.SearchNewsSource
import com.example.e_times.screens.SearchBar
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.lang.IllegalArgumentException

private const val TAG = "NewsViewModel"

class NewsViewModel(application: Application) : AndroidViewModel(application) {

    private val database = NewsDatabase.getDatabase(application)
    private val repository: NewsRepository = NewsRepository(database)
//    val breakingNewsList = mutableStateOf<Resource<News>>(Resource.Loading())
//    val responseState = mutableStateOf(false)
//    private var breakingNewsPage = 1


    val searchNewsList = mutableStateOf<Resource<News>>(Resource.Loading())
    val searchNewsPage = mutableStateOf(1)
    val searchText = mutableStateOf("")
    private var scrollPosition =0
    val searchResponseState = mutableStateOf(false)
    val exceptionOccurred = mutableStateOf(false)

    val favoritesList:MutableState<List<Article>> = mutableStateOf(listOf())

    val article: Flow<PagingData<Article>> = Pager(PagingConfig(pageSize = 5)){
        NewsSource(repository)
    }.flow.cachedIn(viewModelScope)





//
//    fun getBreakingNews(countryCode: String) {
//        viewModelScope.launch {
//            delay(500L)
//            val response = repository.getBreakingNews(countryCode, breakingNewsPage)
//            breakingNewsList.value = handleNewsResponse(response)
//            responseState.value = true
//        }
//    }

    fun getSearchNews() {
        viewModelScope.launch {
            searchResponseState.value = true
            delay(500L)
            val response = try{
                repository.getSearchNews(searchText.value, searchNewsPage.value)
            } catch (e:HttpException){
                Log.d(TAG,"Http Exception occurred")
                exceptionOccurred.value = true
                return@launch
            }catch (e:IOException){
                Log.d(TAG,"IO Exception occurred")
                exceptionOccurred.value = true
                return@launch
            }catch (e:NullPointerException){
                Log.d(TAG,"Null Pointer Exception occurred")
                exceptionOccurred.value = true
                return@launch
            }
            searchNewsList.value = handleSearchNews(response)
            searchResponseState.value = false
        }
    }
    fun nextPage(){
        viewModelScope.launch {
            /*
            Create a lock in order to prevent multiple recompositions once we reach the end of \
            the page.
             */
            if((scrollPosition+1) >= (searchNewsPage.value*PAGE_SIZE)){
                searchResponseState.value = true
                incrementPage()
                Log.d(TAG, "Page Number:${searchNewsPage.value}")

                if(searchNewsPage.value>1){

                    val response = try{
                        repository.getSearchNews(
                            searchQuery = searchText.value,
                            pageNumber = searchNewsPage.value
                        )
                    } catch (e:HttpException){
                        Log.d(TAG,"Http Exception occured")
                        exceptionOccurred.value = true
                        return@launch
                    }catch (e:IOException){
                        Log.d(TAG,"IO Exception occured")
                        exceptionOccurred.value = true
                        return@launch
                    }catch (e:NullPointerException){
                        Log.d(TAG,"Null Pointer Exception occured")
                        exceptionOccurred.value = true
                        return@launch
                    }
                    Log.d(TAG,"The response is: $response")

                    if(response.isSuccessful){
                        response.body()?.let { appendArticles(it.articles) }
                    }

                }
                searchResponseState.value = false


            }
        }
    }
    private fun incrementPage(){
        searchNewsPage.value++
    }

    fun checkScrollPosition(position:Int){
        scrollPosition = position
    }

    private fun appendArticles(articles:List<Article>){
        searchNewsList.value.data?.let {
            val current = ArrayList(it.articles)
            current.addAll(articles)
            it.articles = current
        }

    }

    private fun handleNewsResponse(response: Response<News>): Resource<News> {
        if (response.isSuccessful) {

//            if(response.body() != null){
//                val success = Resource.Success(response.body()!!)  //we don't know if some other thread accesses the response.body(). Hence let keyword should be used.
//                return success
//            }
            //The above lines of code is translated by using the let keyword.
            //The let keyword is a much better option than the above code bcz it handles
            //concurrency whereas the if block doesn't.

            response.body()?.let { newsResponse ->
                return Resource.Success(newsResponse)
            }


        }
        return Resource.Failure(response.message())

    }

    private fun handleSearchNews(response: Response<News>): Resource<News> {
        if (response.isSuccessful) {

            response.body()?.let {
                return Resource.Success(it)
            }

        }
        return Resource.Failure(response.message())
    }

    fun saveArticle(article: Article){
        viewModelScope.launch {
            repository.saveArticle(article = article)
        }
    }

    fun deleteArticle(article: Article){
        viewModelScope.launch {
            repository.deleteArticle(article =article)
        }
    }

    fun getAllFavorites(){
        viewModelScope.launch {
            val flowListArticle = repository.getAllFavoriteArticles()
            flowListArticle.collect{ articleList ->

                favoritesList.value = articleList
            }
        }



    }



}

