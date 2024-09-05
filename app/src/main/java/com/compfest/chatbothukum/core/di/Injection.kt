package com.compfest.chatbothukum.core.di

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.compfest.chatbothukum.ViewModelFactory
import com.compfest.chatbothukum.core.data.remote.network.ApiConfig
import com.compfest.chatbothukum.core.data.remote.network.ApiService
import com.compfest.chatbothukum.core.domain.pref.UserPreference
import com.compfest.chatbothukum.core.domain.repository.UserRepository

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val apiService = ApiConfig.createService(ApiService::class.java)
        val userPreference = UserPreference(context)
        return UserRepository(apiService, userPreference)
    }

    fun provideViewModelFactory(context: Context): ViewModelProvider.Factory {
        val userPreference = UserPreference(context)
        val repository = provideRepository(context)
        return ViewModelFactory(repository, userPreference)
    }
}
