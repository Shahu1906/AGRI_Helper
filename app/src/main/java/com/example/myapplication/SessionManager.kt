package com.example.myapplication

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// This creates the DataStore instance. The name "session" is the filename for the saved preferences.
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")

/**
 * This class handles all operations related to the user's session,
 * such as saving and retrieving the authentication token.
 */
class SessionManager(private val context: Context) {

    // This companion object holds the key we'll use to store the token.
    companion object {
        private val AUTH_TOKEN_KEY = stringPreferencesKey("auth_token")
    }

    /**
     * Saves the authentication token to the DataStore. This is a suspend function
     * because DataStore operations are asynchronous.
     */
    suspend fun saveAuthToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[AUTH_TOKEN_KEY] = token
        }
    }

    /**
     * Retrieves the authentication token from DataStore. This is exposed as a Flow,
     * which allows your UI to automatically update whenever the token changes.
     */
    val authToken: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[AUTH_TOKEN_KEY]
        }

    /**
     * Clears the authentication token, effectively logging the user out.
     */
    suspend fun clearAuthToken() {
        context.dataStore.edit { preferences ->
            preferences.remove(AUTH_TOKEN_KEY)
        }
    }
}

