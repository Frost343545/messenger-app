package com.messenger.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.messenger.app.data.model.User
import com.messenger.app.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactsViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    
    private val _contacts = MutableStateFlow<List<User>>(emptyList())
    val contacts: StateFlow<List<User>> = _contacts.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    init {
        loadContacts()
        loadSampleData()
    }
    
    private fun loadContacts() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                userRepository.getAllUsers().collect { userList ->
                    _contacts.value = userList
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
            val sampleUsers = listOf(
                User(
                    id = "1",
                    name = "Алексей Петров",
                    email = "alexey@example.com",
                    phone = "+7 (999) 123-45-67",
                    status = "Доступен для общения",
                    isOnline = true
                ),
                User(
                    id = "2",
                    name = "Мария Сидорова",
                    email = "maria@example.com",
                    phone = "+7 (999) 234-56-78",
                    status = "Не беспокоить",
                    isOnline = false
                ),
                User(
                    id = "3",
                    name = "Иван Козлов",
                    email = "ivan@example.com",
                    phone = "+7 (999) 345-67-89",
                    status = "На работе",
                    isOnline = true
                ),
                User(
                    id = "4",
                    name = "Анна Волкова",
                    email = "anna@example.com",
                    phone = "+7 (999) 456-78-90",
                    status = "В пути",
                    isOnline = false
                )
            )
            
            sampleUsers.forEach { user ->
                userRepository.insertUser(user)
            }
        }
    }
    
    fun addContact(user: User) {
        viewModelScope.launch {
            userRepository.insertUser(user)
        }
    }
    
    fun deleteContact(user: User) {
        viewModelScope.launch {
            userRepository.deleteUser(user)
        }
    }
}
