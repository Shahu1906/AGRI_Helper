package com.example.myapplication

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myapplication.ui.theme.CreamBackground
import com.example.myapplication.ui.theme.GreenPrimary
import com.example.myapplication.ui.theme.TextDark


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(navController: NavController, authViewModel: AuthViewModel = viewModel()) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }
    val context = LocalContext.current

    // Observe signup result state
    val signupResult by authViewModel.signupResult.collectAsState()
    var errorMessage by remember { mutableStateOf("") }

    // Handle side-effects from the ViewModel
    LaunchedEffect(signupResult) {
        when (val result = signupResult) {
            is AuthResult.Success -> {
                Toast.makeText(context, result.data.message, Toast.LENGTH_LONG).show()
                // On success, go back to the login screen
                navController.popBackStack()
                authViewModel.resetSignupState()
            }
            is AuthResult.Error -> {
                // Show the error message from the server/viewmodel
                errorMessage = result.message
                authViewModel.resetSignupState()
            }
            else -> { /* Idle or Loading */ }
        }
    }

    Scaffold(
        // ... Your existing TopAppBar
        topBar = {
            CenterAlignedTopAppBar(title = { Text("Create Account", color = TextDark, fontSize = 20.sp, fontWeight = FontWeight.SemiBold) }, navigationIcon = { IconButton(onClick = { navController.popBackStack() }, modifier = Modifier.background(Color.White.copy(alpha = 0.8f), RoundedCornerShape(12.dp)).padding(4.dp).size(40.dp)) { Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = TextDark) } }, colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = CreamBackground.copy(alpha = 0.95f)))
        },
        containerColor = CreamBackground
    ) { padding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 16.dp)
        ) {
            // ... Your existing UI code (Logo, Text Fields, etc.)
            Image(painter = painterResource(id = R.drawable.logo), contentDescription = "Agri Helper Logo", modifier = Modifier.size(80.dp))
            Spacer(Modifier.height(16.dp))
            Text("Join Agri Helper", color = TextDark, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            Text("Create your account to get started", color = TextDark.copy(alpha = 0.7f), fontSize = 14.sp, textAlign = TextAlign.Center)
            Spacer(Modifier.height(32.dp))
            // Email
            Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)) { OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email Address", color = TextDark.copy(alpha = 0.7f)) }, modifier = Modifier.fillMaxWidth(), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email), colors = TextFieldDefaults.colors(focusedContainerColor = Color.Transparent, unfocusedContainerColor = Color.Transparent, focusedTextColor = TextDark, unfocusedTextColor = TextDark, focusedIndicatorColor = GreenPrimary, unfocusedIndicatorColor = Color.Gray.copy(alpha = 0.5f), cursorColor = GreenPrimary), shape = RoundedCornerShape(16.dp)) }
            Spacer(Modifier.height(16.dp))
            // Password
            Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)) { OutlinedTextField(value = password, onValueChange = { password = it }, label = { Text("Password", color = TextDark.copy(alpha = 0.7f)) }, modifier = Modifier.fillMaxWidth(), visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password), trailingIcon = { IconButton(onClick = { passwordVisible = !passwordVisible }) { Icon(imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff, contentDescription = if (passwordVisible) "Hide password" else "Show password", tint = TextDark.copy(alpha = 0.5f)) } }, colors = TextFieldDefaults.colors(focusedContainerColor = Color.Transparent, unfocusedContainerColor = Color.Transparent, focusedTextColor = TextDark, unfocusedTextColor = TextDark, focusedIndicatorColor = GreenPrimary, unfocusedIndicatorColor = Color.Gray.copy(alpha = 0.5f), cursorColor = GreenPrimary), shape = RoundedCornerShape(16.dp)) }
            Spacer(Modifier.height(16.dp))
            // Confirm Password
            Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)) { OutlinedTextField(value = confirmPassword, onValueChange = { confirmPassword = it }, label = { Text("Confirm Password", color = TextDark.copy(alpha = 0.7f)) }, modifier = Modifier.fillMaxWidth(), visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password), trailingIcon = { IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) { Icon(imageVector = if (confirmPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff, contentDescription = if (confirmPasswordVisible) "Hide password" else "Show password", tint = TextDark.copy(alpha = 0.5f)) } }, colors = TextFieldDefaults.colors(focusedContainerColor = Color.Transparent, unfocusedContainerColor = Color.Transparent, focusedTextColor = TextDark, unfocusedTextColor = TextDark, focusedIndicatorColor = GreenPrimary, unfocusedIndicatorColor = Color.Gray.copy(alpha = 0.5f), cursorColor = GreenPrimary), shape = RoundedCornerShape(16.dp)) }
            Spacer(Modifier.height(16.dp))

            // Error Message
            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 13.sp,
                    modifier = Modifier.padding(4.dp)
                )
            }

            Spacer(Modifier.height(24.dp))

            // Sign Up Button
            Button(
                onClick = {
                    // Perform client-side validation first
                    if (email.isBlank() || password.isBlank()) {
                        errorMessage = "Email and password cannot be empty"
                    } else if (password != confirmPassword) {
                        errorMessage = "Passwords do not match"
                    } else {
                        errorMessage = ""
                        // Call ViewModel to perform signup
                        authViewModel.signupUser(email, password, confirmPassword)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                enabled = signupResult !is AuthResult.Loading, // Disable button while loading
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = GreenPrimary),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp, pressedElevation = 2.dp)
            ) {
                if (signupResult is AuthResult.Loading) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp), color = Color.White, strokeWidth = 3.dp)
                } else {
                    Text("Create Account", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(Modifier.height(24.dp))

            // ... Your existing UI code (Terms, Sign in prompt)
            Text("By creating an account, you agree to our Terms of Service and Privacy Policy", color = TextDark.copy(alpha = 0.6f), fontSize = 12.sp, textAlign = TextAlign.Center, modifier = Modifier.padding(horizontal = 16.dp))
            Spacer(Modifier.height(24.dp))
            Text(buildAnnotatedString { append("Already have an account? "); withStyle(style = SpanStyle(color = GreenPrimary, fontWeight = FontWeight.Bold)) { append("Sign In") } }, fontSize = 15.sp, modifier = Modifier.clickable { navController.popBackStack() })

        }
    }
}
