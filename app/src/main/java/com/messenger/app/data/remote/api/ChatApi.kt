package com.messenger.app.data.remote.api

import com.messenger.app.data.model.Chat
import retrofit2.http.*

interface ChatApi {
    
    @GET("chats")
    suspend fun getAllChats(): List<Chat>
    
    @GET("chats/{id}")
    suspend fun getChatById(@Path("id") chatId: String): Chat
    
    @POST("chats")
    suspend fun createChat(@Body chat: Chat): Chat
    
    @PUT("chats/{id}")
    suspend fun updateChat(@Path("id") chatId: String, @Body chat: Chat): Chat
    
    @DELETE("chats/{id}")
    suspend fun deleteChat(@Path("id") chatId: String): Boolean
    
    @GET("users/{userId}/chats")
    suspend fun getUserChats(@Path("userId") userId: String): List<Chat>
    
    @POST("chats/{chatId}/participants")
    suspend fun addParticipant(
        @Path("chatId") chatId: String,
        @Query("userId") userId: String
    ): Boolean
    
    @DELETE("chats/{chatId}/participants/{userId}")
    suspend fun removeParticipant(
        @Path("chatId") chatId: String,
        @Path("userId") userId: String
    ): Boolean
}
