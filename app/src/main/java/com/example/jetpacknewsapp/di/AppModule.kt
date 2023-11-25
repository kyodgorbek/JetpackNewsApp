package com.example.jetpacknewsapp.di

import android.app.Application
import com.example.jetpacknewsapp.data.manager.LocalUserManagerImpl
import com.example.jetpacknewsapp.domain.manager.LocalUserManager
import com.example.jetpacknewsapp.domain.usecases.app_entry.AppEntryUseCases
import com.example.jetpacknewsapp.domain.usecases.app_entry.ReadAppEntry
import com.example.jetpacknewsapp.domain.usecases.app_entry.SaveAppEntry
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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
}