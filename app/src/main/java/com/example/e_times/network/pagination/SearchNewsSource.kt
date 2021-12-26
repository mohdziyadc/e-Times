package com.example.e_times.network.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.e_times.NewsRepository
import com.example.e_times.models.Article
import kotlinx.coroutines.delay
import retrofit2.HttpException
import java.io.IOException

class SearchNewsSource(private val repository: NewsRepository,val searchQuery:String): PagingSource<Int, Article>() {
    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        return try {
            val currentPage = params.key?:1
            val searchResponse = repository.getSearchNews(searchQuery = searchQuery, pageNumber = currentPage)
            val searchNewsList = searchResponse.body()!!.articles
                delay(1000)
                LoadResult.Page(
                    data = searchNewsList,
                    prevKey = if(currentPage ==1) null else currentPage-1,
                    nextKey = if(searchNewsList.isEmpty()) null else currentPage+1
                )




        }catch (e:IOException){
            return LoadResult.Error(e)
        }catch (e:HttpException){
            return LoadResult.Error(e)
        }

    }
}