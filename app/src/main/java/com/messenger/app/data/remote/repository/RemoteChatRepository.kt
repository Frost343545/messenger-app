package com.messenger.app.data.remote.repository

import com.messenger.app.data.model.Chat
import com.messenger.app.data.remote.api.ChatApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteChatRepository @Inject constructor(
    private val chatApi: ChatApi
) {
    
    fun getAllChats(): Flow<List<Chat>> = flow {
        try {
            val chats = chatApi.getAllChats()
            emit(chats)
        } catch (e: Exception) {
            emit(emptyList())
        }
    }
    
    suspend fun getChatById(chatId: String): Chat? {
        return try {
            chatApi.getChatById(chatId)
        } catch (e: Exception) {
            null
        }
    }
    
    suspend fun createChat(chat: Chat): Chat? {
        return try {
            chatApi.createChat(chat)
        } catch (e: Exception) {
            null
        }
    }
    
    suspend fun updateChat(chat: Chat): Chat? {
        return try {
            chatApi.updateChat(chat.id, chat)
        } catch (e: Exception) {
            null
        }
    }
    
    suspend fun deleteChat(chat: Chat): Boolean {
        return try {
            chatApi.deleteChat(chat.id)
        } catch (e: Exception) {
            false
        }
    }
    
    suspend fun getUserChats(userId: String): List<Chat> {
        return try {
            chatApi.getUserChats(userId)
        } catch (e: Exception) {
            emptyList()
        }
    }
    
    suspend fun addParticipant(chatId: String, userId: String): Boolean {
        return try {
            chatApi.addParticipant(chatId, userId)
        } catch (e: Exception) {
            false
        }
    }
    
    suspend fun removeParticipant(chatId: String, userId: String): Boolean {
        return try {
            chatApi.removeParticipant(chatId, userId)
        } catch (e: Exception) {
            false
        }
    }
}
