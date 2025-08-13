package com.messenger.app.data.remote.api

import retrofit2.http.*

data class LoginRequest(
    val email: String,
    val password: String
)

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String,
    val phone: String? = null
)

data class AuthResponse(
    val token: String,
    val user: com.messenger.app.data.model.User,
    val expiresAt: Long
)

interface AuthApi {
    
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): AuthResponse
    
    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): AuthResponse
    
    @POST("auth/logout")
    suspend fun logout(): Boolean
    
    @POST("auth/refresh")
    suspend fun refreshToken(@Header("Authorization") token: String): AuthResponse
    
    @GET("auth/me")
    suspend fun getCurrentUser(@Header("Authorization") token: String): com.messenger.app.data.model.User
    
    @POST("auth/forgot-password")
    suspend fun forgotPassword(@Query("email") email: String): Boolean
    
    @POST("auth/reset-password")
    suspend fun resetPassword(
        @Query("token") token: String,
        @Query("newPassword") newPassword: String
    ): Boolean
}
