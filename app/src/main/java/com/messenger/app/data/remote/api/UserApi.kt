package com.messenger.app.data.remote.api

import com.messenger.app.data.model.User
import retrofit2.http.*

interface UserApi {
    
    @GET("users")
    suspend fun getAllUsers(): List<User>
    
    @GET("users/{id}")
    suspend fun getUserById(@Path("id") userId: String): User
    
    @POST("users")
    suspend fun createUser(@Body user: User): User
    
    @PUT("users/{id}")
    suspend fun updateUser(@Path("id") userId: String, @Body user: User): User
    
    @DELETE("users/{id}")
    suspend fun deleteUser(@Path("id") userId: String): Boolean
    
    @GET("users/search")
    suspend fun searchUsers(@Query("query") query: String): List<User>
    
    @GET("users/online")
    suspend fun getOnlineUsers(): List<User>
}
