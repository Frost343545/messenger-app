package com.messenger.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.messenger.app.data.model.Chat
import com.messenger.app.data.repository.ChatRepository
import com.messenger.app.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatsViewModel @Inject constructor(
    private val chatRepository: ChatRepository,
    private val userRepository: UserRepository
) : ViewModel() {
    
    private val _chats = MutableStateFlow<List<Chat>>(emptyList())
    val chats: StateFlow<List<Chat>> = _chats.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    init {
        loadChats()
        loadSampleData()
    }
    
    private fun loadChats() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                chatRepository.getAllChats().collect { chatList ->
                    _chats.value = chatList
                }
            } catch (e: Exception) {
                // Handle error
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    private fun loadSampleData() {
        viewModelScope.launch {
            val sampleChats = listOf(
                Chat(
                    id = "1",
                    name = "Алексей Петров",
                    lastMessage = "Привет! Как дела?",
                    lastMessageTime = System.currentTimeMillis() - 300000,
                    unreadCount = 2
                ),
                Chat(
                    id = "2",
                    name = "Мария Сидорова",
                    lastMessage = "Встречаемся завтра?",
                    lastMessageTime = System.currentTimeMillis() - 600000,
                    unreadCount = 0
                ),
                Chat(
                    id = "3",
                    name = "Рабочий чат",
                    lastMessage = "Документы готовы",
                    lastMessageTime = System.currentTimeMillis() - 900000,
                    unreadCount = 5,
                    isGroup = true
                )
            )
            
            sampleChats.forEach { chat ->
                chatRepository.insertChat(chat)
            }
        }
    }
    
    fun markChatAsRead(chatId: String) {
        viewModelScope.launch {
            chatRepository.markChatAsRead(chatId)
        }
    }
    
    fun deleteChat(chat: Chat) {
        viewModelScope.launch {
            chatRepository.deleteChat(chat)
        }
    }
}
