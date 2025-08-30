package com.example.myapplication

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.ui.theme.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(navController: NavController) {
    // Animation states
    val scale = remember { Animatable(0.8f) }
    val alpha = remember { Animatable(0f) }
    val progress = remember { Animatable(0f) }

    // Session Manager for checking login state
    val context = LocalContext.current
    val sessionManager = remember { SessionManager(context) }
    val token by sessionManager.authToken.collectAsState(initial = null)

    LaunchedEffect(key1 = true) {
        // Start all animations in parallel
        launch {
            scale.animateTo(
                targetValue = 1f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
        }
        launch {
            alpha.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 1000)
            )
        }
        launch {
            progress.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 2000)
            )
        }

        // Wait for animations to have a nice effect
        delay(2500)

        // Decide the destination based on whether the token exists.
        // By the time the delay is over, the 'token' from DataStore will have been loaded.
        val destination = if (token.isNullOrEmpty()) {
            "login"
        } else {
            "home"
        }

        navController.navigate(destination) {
            // Remove the splash screen from the back stack so the user can't navigate back to it
            popUpTo("splash") { inclusive = true }
        }
    }

    // Your existing animated UI
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(CreamBackground),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .scale(scale.value)
                .alpha(alpha.value)
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Agri Helper Logo",
                modifier = Modifier
                    .size(120.dp)
                    .padding(bottom = 24.dp)
            )
            Text(
                text = "Agri Helper",
                color = GreenPrimary,
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Enhance your farming experience",
                color = TextDark.copy(alpha = 0.7f),
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 32.dp)
            )
            Spacer(Modifier.height(40.dp))
            LinearProgressIndicator(
                progress = progress.value,
                modifier = Modifier
                    .fillMaxWidth(0.4f)
                    .height(4.dp),
                color = GreenPrimary,
                trackColor = GreenLight.copy(alpha = 0.3f)
            )
        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp)
                .alpha(alpha.value)
        ) {
            Text(
                text = "Version 1.0 • © 2025 Agri Helper",
                color = TextDark.copy(alpha = 0.5f),
                fontSize = 12.sp
            )
        }
    }
}

