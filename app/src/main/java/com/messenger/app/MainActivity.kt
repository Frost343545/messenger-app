package com.messenger.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.messenger.app.ui.screens.ChatsScreen
import com.messenger.app.ui.screens.ContactsScreen
import com.messenger.app.ui.screens.ProfileScreen
import com.messenger.app.ui.screens.LoginScreen
import com.messenger.app.ui.theme.MessengerTheme
import com.messenger.app.ui.components.BottomNavigation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MessengerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MessengerApp()
                }
            }
        }
    }
}

@Composable
fun MessengerApp() {
    val navController = rememberNavController()
    
    NavHost(
        navController = navController, 
        startDestination = "login"
    ) {
        composable("login") {
            LoginScreen(navController = navController)
        }
        composable("main") {
            MainContent(navController = navController)
        }
    }
}

@Composable
fun MainContent(navController: NavHostController) {
    Scaffold(
        bottomBar = {
            BottomNavigation(navController = navController)
        }
    ) { paddingValues ->
        NavHost(
            navController = navController, 
            startDestination = "chats",
            modifier = Modifier.padding(paddingValues)
        ) {
            composable("chats") {
                ChatsScreen(navController = navController)
            }
            composable("contacts") {
                ContactsScreen(navController = navController)
            }
            composable("profile") {
                ProfileScreen(navController = navController)
            }
        }
    }
}
