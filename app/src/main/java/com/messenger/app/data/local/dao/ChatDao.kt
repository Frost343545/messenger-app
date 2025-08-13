package com.messenger.app.data.local.dao

import androidx.room.*
import com.messenger.app.data.model.Chat
import kotlinx.coroutines.flow.Flow

@Dao
interface ChatDao {
    
    @Query("SELECT * FROM chats ORDER BY lastMessageTime DESC")
    fun getAllChats(): Flow<List<Chat>>
    
    @Query("SELECT * FROM chats WHERE id = :chatId")
    suspend fun getChatById(chatId: String): Chat?
    
    @Query("SELECT * FROM chats WHERE id IN (:chatIds)")
    suspend fun getChatsByIds(chatIds: List<String>): List<Chat>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChat(chat: Chat)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChats(chats: List<Chat>)
    
    @Update
    suspend fun updateChat(chat: Chat)
    
    @Delete
    suspend fun deleteChat(chat: Chat)
    
    @Query("DELETE FROM chats")
    suspend fun deleteAllChats()
    
    @Query("UPDATE chats SET lastMessage = :message, lastMessageTime = :timestamp, unreadCount = unreadCount + 1 WHERE id = :chatId")
    suspend fun updateLastMessage(chatId: String, message: String, timestamp: Long)
    
    @Query("UPDATE chats SET unreadCount = 0 WHERE id = :chatId")
    suspend fun markChatAsRead(chatId: String)
}
