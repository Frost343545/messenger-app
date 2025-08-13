package com.messenger.app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
@Entity(tableName = "users")
data class User(
    @PrimaryKey
    val id: String,
    val name: String,
    val email: String,
    val phone: String? = null,
    val avatar: String? = null,
    val status: String? = null,
    val isOnline: Boolean = false,
    val lastSeen: Long = 0,
    val createdAt: Long = System.currentTimeMillis()
) : Parcelable
