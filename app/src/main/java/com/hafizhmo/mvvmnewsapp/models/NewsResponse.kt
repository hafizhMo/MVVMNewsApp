package com.hafizhmo.mvvmnewsapp.models


import com.hafizhmo.mvvmnewsapp.models.Article

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)