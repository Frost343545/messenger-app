package com.vibe.app.data

import io.socket.client.IO
import io.socket.client.Socket
import com.vibe.app.BuildConfig

class SocketManager(private val baseUrl: String = BuildConfig.WS_BASE_URL.ifEmpty { "http://10.0.2.2:4000" }) {
    private var socket: Socket? = null

    fun connect(token: String, onMessage: (String, String, String, String) -> Unit) {
        val opts = IO.Options().apply {
            extraHeaders = mapOf("Authorization" to listOf("Bearer $token"))
            reconnection = true
        }
        socket = IO.socket(baseUrl, opts).apply {
            on(Socket.EVENT_CONNECT) { }
            on("message:new") { args ->
                val map = args.getOrNull(0) as? Map<*, *> ?: return@on
                val id = map["id"]?.toString() ?: return@on
                val conversationId = map["conversationId"]?.toString() ?: return@on
                val senderId = map["senderId"]?.toString() ?: return@on
                val content = map["content"]?.toString() ?: return@on
                onMessage(id, conversationId, senderId, content)
            }
            on("typing") { _ -> }
            on("message:read") { _ -> }
            on("presence:update") { _ -> }
            connect()
        }
    }

    fun joinConversation(id: String) {
        socket?.emit("conversation:join", id)
    }

    fun sendMessage(conversationId: String, content: String) {
        socket?.emit("message:send", mapOf("conversationId" to conversationId, "content" to content))
    }

    fun typingStart(conversationId: String) {
        socket?.emit("typing:start", mapOf("conversationId" to conversationId))
    }

    fun typingStop(conversationId: String) {
        socket?.emit("typing:stop", mapOf("conversationId" to conversationId))
    }

    fun markRead(messageId: String, conversationId: String) {
        socket?.emit("message:read", mapOf("messageId" to messageId, "conversationId" to conversationId))
    }

    fun disconnect() {
        socket?.disconnect()
        socket?.off()
        socket = null
    }
}


