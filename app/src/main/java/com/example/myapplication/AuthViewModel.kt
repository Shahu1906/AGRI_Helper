package com.example.myapplication

// AndroidX & Lifecycle Imports

// Coroutines & JSON Imports

// --- ALL NECESSARY IMPORTS FOR YOUR PROJECT ---
import RetrofitInstance
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.IOException


// Sealed class to represent the different states of an API call
sealed class AuthResult<out T> {
    data class Success<out T>(val data: T) : AuthResult<T>()
    data class Error(val message: String) : AuthResult<Nothing>()
    object Loading : AuthResult<Nothing>()
    object Idle : AuthResult<Nothing>() // Represents initial state
}

class AuthViewModel : ViewModel() {

    // StateFlow for the login process
    private val _loginResult = MutableStateFlow<AuthResult<AuthResponse>>(AuthResult.Idle)
    val loginResult: StateFlow<AuthResult<AuthResponse>> = _loginResult

    // StateFlow for the signup process
    private val _signupResult = MutableStateFlow<AuthResult<GenericResponse>>(AuthResult.Idle)
    val signupResult: StateFlow<AuthResult<GenericResponse>> = _signupResult

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            _loginResult.value = AuthResult.Loading
            try {
                val response = RetrofitInstance.api.loginUser(LoginRequest(email, password))
                if (response.isSuccessful) {
                    response.body()?.let {
                        _loginResult.value = AuthResult.Success(it)
                    }
                } else {
                    val errorMsg = response.errorBody()?.string()?.let {
                        JSONObject(it).getString("message")
                    } ?: "An unknown error occurred"
                    _loginResult.value = AuthResult.Error(errorMsg)
                }
            } catch (_: IOException) { // Replaced e with _
                _loginResult.value = AuthResult.Error("Network error. Please check your connection.")
            } catch (e: Exception) {
                _loginResult.value = AuthResult.Error("An unexpected error occurred: ${e.message}")
            }
        }
    }

    fun signupUser(email: String, password: String, confirmPassword: String) {
        viewModelScope.launch {
            _signupResult.value = AuthResult.Loading
            try {
                val request = SignupRequest(email, password, confirmPassword)
                val response = RetrofitInstance.api.registerUser(request)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _signupResult.value = AuthResult.Success(it)
                    }
                } else {
                    val errorMsg = response.errorBody()?.string()?.let {
                        JSONObject(it).getString("message")
                    } ?: "An unknown error occurred"
                    _signupResult.value = AuthResult.Error(errorMsg)
                }
            } catch (_: IOException) { // Replaced e with _
                _signupResult.value = AuthResult.Error("Network error. Please check your connection.")
            } catch (e: Exception) {
                _signupResult.value = AuthResult.Error("An unexpected error occurred: ${e.message}")
            }
        }
    }

    // Function to reset the state, e.g., after navigating away
    fun resetLoginState() {
        _loginResult.value = AuthResult.Idle
    }

    fun resetSignupState() {
        _signupResult.value = AuthResult.Idle
    }
}

