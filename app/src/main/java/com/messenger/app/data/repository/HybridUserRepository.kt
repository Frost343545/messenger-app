package com.messenger.app.data.repository

import com.messenger.app.data.local.dao.UserDao
import com.messenger.app.data.model.User
import com.messenger.app.data.remote.repository.RemoteUserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HybridUserRepository @Inject constructor(
    private val localUserDao: UserDao,
    private val remoteUserRepository: RemoteUserRepository
) {
    
    fun getAllUsers(): Flow<List<User>> = localUserDao.getAllUsers()
    
    suspend fun getUserById(userId: String): User? {
        // Сначала пытаемся получить из локальной базы
        var user = localUserDao.getUserById(userId)
        
        // Если не найден локально, пробуем удаленно
        if (user == null) {
            user = remoteUserRepository.getUserById(userId)
            // Сохраняем в локальную базу для кэширования
            user?.let { localUserDao.insertUser(it) }
        }
        
        return user
    }
    
    suspend fun insertUser(user: User) {
        // Сохраняем локально
        localUserDao.insertUser(user)
        
        // Пытаемся сохранить удаленно
        try {
            remoteUserRepository.createUser(user)
        } catch (e: Exception) {
            // Логируем ошибку, но не прерываем выполнение
            // TODO: Добавить логирование
        }
    }
    
    suspend fun insertUsers(users: List<User>) {
        // Сохраняем локально
        localUserDao.insertUsers(users)
        
        // Пытаемся сохранить удаленно
        try {
            users.forEach { user ->
                remoteUserRepository.createUser(user)
            }
        } catch (e: Exception) {
            // Логируем ошибку, но не прерываем выполнение
        }
    }
    
    suspend fun updateUser(user: User) {
        // Обновляем локально
        localUserDao.updateUser(user)
        
        // Пытаемся обновить удаленно
        try {
            remoteUserRepository.updateUser(user)
        } catch (e: Exception) {
            // Логируем ошибку, но не прерываем выполнение
        }
    }
    
    suspend fun deleteUser(user: User) {
        // Удаляем локально
        localUserDao.deleteUser(user)
        
        // Пытаемся удалить удаленно
        try {
            remoteUserRepository.deleteUser(user)
        } catch (e: Exception) {
            // Логируем ошибку, но не прерываем выполнение
        }
    }
    
    suspend fun deleteAllUsers() {
        localUserDao.deleteAllUsers()
    }
    
    suspend fun searchUsers(query: String): List<User> {
        return try {
            remoteUserRepository.searchUsers(query)
        } catch (e: Exception) {
            // Если удаленный поиск не удался, возвращаем пустой список
            emptyList()
        }
    }
    
    suspend fun getOnlineUsers(): List<User> {
        return try {
            remoteUserRepository.getOnlineUsers()
        } catch (e: Exception) {
            // Если удаленный запрос не удался, возвращаем пустой список
            emptyList()
        }
    }
    
    suspend fun syncWithRemote() {
        try {
            val remoteUsers = remoteUserRepository.getAllUsers().first()
            localUserDao.insertUsers(remoteUsers)
        } catch (e: Exception) {
            // Логируем ошибку синхронизации
        }
    }
}
