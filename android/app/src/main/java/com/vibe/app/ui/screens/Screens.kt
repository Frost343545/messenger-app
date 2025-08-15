package com.vibe.app.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.vibe.app.data.ApiClient
import com.vibe.app.data.Message
import com.vibe.app.data.SocketManager
import com.vibe.app.ui.Routes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(nav: NavController) {
    val scope = rememberCoroutineScope()
    val ctx = androidx.compose.ui.platform.LocalContext.current
    val api = remember { ApiClient(ctx).retrofit() }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }
    Column(Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text("VIBE", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(16.dp))
        OutlinedTextField(value = username, onValueChange = { username = it }, label = { Text("Username") })
        OutlinedTextField(value = password, onValueChange = { password = it }, label = { Text("Password") }, visualTransformation = PasswordVisualTransformation())
        Spacer(Modifier.height(8.dp))
        Button(onClick = {
            scope.launch {
                try {
                    val resp = api.login(mapOf("username" to username, "password" to password))
                    ApiClient(ctx).saveToken(resp.token)
                    nav.navigate(Routes.ChatList) { popUpTo(Routes.Login) { inclusive = true } }
                } catch (e: Exception) {
                    error = e.message
                }
            }
        }) { Text("Войти") }
        TextButton(onClick = { nav.navigate(Routes.Register) }) { Text("Регистрация") }
        if (error != null) Text(error!!, color = MaterialTheme.colorScheme.error)
    }
}

@Composable
fun RegisterScreen(nav: NavController) {
    val scope = rememberCoroutineScope()
    val ctx = androidx.compose.ui.platform.LocalContext.current
    val api = remember { ApiClient(ctx).retrofit() }
    var username by remember { mutableStateOf("") }
    var displayName by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }
    Column(Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Регистрация", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(16.dp))
        OutlinedTextField(value = username, onValueChange = { username = it }, label = { Text("Username") })
        OutlinedTextField(value = displayName, onValueChange = { displayName = it }, label = { Text("Display name") })
        OutlinedTextField(value = password, onValueChange = { password = it }, label = { Text("Password") }, visualTransformation = PasswordVisualTransformation())
        Spacer(Modifier.height(8.dp))
        Button(onClick = {
            scope.launch {
                try {
                    val resp = api.register(mapOf("username" to username, "password" to password, "displayName" to displayName))
                    ApiClient(ctx).saveToken(resp.token)
                    nav.navigate(Routes.ChatList) { popUpTo(Routes.Register) { inclusive = true } }
                } catch (e: Exception) { error = e.message }
            }
        }) { Text("Создать") }
        if (error != null) Text(error!!, color = MaterialTheme.colorScheme.error)
    }
}

@Composable
fun ChatListScreen(nav: NavController) {
    val ctx = androidx.compose.ui.platform.LocalContext.current
    val api = remember { ApiClient(ctx).retrofit() }
    var token by remember { mutableStateOf<String?>(null) }
    var conversations by remember { mutableStateOf(listOf<com.vibe.app.data.Conversation>()) }
    val scope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        token = ApiClient(ctx).loadToken()
        token?.let {
            val list = api.listConversations("Bearer $it")["conversations"] ?: emptyList()
            conversations = list
        }
    }
    LazyColumn(Modifier.fillMaxSize().padding(8.dp)) {
        items(conversations) { c ->
            ElevatedCard(Modifier.fillMaxWidth().padding(vertical = 6.dp).clickable { nav.navigate("chat/${c.id}") }) {
                Column(Modifier.padding(16.dp)) {
                    Text(c.title ?: "Личный чат", style = MaterialTheme.typography.titleMedium)
                    Text("Последнее: ${c.lastMessageAt ?: "нет"}")
                }
            }
        }
    }
}

@Composable
fun ChatScreen(nav: NavController, conversationId: String) {
    val ctx = androidx.compose.ui.platform.LocalContext.current
    val api = remember { ApiClient(ctx).retrofit() }
    val socket = remember { SocketManager("http://10.0.2.2:4000") }
    var token by remember { mutableStateOf<String?>(null) }
    var messages by remember { mutableStateOf(listOf<Message>()) }
    var input by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        token = ApiClient(ctx).loadToken()
        token?.let { t ->
            socket.connect(t) { _, convId, _, _ ->
                if (convId == conversationId) {
                    scope.launch { // re-fetch recent
                        val list = api.listMessages("Bearer $t", conversationId)["messages"] ?: emptyList()
                        messages = list
                    }
                }
            }
            socket.joinConversation(conversationId)
            val list = api.listMessages("Bearer $t", conversationId)["messages"] ?: emptyList()
            messages = list
        }
    }

    Column(Modifier.fillMaxSize()) {
        LazyColumn(Modifier.weight(1f).fillMaxWidth().padding(8.dp), reverseLayout = true) {
            items(messages) { m ->
                Text("${m.senderId.take(6)}: ${m.content}")
                Spacer(Modifier.height(8.dp))
            }
        }
        Row(Modifier.fillMaxWidth().padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(Modifier.weight(1f), value = input, onValueChange = { input = it }, label = { Text("Сообщение") })
            Spacer(Modifier.width(8.dp))
            Button(onClick = {
                val t = token ?: return@Button
                scope.launch {
                    api.sendMessage("Bearer $t", conversationId, mapOf("content" to input))
                    socket.sendMessage(conversationId, input)
                    input = ""
                }
            }) { Text("Send") }
        }
    }
}


