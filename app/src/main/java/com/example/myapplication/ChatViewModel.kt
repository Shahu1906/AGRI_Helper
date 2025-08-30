package com.example.myapplication.ChatBot

import android.app.Application
import android.speech.tts.TextToSpeech
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Locale

class ChatViewModel(application: Application) : AndroidViewModel(application) {

    private val _chatHistory = MutableStateFlow(
        listOf("🌳 Prakriti is here to help! Ask me anything about agriculture.")
    )
    val chatHistory: StateFlow<List<String>> = _chatHistory.asStateFlow()

    private val generativeModel = GenerativeModel(
        modelName = "gemini-1.5-flash-latest",
        apiKey = "AIzaSyB2inVrOs7lkR9qS2nhKhbb_dcBn_9tzhI"
    )

    private val predefinedResponses = mapOf(
        "who are you" to "Hello! My name is Prakriti 🌱. I'm here to help you with your agriculture questions.",
        "what is your name" to "My name is Prakriti 🌱. Nice to meet you!",
        "who created you" to "I was created by a team which consists of Shahu Jadhav, Dhanashree Jagtap, Siddharth Jadhav, Aryan Jagtap 👨‍💻.",
        "hello" to "Hello! 👋 How can I help you today?",
        "hi" to "Hi there! 🌸 What would you like to know?",
        "thank you" to "You're welcome! 😊",
        "how are you" to "I'm just a bot, but I'm always ready to help you 🌾."
    )

    // --- Text to Speech ---
    private var tts: TextToSpeech? = null

    fun initTTS(language: String) {
        val localeMap = mapOf(
            "English" to Locale("en", "US"),
            "Hindi" to Locale("hi", "IN"),
            "Marathi" to Locale("mr", "IN"),
            "Bengali" to Locale("bn", "IN"),
            "Tamil" to Locale("ta", "IN"),
            "Telugu" to Locale("te", "IN"),
            "Kannada" to Locale("kn", "IN"),
            "Gujarati" to Locale("gu", "IN"),
            "Punjabi" to Locale("pa", "IN"),
            "Malayalam" to Locale("ml", "IN")
        )

        val selectedLocale = localeMap[language] ?: Locale.ENGLISH

        if (tts == null) {
            tts = TextToSpeech(getApplication()) { status ->
                if (status == TextToSpeech.SUCCESS) {
                    tts?.language = selectedLocale
                }
            }
        } else {
            tts?.language = selectedLocale
        }
    }

    private fun speakOut(text: String) {
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
    }

    fun sendMessage(userMessage: String, language: String) {
        viewModelScope.launch {
            _chatHistory.value = _chatHistory.value + "🧑 You: $userMessage"
            val lowerCaseMessage = userMessage.lowercase(Locale.getDefault())

            val matchedResponse = predefinedResponses.entries.find { (key, _) ->
                lowerCaseMessage.contains(key)
            }?.value

            var botResponse: String
            if (matchedResponse != null) {
                botResponse = matchedResponse
            } else {
                try {
                    val prompt = "Answer the following agricultural question in $language: '$userMessage'"
                    val response = generativeModel.generateContent(prompt)
                    botResponse = (response.text ?: "I'm sorry, I couldn't process that. Please try again.")
                        .replace("* ", "")
                } catch (e: Exception) {
                    botResponse = "An error occurred: ${e.message}"
                }
            }
            _chatHistory.value = _chatHistory.value + "🤖 Bot: $botResponse"

            // --- Speak the bot response ---
            initTTS(language)
            speakOut(botResponse)
        }
    }

    override fun onCleared() {
        super.onCleared()
        tts?.stop()
        tts?.shutdown()
    }
}
