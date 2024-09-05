package com.compfest.chatbothukum.core.domain.repository

import com.compfest.chatbothukum.core.data.remote.network.ApiService
import com.compfest.chatbothukum.core.data.remote.request.AuthRequest
import com.compfest.chatbothukum.core.data.remote.response.AuthResponse
import com.compfest.chatbothukum.core.domain.pref.UserPreference
import retrofit2.Response

import android.util.Log

class UserRepository(private val apiService: ApiService, private val userPreference: UserPreference) {

    private val TAG = "UserRepository"

    suspend fun register(email: String, password: String): Response<AuthResponse> {
        Log.d(TAG, "Attempting to register with email: $email and password: $password")
        val response = apiService.register(AuthRequest(email, password))
        if (response.isSuccessful) {
            response.body()?.let {
                userPreference.setLoggedIn(true)
                userPreference.setUserEmail(email)
                userPreference.setUid(it.uid)
                Log.d(TAG, "Registration successful. Email stored: $email, UID: ${it.uid}")
            }
        } else {
            Log.d(TAG, "Registration failed.")
        }
        return response
    }

    suspend fun login(email: String, password: String): Response<AuthResponse> {
        Log.d(TAG, "Attempting to login with email: $email and password: $password")
        val response = apiService.login(AuthRequest(email, password))
        if (response.isSuccessful) {
            response.body()?.let {
                userPreference.setLoggedIn(true)
                userPreference.setUserEmail(email)
                userPreference.setUid(it.uid)
                Log.d(TAG, "Login successful. Email stored: $email, UID: ${it.uid}")
            }
        } else {
            Log.d(TAG, "Login failed.")
        }
        return response
    }

    suspend fun logout(): Response<AuthResponse> {
        val token = userPreference.getUid() ?: ""
        Log.d(TAG, "Attempting to logout with token: $token")

        val requestBody = mapOf("token" to token)

        val response = apiService.logout(requestBody)
        if (response.isSuccessful) {
            userPreference.clear()
            Log.d(TAG, "Logout successful. SharedPreferences cleared.")
        } else {
            Log.d(TAG, "Logout failed.")
        }
        return response
    }
}