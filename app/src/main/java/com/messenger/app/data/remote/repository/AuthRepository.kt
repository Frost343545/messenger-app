package com.messenger.app.data.remote.repository

import com.messenger.app.data.model.User
import com.messenger.app.data.remote.api.AuthApi
import com.messenger.app.data.remote.api.AuthResponse
import com.messenger.app.data.remote.api.LoginRequest
import com.messenger.app.data.remote.api.RegisterRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val authApi: AuthApi
) {
    
    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()
    
    private val _authToken = MutableStateFlow<String?>(null)
    val authToken: StateFlow<String?> = _authToken.asStateFlow()
    
    suspend fun login(email: String, password: String): Result<AuthResponse> {
        return try {
            val request = LoginRequest(email, password)
            val response = authApi.login(request)
            _currentUser.value = response.user
            _authToken.value = response.token
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun register(name: String, email: String, password: String, phone: String? = null): Result<AuthResponse> {
        return try {
            val request = RegisterRequest(name, email, password, phone)
            val response = authApi.register(request)
            _currentUser.value = response.user
            _authToken.value = response.token
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun logout(): Result<Boolean> {
        return try {
            val success = authApi.logout()
            if (success) {
                _currentUser.value = null
                _authToken.value = null
            }
            Result.success(success)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun refreshToken(): Result<AuthResponse> {
        return try {
            val currentToken = _authToken.value ?: throw IllegalStateException("No token available")
            val response = authApi.refreshToken("Bearer $currentToken")
            _currentUser.value = response.user
            _authToken.value = response.token
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getCurrentUser(): Result<User> {
        return try {
            val currentToken = _authToken.value ?: throw IllegalStateException("No token available")
            val user = authApi.getCurrentUser("Bearer $currentToken")
            _currentUser.value = user
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun forgotPassword(email: String): Result<Boolean> {
        return try {
            val success = authApi.forgotPassword(email)
            Result.success(success)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun resetPassword(token: String, newPassword: String): Result<Boolean> {
        return try {
            val success = authApi.resetPassword(token, newPassword)
            Result.success(success)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    fun isLoggedIn(): Boolean {
        return _authToken.value != null && _currentUser.value != null
    }
    
    fun getAuthToken(): String? {
        return _authToken.value
    }
}
