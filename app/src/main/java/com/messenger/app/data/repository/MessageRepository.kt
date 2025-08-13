package com.messenger.app.data.repository

import com.messenger.app.data.local.dao.MessageDao
import com.messenger.app.data.model.Message
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MessageRepository @Inject constructor(
    private val messageDao: MessageDao
) {
    
    fun getMessagesByChatId(chatId: String): Flow<List<Message>> = messageDao.getMessagesByChatId(chatId)
    
    suspend fun getMessageById(messageId: String): Message? = messageDao.getMessageById(messageId)
    
    suspend fun getLastMessageByChatId(chatId: String): Message? = messageDao.getLastMessageByChatId(chatId)
    
    suspend fun insertMessage(message: Message) = messageDao.insertMessage(message)
    
    suspend fun insertMessages(messages: List<Message>) = messageDao.insertMessages(messages)
    
    suspend fun updateMessage(message: Message) = messageDao.updateMessage(message)
    
    suspend fun deleteMessage(message: Message) = messageDao.deleteMessage(message)
    
    suspend fun deleteMessagesByChatId(chatId: String) = messageDao.deleteMessagesByChatId(chatId)
    
    suspend fun deleteAllMessages() = messageDao.deleteAllMessages()
    
    suspend fun markMessagesAsRead(chatId: String, currentUserId: String) = 
        messageDao.markMessagesAsRead(chatId, currentUserId)
    
    suspend fun markMessageAsDelivered(messageId: String) = messageDao.markMessageAsDelivered(messageId)
}
