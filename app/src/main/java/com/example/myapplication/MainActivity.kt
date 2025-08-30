package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ChatBot.ChatbotScreen
import com.example.myapplication.ui.theme.SmartFarmingTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SmartFarmingTheme {
                val navController = rememberNavController()
                Surface(modifier = Modifier.fillMaxSize()) {
                    // This NavHost is the definitive "map" for your app.
                    NavHost(navController = navController, startDestination = "splash") {

                        // --- Session and Auth Flow ---
                        composable("splash") { SplashScreen(navController) }
                        composable("login") { LoginScreen(navController) }
                        composable("signup") { SignUpScreen(navController) }

                        // --- Main App Screens ---
                        composable("home") { HomeScreen(navController) }

                        // --- THIS IS THE MISSING LINE THAT FIXES THE CRASH ---
                        composable("profile") { UserProfileScreen(navController = navController) }
                        // ---

                        composable("settings") { SettingsScreen(navController) }
                        composable("chatbot") { ChatbotScreen(navController) }
                    }
                }
            }
        }
    }
}