//package com.example.agrihelper.ui.screens
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.*
//import androidx.compose.material3.*
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavController
//import kotlinx.coroutines.delay
//
//@Composable
//fun SplashScreen(navController: NavController) {
//    // Navigate after delay
//    LaunchedEffect(Unit) {
//        delay(2000) // 2 seconds
//        navController.navigate("login") {
//            popUpTo("splash") { inclusive = true } // remove splash from backstack
//        }
//    }
//
//    // UI of splash
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color(0xFF1B5E20)), // Dark green background
//        contentAlignment = Alignment.Center
//    ) {
//        Column(horizontalAlignment = Alignment.CenterHorizontally) {
//            Text(
//                text = "🌱 AgriHelper",
//                color = Color.White,
//                fontSize = 36.sp,
//                fontWeight = FontWeight.Bold
//            )
//            Spacer(modifier = Modifier.height(16.dp))
//            CircularProgressIndicator(color = Color.White, strokeWidth = 3.dp)
//        }
//    }
//}
