package com.example.myapplication

import RetrofitInstance
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// Represents the state of the profile screen
sealed class ProfileState {
    object Loading : ProfileState()
    data class Success(val userProfile: UserProfile) : ProfileState()
    data class Error(val message: String) : ProfileState()
}

class ProfileViewModel : ViewModel() {

    private val _profileState = MutableStateFlow<ProfileState>(ProfileState.Loading)
    val profileState = _profileState.asStateFlow()

    // Fetches the user profile when the ViewModel is created
    fun fetchUserProfile(token: String) {
        viewModelScope.launch {
            _profileState.value = ProfileState.Loading
            try {
                val response = RetrofitInstance.api.getUserProfile("Bearer $token")
                if (response.isSuccessful && response.body() != null) {
                    _profileState.value = ProfileState.Success(response.body()!!)
                } else {
                    _profileState.value = ProfileState.Error("Failed to load profile.")
                }
            } catch (e: Exception) {
                _profileState.value = ProfileState.Error("Network error: ${e.message}")
            }
        }
    }

    // Saves the updated user profile
    fun saveUserProfile(token: String, updatedProfile: UpdateProfileRequest) {
        viewModelScope.launch {
            _profileState.value = ProfileState.Loading
            try {
                val response = RetrofitInstance.api.updateUserProfile("Bearer $token", updatedProfile)
                if (response.isSuccessful && response.body() != null) {
                    _profileState.value = ProfileState.Success(response.body()!!)
                } else {
                    _profileState.value = ProfileState.Error("Failed to save profile.")
                }
            } catch (e: Exception) {
                _profileState.value = ProfileState.Error("Network error: ${e.message}")
            }
        }
    }

    // Calculates the profile completion percentage
    fun calculateProfileCompletion(profile: UserProfile?, photoUri: Any?): Float {
        if (profile == null) return 0f

        val fields = listOf(
            profile.name,
            profile.email,
            profile.location,
            profile.contactNumber,
            profile.gender,
            profile.farmName,
            profile.farmSize,
            profile.primaryCrops,
            profile.soilType
        )
        val filledFields = fields.count { !it.isNullOrBlank() } + (if (photoUri != null) 1 else 0)
        val totalFields = fields.size + 1 // +1 for the photo
        return filledFields.toFloat() / totalFields.toFloat()
    }
}
