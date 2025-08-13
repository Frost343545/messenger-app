package com.messenger.app.data.remote.api

import com.messenger.app.data.model.Message
import retrofit2.http.*

interface MessageApi {
    
    @GET("chats/{chatId}/messages")
    suspend fun getMessagesByChatId(
        @Path("chatId") chatId: String,
        @Query("limit") limit: Int = 50,
        @Query("offset") offset: Int = 0
    ): List<Message>
    
    @GET("messages/{id}")
    suspend fun getMessageById(@Path("id") messageId: String): Message
    
    @POST("messages")
    suspend fun sendMessage(@Body message: Message): Message
    
    @PUT("messages/{id}")
    suspend fun updateMessage(@Path("id") messageId: String, @Body message: Message): Message
    
    @DELETE("messages/{id}")
    suspend fun deleteMessage(@Path("id") messageId: String): Boolean
    
    @POST("messages/{id}/read")
    suspend fun markMessageAsRead(@Path("id") messageId: String): Boolean
    
    @POST("messages/{id}/delivered")
    suspend fun markMessageAsDelivered(@Path("id") messageId: String): Boolean
    
    @GET("chats/{chatId}/messages/unread")
    suspend fun getUnreadMessages(@Path("chatId") chatId: String): List<Message>
}
