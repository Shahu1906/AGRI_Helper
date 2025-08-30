@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.myapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.HelpOutline
import androidx.compose.material.icons.automirrored.filled.Subject
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Brightness4
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.ui.theme.CreamBackground
import com.example.myapplication.ui.theme.GreenLight
import com.example.myapplication.ui.theme.GreenPrimary
import com.example.myapplication.ui.theme.TextDark

@Composable
fun SettingsScreen(navController: NavController) {
    // State holders for the interactive settings
    var isDarkMode by remember { mutableStateOf(false) }
    var areWeatherAlertsEnabled by remember { mutableStateOf(true) }
    var arePestAlertsEnabled by remember { mutableStateOf(true) }
    var isVoiceOutputEnabled by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings", color = Color.White, fontWeight = FontWeight.SemiBold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = GreenPrimary)
            )
        },
        containerColor = CreamBackground
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .background(CreamBackground)
        ) {
            // Account Section
            SectionTitle("Account")
            SettingsCard {
                SettingsRow(
                    icon = Icons.Default.AccountCircle,
                    title = "Edit Profile",
                    onClick = { navController.navigate("profile") }
                )
                Divider(color = GreenLight.copy(alpha = 0.2f))
                SettingsRow(
                    icon = Icons.Default.Password,
                    title = "Change Password",
                    onClick = { /* TODO: Navigate to change password screen */ }
                )
            }

            // App Preferences Section
            SectionTitle("Preferences")
            SettingsCard {
                SettingsRowWithSwitch(
                    icon = Icons.Default.Brightness4,
                    title = "Dark Mode",
                    isChecked = isDarkMode,
                    onCheckedChange = { isDarkMode = it }
                )
                Divider(color = GreenLight.copy(alpha = 0.2f))
                SettingsRowWithSwitch(
                    icon = Icons.Default.RecordVoiceOver,
                    title = "Voice Output for Advice",
                    subtitle = "Read recommendations aloud",
                    isChecked = isVoiceOutputEnabled,
                    onCheckedChange = { isVoiceOutputEnabled = it }
                )
                Divider(color = GreenLight.copy(alpha = 0.2f))
                SettingsRow(
                    icon = Icons.Default.Language,
                    title = "Language",
                    subtitle = "English",
                    onClick = { /* TODO: Show language selection dialog */ }
                )
            }

            // Notifications Section
            SectionTitle("Notifications")
            SettingsCard {
                SettingsRowWithSwitch(
                    icon = Icons.Default.WbSunny,
                    title = "Weather Alerts",
                    subtitle = "Get notified about important weather changes",
                    isChecked = areWeatherAlertsEnabled,
                    onCheckedChange = { areWeatherAlertsEnabled = it }
                )
                Divider(color = GreenLight.copy(alpha = 0.2f))
                SettingsRowWithSwitch(
                    icon = Icons.Default.BugReport,
                    title = "Pest Alerts",
                    subtitle = "Receive alerts for potential pest outbreaks",
                    isChecked = arePestAlertsEnabled,
                    onCheckedChange = { arePestAlertsEnabled = it }
                )
            }

            // Support & Legal Section
            SectionTitle("About & Support")
            SettingsCard {
                SettingsRow(
                    icon = Icons.AutoMirrored.Filled.HelpOutline,
                    title = "Help & Support",
                    onClick = { /* TODO: Navigate to support screen or open link */ }
                )
                Divider(color = GreenLight.copy(alpha = 0.2f))
                SettingsRow(
                    icon = Icons.AutoMirrored.Filled.Subject,
                    title = "Privacy Policy",
                    onClick = { /* TODO: Open privacy policy link */ }
                )
                Divider(color = GreenLight.copy(alpha = 0.2f))
                SettingsRow(
                    icon = Icons.Default.Info,
                    title = "About Agri Helper",
                    subtitle = "Version 1.0",
                    onClick = {}
                )
            }

            Spacer(Modifier.height(24.dp))
        }
    }
}

// --- Helper Composables for a Cleaner Layout ---

@Composable
private fun SectionTitle(title: String) {
    Text(
        text = title,
        color = TextDark,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 24.dp, bottom = 8.dp)
    )
}

@Composable
private fun SettingsCard(content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier.padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            content()
        }
    }
}

@Composable
private fun SettingsRow(
    icon: ImageVector,
    title: String,
    subtitle: String? = null,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = GreenPrimary,
            modifier = Modifier
                .size(40.dp)
                .background(GreenLight.copy(alpha = 0.1f), CircleShape)
                .padding(8.dp)
        )
        Spacer(Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(title, color = TextDark, fontSize = 17.sp)
            if (subtitle != null) {
                Spacer(Modifier.height(2.dp))
                Text(subtitle, color = TextDark.copy(alpha = 0.6f), fontSize = 13.sp)
            }
        }
        Icon(Icons.Default.ChevronRight, contentDescription = "Navigate", tint = Color.Gray)
    }
}

@Composable
private fun SettingsRowWithSwitch(
    icon: ImageVector,
    title: String,
    subtitle: String? = null,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCheckedChange(!isChecked) }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = GreenPrimary,
            modifier = Modifier
                .size(40.dp)
                .background(GreenLight.copy(alpha = 0.1f), CircleShape)
                .padding(8.dp)
        )
        Spacer(Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(title, color = TextDark, fontSize = 17.sp)
            if (subtitle != null) {
                Spacer(Modifier.height(2.dp))
                Text(subtitle, color = TextDark.copy(alpha = 0.6f), fontSize = 13.sp)
            }
        }
        Switch(
            checked = isChecked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = GreenPrimary,
                uncheckedThumbColor = Color.White,
                uncheckedTrackColor = Color.LightGray
            )
        )
    }
}
