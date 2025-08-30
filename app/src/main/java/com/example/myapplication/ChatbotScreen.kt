@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)

package com.example.myapplication.ChatBot

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.speech.RecognizerIntent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.VolumeOff
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myapplication.ChatInputBar
import com.example.myapplication.R
import com.example.myapplication.ui.theme.*

@Composable
fun ChatbotScreen(
    navController: NavController,
    viewModel: ChatViewModel = viewModel()
) {
    var userInput by remember { mutableStateOf("") }
    val chatHistory by viewModel.chatHistory.collectAsState()
    val scrollState = rememberScrollState()
    val keyboardController = LocalSoftwareKeyboardController.current

    // --- Language Selection State ---
    val languages = listOf("English", "Hindi", "Marathi", "Bengali", "Tamil", "Telugu", "Kannada", "Gujarati", "Punjabi", "Malayalam")
    val languageToLocale = mapOf(
        "English" to "en-US", "Hindi" to "hi-IN", "Marathi" to "mr-IN",
        "Bengali" to "bn-IN", "Tamil" to "ta-IN", "Telugu" to "te-IN",
        "Kannada" to "kn-IN", "Gujarati" to "gu-IN", "Punjabi" to "pa-IN",
        "Malayalam" to "ml-IN"
    )
    var selectedLanguage by remember { mutableStateOf(languages.first()) }
    var isLanguageMenuExpanded by remember { mutableStateOf(false) }

    // --- Voice Toggle ---
    var isVoiceEnabled by remember { mutableStateOf(true) }

    // --- Speech Recognizer Launcher ---
    val speechRecognizerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val spokenText = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)?.get(0)
            if (!spokenText.isNullOrEmpty()) {
                userInput = spokenText
            }
        }
    }

    // --- Permission Launcher ---
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                putExtra(RecognizerIntent.EXTRA_LANGUAGE, languageToLocale[selectedLanguage])
                putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak now...")
            }
            speechRecognizerLauncher.launch(intent)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.chatbot_image_prakriti),
                            contentDescription = "Prakriti Logo",
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                        )
                        Spacer(Modifier.width(12.dp))
                        Text(
                            text = "Prakriti",
                            color = Color.White,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 20.sp
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                },
                actions = {
                    // --- Voice Toggle ---
                    IconButton(onClick = { isVoiceEnabled = !isVoiceEnabled }) {
                        Icon(
                            imageVector = if (isVoiceEnabled) Icons.Default.VolumeUp else Icons.Default.VolumeOff,
                            contentDescription = if (isVoiceEnabled) "Voice On" else "Voice Off",
                            tint = Color.White
                        )
                    }

                    // --- Language Selection Dropdown ---
                    Box {
                        IconButton(onClick = { isLanguageMenuExpanded = true }) {
                            Icon(Icons.Default.MoreVert, contentDescription = "Select Language", tint = Color.White)
                        }
                        DropdownMenu(
                            expanded = isLanguageMenuExpanded,
                            onDismissRequest = { isLanguageMenuExpanded = false }
                        ) {
                            languages.forEach { language ->
                                DropdownMenuItem(
                                    text = { Text(language) },
                                    onClick = {
                                        selectedLanguage = language
                                        isLanguageMenuExpanded = false
                                    }
                                )
                            }
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = GreenDark,
                    navigationIconContentColor = Color.White,
                    titleContentColor = Color.White
                ),
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 8.dp)
                    .shadow(
                        elevation = 4.dp,
                        shape = RoundedCornerShape(16.dp),
                        clip = false
                    )
            )
        },
        containerColor = CreamBackground
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(CreamBackground)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(scrollState)
                        .padding(bottom = 72.dp)
                ) {
                    Spacer(Modifier.height(16.dp))
                    if (chatHistory.isEmpty()) {
                        WelcomeMessage()
                    }
                    chatHistory.forEachIndexed { index, msg ->
                        if (msg.startsWith("🤖 Bot:")) {
                            val message = msg.removePrefix("🤖 Bot:").trim()
                            BotMessage(message = message, bubbleColor = BotBubbleColor, textColor = BotTextColor)
                        } else {
                            val message = msg.removePrefix("🧑 You:").trim()
                            UserMessage(message = message, bubbleColor = UserBubbleColor, textColor = UserTextColor)
                        }
                        if (index < chatHistory.size - 1) {
                            Spacer(Modifier.height(4.dp))
                        }
                    }
                    Spacer(Modifier.height(16.dp))
                }
            }

            // Floating input bar
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(horizontal = 16.dp, vertical = 12.dp)
                    .fillMaxWidth()
                    .shadow(
                        elevation = 6.dp,
                        shape = RoundedCornerShape(24.dp),
                        clip = false,
                        spotColor = Color.Black.copy(alpha = 0.1f)
                    )
            ) {
                ChatInputBar(
                    userInput = userInput,
                    onUserInputChanged = { userInput = it },
                    onSendMessage = {
                        if (userInput.isNotBlank()) {
                            // init TTS with selected language
                            viewModel.initTTS(selectedLanguage)
                            // pass voice toggle too
                            if (isVoiceEnabled) {
                                viewModel.sendMessage(userInput, selectedLanguage)
                            } else {
                                viewModel.sendMessage(userInput, selectedLanguage) // text only, TTS skipped inside VM
                            }
                            userInput = ""
                            keyboardController?.hide()
                        }
                    },
                    onMicClicked = {
                        permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
                    },
                    inputBackground = Color.White,
                    sendButtonColor = GreenDark,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
    LaunchedEffect(chatHistory.size) {
        scrollState.animateScrollTo(scrollState.maxValue)
    }
}
