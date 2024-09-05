package com.compfest.chatbothukum.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.compfest.chatbothukum.core.domain.model.Result
import com.compfest.chatbothukum.core.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers

class RegisterViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun register(email: String, password: String) = liveData(Dispatchers.IO) {
        emit(Result.Loading)
        try {
            val response = userRepository.register(email, password)
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(Result.Success(it))
                } ?: emit(Result.Error("Unexpected empty response"))
            } else {
                emit(Result.Error("Registration failed: ${response.message()}"))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }
}
