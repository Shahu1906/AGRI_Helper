package com.example.myapplication

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myapplication.ui.theme.CreamBackground
import com.example.myapplication.ui.theme.GreenPrimary
import com.example.myapplication.ui.theme.TextDark
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(navController: NavController, authViewModel: AuthViewModel = viewModel()) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current

    // --- ADDED FOR SESSION MANAGEMENT ---
    val sessionManager = remember { SessionManager(context) }
    val scope = rememberCoroutineScope()
    // ---

    // Observe the login result state from the ViewModel
    val loginResult by authViewModel.loginResult.collectAsState()

    // Handle navigation and toasts based on login result
    LaunchedEffect(loginResult) {
        when (val result = loginResult) {
            is AuthResult.Success -> {
                // --- THIS IS THE CRITICAL CHANGE ---
                // Save the token on successful login
                result.data.token?.let { token ->
                    // Launch a coroutine to save the token asynchronously
                    scope.launch {
                        sessionManager.saveAuthToken(token)

                        // Navigate to the home screen after saving
                        navController.navigate("home") {
                            // Clear the entire back stack so user can't go back to login
                            popUpTo(0)
                        }
                    }
                }
                // Reset state after handling
                authViewModel.resetLoginState()
            }
            is AuthResult.Error -> {
                // Show error message in a toast
                Toast.makeText(context, result.message, Toast.LENGTH_LONG).show()
                authViewModel.resetLoginState()
            }
            else -> { /* Handle Idle and Loading states if necessary */ }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(CreamBackground),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth()
        ) {
            // Logo and App Name
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Agri Helper Logo",
                modifier = Modifier.size(80.dp)
            )
            Spacer(Modifier.height(16.dp))
            Text("Agri Helper", color = TextDark, fontSize = 28.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            Text("Agricultural Management Platform", color = TextDark.copy(alpha = 0.7f), fontSize = 14.sp, textAlign = TextAlign.Center)
            Spacer(Modifier.height(32.dp))

            // Email Field
            Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)) {
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email Address", color = TextDark.copy(alpha = 0.7f)) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(focusedContainerColor = Color.Transparent, unfocusedContainerColor = Color.Transparent, focusedTextColor = TextDark, unfocusedTextColor = TextDark, focusedIndicatorColor = GreenPrimary, unfocusedIndicatorColor = Color.Gray.copy(alpha = 0.5f), cursorColor = GreenPrimary),
                    shape = RoundedCornerShape(16.dp)
                )
            }
            Spacer(Modifier.height(16.dp))

            // Password Field
            Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)) {
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password", color = TextDark.copy(alpha = 0.7f)) },
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = PasswordVisualTransformation(),
                    colors = TextFieldDefaults.colors(focusedContainerColor = Color.Transparent, unfocusedContainerColor = Color.Transparent, focusedTextColor = TextDark, unfocusedTextColor = TextDark, focusedIndicatorColor = GreenPrimary, unfocusedIndicatorColor = Color.Gray.copy(alpha = 0.5f), cursorColor = GreenPrimary),
                    shape = RoundedCornerShape(16.dp)
                )
            }
            Spacer(Modifier.height(24.dp))

            // Login Button
            Button(
                onClick = {
                    // Call the ViewModel to perform login
                    if (email.isNotBlank() && password.isNotBlank()) {
                        authViewModel.loginUser(email, password)
                    } else {
                        Toast.makeText(context, "Please enter email and password", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                enabled = loginResult !is AuthResult.Loading, // Disable button while loading
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = GreenPrimary),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp, pressedElevation = 2.dp)
            ) {
                // Show a progress indicator when loading
                if (loginResult is AuthResult.Loading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = Color.White,
                        strokeWidth = 3.dp
                    )
                } else {
                    Text("Sign In", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(Modifier.height(24.dp))

            // Sign up prompt
            Text(
                buildAnnotatedString {
                    append("New to Agri Helper? ")
                    withStyle(style = SpanStyle(color = GreenPrimary, fontWeight = FontWeight.Bold)) {
                        append("Sign Up")
                    }
                },
                fontSize = 15.sp,
                modifier = Modifier
                    .clickable { navController.navigate("signup") }
            )
        }
    }
}

