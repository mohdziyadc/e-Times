package com.example.e_times.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "articles_table")
data class Article(
    @PrimaryKey(autoGenerate = true)
    var id:Int,
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val source: Source?,
    val title: String?,
    val url: String?,
    val urlToImage: String?
)

/*
NOTE:
The id variable is var and not val bcz it initially should be null bcz we wont be needing an id all the time.
The id variable is only needed when the user favorites a news article.
 */