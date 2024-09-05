package com.compfest.chatbothukum

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.compfest.chatbothukum.core.domain.pref.UserPreference
import com.compfest.chatbothukum.core.domain.repository.UserRepository
import com.compfest.chatbothukum.home.HomeViewModel
import com.compfest.chatbothukum.login.LoginViewModel
import com.compfest.chatbothukum.register.RegisterViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(
    private val userRepository: UserRepository,
    private val userPreference: UserPreference
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> LoginViewModel(userRepository)
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> RegisterViewModel(userRepository)
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> HomeViewModel()
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(userRepository, userPreference) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        } as T
    }
}
