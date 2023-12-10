package com.example.jetpacknewsapp.presentation.bookmark

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.example.jetpacknewsapp.domain.model.Article

data class BookmarkState(
    val articles:List<Article> = emptyList()
)