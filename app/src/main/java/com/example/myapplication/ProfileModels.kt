package com.example.myapplication

import com.google.gson.annotations.SerializedName

/**
 * Represents the complete user profile data received from the server.
 * This class should match the JSON object the server sends.
 */
data class UserProfile(
    @SerializedName("_id") val id: String?,
    val name: String?,
    val email: String?,
    val location: String?,
    val contactNumber: String?,
    val gender: String?,
    val farmName: String?,
    val farmSize: String?,
    val primaryCrops: String?,
    val soilType: String?
)

/**
 * Represents the data structure for sending profile updates to the server.
 * Email is not included as it's typically not editable.
 */
data class UpdateProfileRequest(
    val name: String,
    val location: String,
    val contactNumber: String,
    val gender: String,
    val farmName: String,
    val farmSize: String,
    val primaryCrops: String,
    val soilType: String
)