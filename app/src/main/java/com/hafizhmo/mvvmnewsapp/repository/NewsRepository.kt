package com.hafizhmo.mvvmnewsapp.repository

import com.hafizhmo.mvvmnewsapp.api.RetrofitInstance
import com.hafizhmo.mvvmnewsapp.db.ArticleDatabase

class NewsRepository(
    val db: ArticleDatabase
) {
    suspend fun getBreakingNews(countryCode: String, pageNumber: Int) =
        RetrofitInstance.api.getBreakingNews(countryCode, pageNumber)
}