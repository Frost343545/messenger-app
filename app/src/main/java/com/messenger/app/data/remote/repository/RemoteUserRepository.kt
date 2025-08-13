package com.messenger.app.data.remote.repository

import com.messenger.app.data.model.User
import com.messenger.app.data.remote.api.UserApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteUserRepository @Inject constructor(
    private val userApi: UserApi
) {
    
    fun getAllUsers(): Flow<List<User>> = flow {
        try {
            val users = userApi.getAllUsers()
            emit(users)
        } catch (e: Exception) {
            emit(emptyList())
        }
    }
    
    suspend fun getUserById(userId: String): User? {
        return try {
            userApi.getUserById(userId)
        } catch (e: Exception) {
            null
        }
    }
    
    suspend fun createUser(user: User): User? {
        return try {
            userApi.createUser(user)
        } catch (e: Exception) {
            null
        }
    }
    
    suspend fun updateUser(user: User): User? {
        return try {
            userApi.updateUser(user.id, user)
        } catch (e: Exception) {
            null
        }
    }
    
    suspend fun deleteUser(user: User): Boolean {
        return try {
            userApi.deleteUser(user.id)
        } catch (e: Exception) {
            false
        }
    }
    
    suspend fun searchUsers(query: String): List<User> {
        return try {
            userApi.searchUsers(query)
        } catch (e: Exception) {
            emptyList()
        }
    }
    
    suspend fun getOnlineUsers(): List<User> {
        return try {
            userApi.getOnlineUsers()
        } catch (e: Exception) {
            emptyList()
        }
    }
}
