package com.example.e_times.models

data class News(
    var articles: List<Article>,
    val status: String,
    val totalResults: Int
)