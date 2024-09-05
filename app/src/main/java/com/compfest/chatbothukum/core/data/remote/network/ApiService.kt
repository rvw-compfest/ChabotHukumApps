package com.compfest.chatbothukum.core.data.remote.network

import com.compfest.chatbothukum.core.data.remote.request.AuthRequest
import com.compfest.chatbothukum.core.data.remote.response.AuthResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("register")
    suspend fun register(@Body request: AuthRequest): Response<AuthResponse>

    @POST("login")
    suspend fun login(@Body request: AuthRequest): Response<AuthResponse>

    @POST("logout")
    suspend fun logout(@Body body: Map<String, String>): Response<AuthResponse>
}