package com.example.e_times.database

import androidx.room.*
import com.example.e_times.models.Article
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDAO {

    @Query("SELECT * FROM articles_table ORDER BY id ASC")
    fun getAllFavoriteArticles(): Flow<List<Article>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(news: Article)

    @Delete
    suspend fun deleteArticle(news: Article)

}

/*
The onConflict strategy is REPLACE bcz if an article already exists in our
favorites db, then the new added article will jzt replace the old one.
 */