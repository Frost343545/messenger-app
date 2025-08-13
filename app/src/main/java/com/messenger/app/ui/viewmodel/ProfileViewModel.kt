package com.messenger.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.messenger.app.data.model.User
import com.messenger.app.data.repository.UserRepository
import com.messenger.app.data.remote.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository
) : ViewModel() {
    
    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()
    
    private val _isEditing = MutableStateFlow(false)
    val isEditing: StateFlow<Boolean> = _isEditing.asStateFlow()
    
    init {
        loadCurrentUser()
    }
    
    private fun loadCurrentUser() {
        viewModelScope.launch {
            // For demo purposes, create a sample current user
            val sampleUser = User(
                id = "current_user",
                name = "Иван Иванов",
                email = "ivan@example.com",
                phone = "+7 (999) 111-22-33",
                status = "Доступен для общения",
                isOnline = true
            )
            
            userRepository.insertUser(sampleUser)
            _currentUser.value = sampleUser
        }
    }
    
    fun startEditing() {
        _isEditing.value = true
    }
    
    fun cancelEditing() {
        _isEditing.value = false
    }
    
    fun saveProfile() {
        viewModelScope.launch {
            _currentUser.value?.let { user ->
                userRepository.updateUser(user)
            }
            _isEditing.value = false
        }
    }
    
    fun logout() {
        viewModelScope.launch {
            try {
                authRepository.logout()
                // TODO: Navigate to login screen
            } catch (e: Exception) {
                // Handle logout error
            }
        }
    }
    
    fun updateUserStatus(status: String) {
        viewModelScope.launch {
            _currentUser.value?.let { user ->
                val updatedUser = user.copy(status = status)
                userRepository.updateUser(updatedUser)
                _currentUser.value = updatedUser
            }
        }
    }
    
    fun updateUserName(name: String) {
        viewModelScope.launch {
            _currentUser.value?.let { user ->
                val updatedUser = user.copy(name = name)
                userRepository.updateUser(updatedUser)
                _currentUser.value = updatedUser
            }
        }
    }
}
