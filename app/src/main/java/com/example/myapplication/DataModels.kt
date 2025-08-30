package com.example.myapplication

import com.google.gson.annotations.SerializedName

/**
 * This file contains all the data classes used for network requests and responses.
 * Using @SerializedName ensures that the Kotlin property name can be different from the JSON key name,
 * which is a good practice for clean code.
 */


// --- REQUEST MODELS (What your app SENDS to the server) ---

/**
 * Represents the JSON body for a login request.
 * e.g., { "email": "user@example.com", "password": "password123" }
 */
data class LoginRequest(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String
)

/**
 * Represents the JSON body for a registration/signup request.
 * e.g., { "email": "...", "password": "...", "confirmPassword": "..." }
 */
data class SignupRequest(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("confirmPassword") val confirmPassword: String
)


// --- RESPONSE MODELS (What your app RECEIVES from the server) ---

/**
 * A generic response from the server, typically used for actions
 * that just need a confirmation message, like a successful registration.
 * e.g., { "message": "User registered successfully!" }
 */
data class GenericResponse(
    @SerializedName("message") val message: String
)

/**
 * The response received after a successful login. It contains a message,
 * a JWT token for authentication, and user details.
 */
data class AuthResponse(
    @SerializedName("message") val message: String,
    @SerializedName("token") val token: String?,
    @SerializedName("user") val user: User?
)

/**
 * Represents the user object nested inside the AuthResponse.
 */
data class User(
    // --- THIS IS THE CORRECTED LINE ---
    @SerializedName("id") val id: String,
    @SerializedName("email") val email: String
)

