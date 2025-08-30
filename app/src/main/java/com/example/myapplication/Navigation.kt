package com.example.myapplication

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ChatBot.ChatbotScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    // It's best practice to start at the splash screen to check for a valid session.
    NavHost(navController = navController, startDestination = "splash") {

        // --- Session and Auth Flow ---
        composable("splash") { SplashScreen(navController) }
        composable("login") { LoginScreen(navController) }
        composable("signup") { SignUpScreen(navController) }

        // --- Main App Screens ---
        composable("home") { HomeScreen(navController) }

        // --- THIS IS THE MISSING ROUTE ---
        // This line adds the "profile" room to your app's map.
        composable("profile") { UserProfileScreen(navController = navController) }
        // ---

        composable("settings") { SettingsScreen(navController) }
        composable("chatbot") { ChatbotScreen(navController) }
    }
}