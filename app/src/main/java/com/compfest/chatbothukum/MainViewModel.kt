package com.compfest.chatbothukum

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compfest.chatbothukum.core.domain.pref.UserPreference
import com.compfest.chatbothukum.core.domain.repository.UserRepository
import kotlinx.coroutines.launch

class MainViewModel(
    private val userRepository: UserRepository,
    private val userPreference: UserPreference
) : ViewModel() {

    private val _logoutResult = MutableLiveData<Result<Unit>>()
    val logoutResult: LiveData<Result<Unit>> = _logoutResult

    private val _userEmail = MutableLiveData<String>()
    val userEmail: LiveData<String> = _userEmail

    init {
        fetchUserEmail()
    }

    fun fetchUserEmail() {
        val email = userPreference.getUserEmail()
        Log.d("MainViewModel", "Fetched email: $email")
        _userEmail.value = email ?: "Email not available"
    }

    fun logout() {
        viewModelScope.launch {
            try {
                val response = userRepository.logout()
                if (response.isSuccessful) {
                    userPreference.clear()
                    _logoutResult.value = Result.success(Unit)
                } else {
                    _logoutResult.value = Result.failure(Exception("Logout failed"))
                }
            } catch (e: Exception) {
                _logoutResult.value = Result.failure(e)
            }
        }
    }

    fun isLoggedIn(): Boolean {
        return userPreference.isLoggedIn()
    }
}
