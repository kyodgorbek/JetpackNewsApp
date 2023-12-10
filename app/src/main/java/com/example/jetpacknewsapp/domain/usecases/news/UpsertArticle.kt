package com.example.jetpacknewsapp.domain.usecases.news

import com.example.jetpacknewsapp.data.local.NewsDao
import com.example.jetpacknewsapp.domain.model.Article

import javax.inject.Inject

class UpsertArticle @Inject constructor(
    private val newsDao: NewsDao
) {

    suspend operator fun invoke(article: Article){
        newsDao.upsert(article = article)
    }

}