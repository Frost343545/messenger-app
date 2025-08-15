package com.vibe.app.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vibe.app.ui.screens.ChatListScreen
import com.vibe.app.ui.screens.ChatScreen
import com.vibe.app.ui.screens.LoginScreen
import com.vibe.app.ui.screens.RegisterScreen

object Routes {
    const val Login = "login"
    const val Register = "register"
    const val ChatList = "chats"
    const val Chat = "chat/{conversationId}"
}

@Composable
fun AppNavHost() {
    val navController: NavHostController = rememberNavController()
    val startDestination = remember { Routes.Login }
    NavHost(navController = navController, startDestination = startDestination) {
        composable(Routes.Login) { LoginScreen(navController) }
        composable(Routes.Register) { RegisterScreen(navController) }
        composable(Routes.ChatList) { ChatListScreen(navController) }
        composable(Routes.Chat) { backStackEntry ->
            val conversationId = backStackEntry.arguments?.getString("conversationId") ?: ""
            ChatScreen(navController, conversationId)
        }
    }
}


