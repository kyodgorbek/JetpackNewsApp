package com.example.jetpacknewsapp.domain.usecases.app_entry

import com.example.jetpacknewsapp.domain.manager.LocalUserManager

class SaveAppEntry(
    private val localUserManager: LocalUserManager
) {
    suspend operator fun invoke(){
        localUserManager.saveAppEntry()
    }
}