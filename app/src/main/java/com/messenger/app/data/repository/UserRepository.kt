package com.messenger.app.data.repository

import com.messenger.app.data.local.dao.UserDao
import com.messenger.app.data.model.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val userDao: UserDao
) {
    
    fun getAllUsers(): Flow<List<User>> = userDao.getAllUsers()
    
    suspend fun getUserById(userId: String): User? = userDao.getUserById(userId)
    
    suspend fun getUsersByIds(userIds: List<String>): List<User> = userDao.getUsersByIds(userIds)
    
    suspend fun insertUser(user: User) = userDao.insertUser(user)
    
    suspend fun insertUsers(users: List<User>) = userDao.insertUsers(users)
    
    suspend fun updateUser(user: User) = userDao.updateUser(user)
    
    suspend fun deleteUser(user: User) = userDao.deleteUser(user)
    
    suspend fun deleteAllUsers() = userDao.deleteAllUsers()
}
