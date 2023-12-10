package com.example.jetpacknewsapp.di

import android.app.Application
import androidx.room.Room
import com.example.jetpacknewsapp.data.local.NewsDao
import com.example.jetpacknewsapp.data.local.NewsDatabase
import com.example.jetpacknewsapp.data.local.NewsTypeConvertor
import com.example.jetpacknewsapp.data.manager.LocalUserManagerImpl
import com.example.jetpacknewsapp.data.remote.NewsApi
import com.example.jetpacknewsapp.data.repository.NewsRepositoryImpl
import com.example.jetpacknewsapp.domain.manager.LocalUserManager
import com.example.jetpacknewsapp.domain.repository.NewsRepository
import com.example.jetpacknewsapp.domain.usecases.app_entry.AppEntryUseCases
import com.example.jetpacknewsapp.domain.usecases.app_entry.ReadAppEntry
import com.example.jetpacknewsapp.domain.usecases.app_entry.SaveAppEntry
import com.example.jetpacknewsapp.domain.usecases.news.DeleteArticle
import com.example.jetpacknewsapp.domain.usecases.news.GetNews
import com.example.jetpacknewsapp.domain.usecases.news.NewsUseCases
import com.example.jetpacknewsapp.domain.usecases.news.SearchNews
import com.example.jetpacknewsapp.domain.usecases.news.SelectArticle
import com.example.jetpacknewsapp.domain.usecases.news.SelectArticles
import com.example.jetpacknewsapp.domain.usecases.news.UpsertArticle
import com.example.jetpacknewsapp.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLocalUserManager(
        application: Application
    ):LocalUserManager = LocalUserManagerImpl(application)

    @Provides
    @Singleton
    fun provideAppEntryUsesCases(
        localUserManager: LocalUserManager
    ) = AppEntryUseCases(
        readAppEntry = ReadAppEntry(
            localUserManager
        ),
        saveAppEntry = SaveAppEntry(localUserManager)
    )


    @Provides
    @Singleton
    fun provideNewsApi():NewsApi{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApi::class.java)


    }

    @Provides
    @Singleton
    fun provideNewsRepository(newsApi: NewsApi):NewsRepository = NewsRepositoryImpl(newsApi)

    @Provides
    @Singleton
    fun provideNewsUsesCases(newsRepository: NewsRepository, newsDao: NewsDao):NewsUseCases{
        return NewsUseCases(
            getNews = GetNews(newsRepository),
            searchNews = SearchNews(newsRepository),
            upsertArticle = UpsertArticle(newsDao),
            deleteArticle = DeleteArticle(newsDao),
            selectArticles = SelectArticles(newsDao),
            selectArticle = SelectArticle(newsDao)
        )
    }

    @Provides
    @Singleton
    fun provideNewsDatabase(
        application: Application
    ): NewsDatabase {
        return Room.databaseBuilder(
            context = application,
             NewsDatabase::class.java,
            name = "news_db"
        ).addTypeConverter(NewsTypeConvertor())
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideNewsDao(
        newsDatabase: NewsDatabase
    ): NewsDao = newsDatabase.newsDao

}