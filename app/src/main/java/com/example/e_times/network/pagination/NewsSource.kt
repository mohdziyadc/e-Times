package com.example.e_times.network.pagination

import androidx.compose.runtime.mutableStateOf
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.e_times.NewsRepository
import com.example.e_times.models.Article
import retrofit2.HttpException
import java.io.IOException

class NewsSource(private val repository: NewsRepository):PagingSource<Int, Article>() {


    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {

                return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        return try{
            val nextPage = params.key ?:1
            val newsResponse = repository.getBreakingNews("in", pageNumber = nextPage)

            val newsList = newsResponse.body()!!.articles

            LoadResult.Page(
                data = newsList,
                prevKey = if(nextPage ==1) null else nextPage -1,
                nextKey = if(newsList.isEmpty()) null else nextPage+1
            )

        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

}