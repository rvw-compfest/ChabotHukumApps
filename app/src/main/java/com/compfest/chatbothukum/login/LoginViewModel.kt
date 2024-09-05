package com.compfest.chatbothukum.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.compfest.chatbothukum.core.domain.model.Result
import com.compfest.chatbothukum.core.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers

class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val TAG = "LoginViewModel"

    fun login(email: String, password: String) = liveData(Dispatchers.IO) {
        Log.d(TAG, "Attempting login with email: $email and password: $password")
        emit(Result.Loading)
        try {
            val response = userRepository.login(email, password)
            if (response.isSuccessful) {
                response.body()?.let {
                    Log.d(TAG, "Login successful for email: $email")
                    emit(Result.Success(it))
                } ?: run {
                    Log.d(TAG, "Login response body is null")
                    emit(Result.Error("Login failed: response body is null"))
                }
            } else {
                Log.d(TAG, "Login failed: ${response.message()} (Code: ${response.code()})")
                emit(Result.Error("Login failed: ${response.message()}"))
            }
        } catch (e: Exception) {
            Log.e(TAG, "Login exception: ${e.localizedMessage ?: "An unexpected error occurred"}")
            emit(Result.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }
}
