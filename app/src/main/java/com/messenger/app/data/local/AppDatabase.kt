package com.messenger.app.data.local

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import android.content.Context
import com.messenger.app.data.model.User
import com.messenger.app.data.model.Chat
import com.messenger.app.data.model.Message
import com.messenger.app.data.local.converter.ListConverter
import com.messenger.app.data.local.dao.UserDao
import com.messenger.app.data.local.dao.ChatDao
import com.messenger.app.data.local.dao.MessageDao

@Database(
    entities = [User::class, Chat::class, Message::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(ListConverter::class)
abstract class AppDatabase : RoomDatabase() {
    
    abstract fun userDao(): UserDao
    abstract fun chatDao(): ChatDao
    abstract fun messageDao(): MessageDao
    
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "messenger_database"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
