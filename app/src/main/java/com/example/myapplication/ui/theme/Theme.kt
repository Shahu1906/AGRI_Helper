package com.example.myapplication.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Natural & Farming-themed Color Palette
val GreenPrimary = Color(0xFF6B8E23)
val GreenSecondary = Color(0xFF81C784) // Lighter Green
// ...
val TextPrimary = Color(0xFF1C1C1E)
val BackgroundWhite = Color(0xFFFFFFFF)
// ...
val GreenLight = Color(0xFF8DC04A)
val CalculatorGreen = Color(0xFFF3FFF0)
val GreenDark = Color(0xFF556B2F)
val BrownEarthy = Color(0xFF8B4513)
val CreamBackground = Color(0xFFF5F5DC)
val TextDark = Color(0xFF2F2F2F)
val AccentBlue = Color(0xFF2F66F5)
val CardLavender = Color(0xFFF3EFFF)
val HighlightOrange = Color(0xFFFF9800)

// Chatbot specific colors (already in your code, but now centralized)
val UserBubbleColor = Color(0xFF689F38)
val BotBubbleColor = Color(0xFFFFFFFF)
val UserTextColor = Color.White
val BotTextColor = Color(0xFF333333)
val ChatInputBackground = Color(0xFFFFFFFF)

private val LightColors = lightColorScheme(
    primary = GreenLight,
    onPrimary = Color.White,
    secondary = GreenPrimary,
    onSecondary = Color.White,
    tertiary = BrownEarthy,
    background = CreamBackground,
    surface = ChatInputBackground,
    onSurface = TextDark
)

private val DarkColors = darkColorScheme(
    primary = GreenDark,
    onPrimary = Color.White,
    secondary = BrownEarthy,
    onSecondary = Color.White,
    tertiary = GreenLight,
    background = Color(0xFF1C1B1F),
    surface = Color(0xFF1C1B1F),
    onSurface = Color.White
)

@Composable
fun SmartFarmingTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColors,
        typography = Typography,
        content = content
    )
}