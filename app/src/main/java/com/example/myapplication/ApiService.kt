package com.example.myapplication

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

/**
 * The central interface for defining all API endpoints for Retrofit.
 */
interface ApiService {

    // --- Authentication Endpoints (Existing) ---

    @POST("api/users/login")
    suspend fun loginUser(@Body loginRequest: LoginRequest): Response<AuthResponse>

    @POST("api/users/register")
    suspend fun registerUser(@Body signupRequest: SignupRequest): Response<GenericResponse>


    // --- User Profile Endpoints (Newly Added) ---

    /**
     * Fetches the profile of the currently authenticated user.
     * @param token The user's JWT, passed in the Authorization header.
     */
    @GET("api/profile")
    suspend fun getUserProfile(@Header("Authorization") token: String): Response<UserProfile>

    /**
     * Creates or updates the profile for the currently authenticated user.
     * @param token The user's JWT for authentication.
     * @param profileData The updated profile information.
     */
    @POST("api/profile")
    suspend fun updateUserProfile(
        @Header("Authorization") token: String,
        @Body profileData: UpdateProfileRequest
    ): Response<UserProfile>
}