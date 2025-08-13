package com.messenger.app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
@Entity(tableName = "chats")
data class Chat(
    @PrimaryKey
    val id: String,
    val name: String,
    val avatar: String? = null,
    val lastMessage: String? = null,
    val lastMessageTime: Long = 0,
    val unreadCount: Int = 0,
    val participants: List<String> = emptyList(),
    val isGroup: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
) : Parcelable
