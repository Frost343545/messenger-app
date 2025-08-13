package com.messenger.app.data.repository

import com.messenger.app.data.local.dao.ChatDao
import com.messenger.app.data.model.Chat
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChatRepository @Inject constructor(
    private val chatDao: ChatDao
) {
    
    fun getAllChats(): Flow<List<Chat>> = chatDao.getAllChats()
    
    suspend fun getChatById(chatId: String): Chat? = chatDao.getChatById(chatId)
    
    suspend fun getChatsByIds(chatIds: List<String>): List<Chat> = chatDao.getChatsByIds(chatIds)
    
    suspend fun insertChat(chat: Chat) = chatDao.insertChat(chat)
    
    suspend fun insertChats(chats: List<Chat>) = chatDao.insertChats(chats)
    
    suspend fun updateChat(chat: Chat) = chatDao.updateChat(chat)
    
    suspend fun deleteChat(chat: Chat) = chatDao.deleteChat(chat)
    
    suspend fun deleteAllChats() = chatDao.deleteAllChats()
    
    suspend fun updateLastMessage(chatId: String, message: String, timestamp: Long) = 
        chatDao.updateLastMessage(chatId, message, timestamp)
    
    suspend fun markChatAsRead(chatId: String) = chatDao.markChatAsRead(chatId)
}
