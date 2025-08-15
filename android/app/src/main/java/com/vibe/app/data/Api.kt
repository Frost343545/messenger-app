package com.vibe.app.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.squareup.moshi.Moshi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

import com.vibe.app.BuildConfig

private const val DEFAULT_BASE = "http://10.0.2.2:4000"

data class AuthResponse(val token: String, val user: User)
data class User(val id: String, val username: String, val displayName: String, val avatarUrl: String?)
data class Conversation(val id: String, val type: String, val title: String?, val lastMessageAt: String?)
data class Message(val id: String, val conversationId: String, val senderId: String, val content: String, val createdAt: String)

interface VibeApi {
    @POST("/api/auth/register")
    suspend fun register(@Body body: Map<String, String>): AuthResponse

    @POST("/api/auth/login")
    suspend fun login(@Body body: Map<String, String>): AuthResponse

    @GET("/api/conversations")
    suspend fun listConversations(@Header("Authorization") bearer: String): Map<String, List<Conversation>>

    @GET("/api/conversations/{id}/messages")
    suspend fun listMessages(
        @Header("Authorization") bearer: String,
        @Path("id") id: String,
        @Query("limit") limit: Int = 50,
        @Query("before") before: String? = null
    ): Map<String, List<Message>>

    @POST("/api/conversations/{id}/messages")
    suspend fun sendMessage(
        @Header("Authorization") bearer: String,
        @Path("id") id: String,
        @Body body: Map<String, String>
    ): Map<String, Message>
}

val Context.dataStore by preferencesDataStore(name = "vibe")

class ApiClient(private val context: Context) {
    companion object {
        val keyToken = stringPreferencesKey("token")
    }

    suspend fun saveToken(token: String) {
        context.dataStore.edit { it[keyToken] = token }
    }

    suspend fun loadToken(): String? {
        val prefs = context.dataStore.data.replayCache.firstOrNull()?.toPreferences()
        return prefs?.get(keyToken)
    }

    fun retrofit(): VibeApi {
        val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
        val client = OkHttpClient.Builder()
            .addInterceptor(logger)
            .addInterceptor(Interceptor { chain ->
                val req = chain.request().newBuilder()
                chain.proceed(req.build())
            })
            .build()
        val moshi = Moshi.Builder().build()
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL.ifEmpty { DEFAULT_BASE })
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(VibeApi::class.java)
    }
}


