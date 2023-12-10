package com.example.jetpacknewsapp.domain.usecases.news

import com.example.jetpacknewsapp.data.local.NewsDao
import com.example.jetpacknewsapp.domain.model.Article

class SelectArticle( private val newsDao: NewsDao
) {

    suspend operator fun invoke(url: String):Article?{
        return newsDao.getArticle(url)
    }
}