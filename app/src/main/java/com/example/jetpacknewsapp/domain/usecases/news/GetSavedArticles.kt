package com.example.jetpacknewsapp.domain.usecases.news

import com.example.jetpacknewsapp.data.local.NewsDao
import com.example.jetpacknewsapp.domain.model.Article
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSavedArticles @Inject constructor(
    private val newsDao: NewsDao
) {

    operator fun invoke(): Flow<List<Article>> {
        return newsDao.getArticles()
    }

}