package com.messenger.app.data.remote.repository

import com.messenger.app.data.model.Message
import com.messenger.app.data.remote.api.MessageApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteMessageRepository @Inject constructor(
    private val messageApi: MessageApi
) {
    
    fun getMessagesByChatId(chatId: String): Flow<List<Message>> = flow {
        try {
            val messages = messageApi.getMessagesByChatId(chatId)
            emit(messages)
        } catch (e: Exception) {
            emit(emptyList())
        }
    }
    
    suspend fun getMessageById(messageId: String): Message? {
        return try {
            messageApi.getMessageById(messageId)
        } catch (e: Exception) {
            null
        }
    }
    
    suspend fun sendMessage(message: Message): Message? {
        return try {
            messageApi.sendMessage(message)
        } catch (e: Exception) {
            null
        }
    }
    
    suspend fun updateMessage(message: Message): Message? {
        return try {
            messageApi.updateMessage(message.id, message)
        } catch (e: Exception) {
            null
        }
    }
    
    suspend fun deleteMessage(message: Message): Boolean {
        return try {
            messageApi.deleteMessage(message.id)
        } catch (e: Exception) {
            false
        }
    }
    
    suspend fun markMessageAsRead(messageId: String): Boolean {
        return try {
            messageApi.markMessageAsRead(messageId)
        } catch (e: Exception) {
            false
        }
    }
    
    suspend fun markMessageAsDelivered(messageId: String): Boolean {
        return try {
            messageApi.markMessageAsDelivered(messageId)
        } catch (e: Exception) {
            false
        }
    }
    
    suspend fun getUnreadMessages(chatId: String): List<Message> {
        return try {
            messageApi.getUnreadMessages(chatId)
        } catch (e: Exception) {
            emptyList()
        }
    }
}
