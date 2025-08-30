@file:OptIn(ExperimentalMaterial3Api::class)
@file:Suppress("Annotator", "Annotator", "Annotator", "Annotator", "Annotator", "Annotator",
    "Annotator"
)

package com.example.myapplication

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.SquareFoot
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.myapplication.ui.theme.CreamBackground
import com.example.myapplication.ui.theme.GreenLight
import com.example.myapplication.ui.theme.GreenPrimary
import com.example.myapplication.ui.theme.TextDark

@Composable
fun UserProfileScreen(
    navController: NavController,
    profileViewModel: ProfileViewModel = viewModel()
) {
    val context = LocalContext.current

    // Add error handling for SessionManager
    val sessionManager = remember {
        try {
            SessionManager(context)
        } catch (e: Exception) {
            Log.e("UserProfile", "Error creating SessionManager: ${e.message}")
            null
        }
    }

    // Handle case where SessionManager couldn't be created
    if (sessionManager == null) {
        LaunchedEffect(Unit) {
            Toast.makeText(context, "Error initializing session", Toast.LENGTH_LONG).show()
        }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("My Profile", color = Color.White, fontWeight = FontWeight.SemiBold, fontSize = 20.sp) },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF4CAF50)) // Fallback green
                )
            }
        ) { padding ->
            Box(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("Error loading profile. Please try again.", color = Color.Red)
            }
        }
        return
    }

    val token by sessionManager.authToken.collectAsState(initial = null)
    val profileState by profileViewModel.profileState.collectAsState()

    // Add logging for debugging
    LaunchedEffect(token) {
        Log.d("UserProfile", "Token received: ${if (token.isNullOrBlank()) "null/empty" else "valid"}")
        if (!token.isNullOrBlank()) {
            try {
                profileViewModel.fetchUserProfile(token!!)
            } catch (e: Exception) {
                Log.e("UserProfile", "Error fetching profile: ${e.message}")
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Profile", color = Color.White, fontWeight = FontWeight.SemiBold, fontSize = 20.sp) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = try {
                        GreenPrimary
                    } catch (e: Exception) {
                        Color(0xFF4CAF50) // Fallback color
                    }
                )
            )
        },
        containerColor = try {
            CreamBackground
        } catch (e: Exception) {
            Color(0xFFF5F5F5) // Fallback color
        }
    ) { padding ->
        when (val state = profileState) {
            is ProfileState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize().padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator(
                            color = try {
                                GreenPrimary
                            } catch (e: Exception) {
                                Color(0xFF4CAF50)
                            }
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("Loading profile...", color = Color.Gray)
                    }
                }
            }
            is ProfileState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize().padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = state.message,
                            color = Color.Red,
                            modifier = Modifier.padding(16.dp)
                        )
                        Button(
                            onClick = {
                                if (!token.isNullOrBlank()) {
                                    profileViewModel.fetchUserProfile(token!!)
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF4CAF50)
                            )
                        ) {
                            Text("Retry", color = Color.White)
                        }
                    }
                }
            }
            is ProfileState.Success -> {
                ProfileContent(
                    padding = padding,
                    userProfile = state.userProfile,
                    viewModel = profileViewModel,
                    token = token ?: ""
                )
            }
        }
    }
}

@Composable
fun ProfileContent(
    padding: PaddingValues,
    userProfile: UserProfile,
    viewModel: ProfileViewModel,
    token: String
) {
    // Local state for text fields, initialized with data from the ViewModel
    var editableName by remember { mutableStateOf(userProfile.name ?: "") }
    var editableLocation by remember { mutableStateOf(userProfile.location ?: "") }
    var editableContactNumber by remember { mutableStateOf(userProfile.contactNumber ?: "") }
    var editableGender by remember { mutableStateOf(userProfile.gender?.takeIf { it.isNotBlank() } ?: "Select Gender") }
    var editableFarmName by remember { mutableStateOf(userProfile.farmName ?: "") }
    var editableFarmSize by remember { mutableStateOf(userProfile.farmSize ?: "") }
    var editablePrimaryCrops by remember { mutableStateOf(userProfile.primaryCrops ?: "") }
    var editableSoilType by remember { mutableStateOf(userProfile.soilType ?: "") }
    var profilePhotoUri by remember { mutableStateOf<Uri?>(null) }

    val context = LocalContext.current

    // Calculate profile completion based on current state
    val profileCompletion = viewModel.calculateProfileCompletion(userProfile.copy(
        name = editableName,
        location = editableLocation,
        contactNumber = editableContactNumber,
        gender = if (editableGender == "Select Gender") "" else editableGender,
        farmName = editableFarmName,
        farmSize = editableFarmSize,
        primaryCrops = editablePrimaryCrops,
        soilType = editableSoilType
    ), profilePhotoUri)
    val animatedProgress by animateFloatAsState(targetValue = profileCompletion, label = "ProfileProgressAnimation")

    var showPhotoDialog by remember { mutableStateOf(false) }
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? -> profilePhotoUri = uri }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // --- Profile Header ---
        Box(
            modifier = Modifier
                .size(130.dp)
                .clip(CircleShape)
                .border(
                    3.dp,
                    try { GreenPrimary.copy(alpha = 0.5f) } catch (e: Exception) { Color(0xFF4CAF50).copy(alpha = 0.5f) },
                    CircleShape
                )
                .background(Color.White, CircleShape)
                .clickable { showPhotoDialog = true },
            contentAlignment = Alignment.Center
        ) {
            if (profilePhotoUri != null) {
                Image(
                    painter = rememberAsyncImagePainter(profilePhotoUri),
                    contentDescription = "Profile Picture",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = "Profile Picture Placeholder",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
        Spacer(Modifier.height(16.dp))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp)
        ) {
            val percentage = (animatedProgress * 100).toInt()
            Text(
                "Your profile is $percentage% complete",
                fontSize = 14.sp,
                color = try { TextDark.copy(alpha = 0.8f) } catch (e: Exception) { Color.Black.copy(alpha = 0.8f) },
                fontWeight = FontWeight.Medium
            )
            Spacer(Modifier.height(8.dp))
            LinearProgressIndicator(
                progress = { animatedProgress },
                modifier = Modifier.fillMaxWidth().height(8.dp).clip(RoundedCornerShape(4.dp)),
                color = try { GreenPrimary } catch (e: Exception) { Color(0xFF4CAF50) },
                trackColor = try { GreenLight.copy(alpha = 0.3f) } catch (e: Exception) { Color(0xFF81C784).copy(alpha = 0.3f) },
                strokeCap = StrokeCap.Round
            )
        }
        Spacer(Modifier.height(32.dp))

        // --- Personal Details ---
        SectionHeader("Personal Details")
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(Modifier.padding(20.dp)) {
                ProfileTextField(
                    value = editableName,
                    onValueChange = { editableName = it },
                    label = "Full Name",
                    leadingIcon = Icons.Default.AccountCircle
                )
                Spacer(Modifier.height(16.dp))
                ProfileTextField(
                    value = userProfile.email ?: "",
                    onValueChange = { },
                    label = "Email Address",
                    leadingIcon = Icons.Default.Email,
                    readOnly = true
                )
                Spacer(Modifier.height(16.dp))
                ProfileTextField(
                    value = editableContactNumber,
                    onValueChange = { editableContactNumber = it },
                    label = "Contact Number",
                    leadingIcon = Icons.Default.Phone
                )
                Spacer(Modifier.height(16.dp))
                GenderSelector(
                    selectedGender = editableGender,
                    onGenderSelect = { editableGender = it }
                )
                Spacer(Modifier.height(16.dp))
                ProfileTextField(
                    value = editableLocation,
                    onValueChange = { editableLocation = it },
                    label = "Location (City, State)",
                    leadingIcon = Icons.Default.LocationOn
                )
            }
        }
        Spacer(Modifier.height(24.dp))

        // --- Farming Details ---
        SectionHeader("Farming Details")
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(Modifier.padding(20.dp)) {
                ProfileTextField(
                    value = editableFarmName,
                    onValueChange = { editableFarmName = it },
                    label = "Farm Name (Optional)",
                    leadingIcon = Icons.Default.Home
                )
                Spacer(Modifier.height(16.dp))
                ProfileTextField(
                    value = editableFarmSize,
                    onValueChange = { editableFarmSize = it },
                    label = "Farm Size (e.g., 10 Acres)",
                    leadingIcon = Icons.Default.SquareFoot
                )
                Spacer(Modifier.height(16.dp))
                ProfileTextField(
                    value = editablePrimaryCrops,
                    onValueChange = { editablePrimaryCrops = it },
                    label = "Primary Crops (e.g., Wheat, Corn)",
                    leadingIcon = Icons.Default.Eco
                )
                Spacer(Modifier.height(16.dp))
                ProfileTextField(
                    value = editableSoilType,
                    onValueChange = { editableSoilType = it },
                    label = "Dominant Soil Type",
                    leadingIcon = Icons.Default.Layers
                )
            }
        }
        Spacer(Modifier.height(24.dp))

        // --- Save Button ---
        Button(
            onClick = {
                try {
                    val updatedProfileData = UpdateProfileRequest(
                        name = editableName,
                        location = editableLocation,
                        contactNumber = editableContactNumber,
                        gender = if (editableGender == "Select Gender") "" else editableGender,
                        farmName = editableFarmName,
                        farmSize = editableFarmSize,
                        primaryCrops = editablePrimaryCrops,
                        soilType = editableSoilType
                    )
                    viewModel.saveUserProfile(token, updatedProfileData)
                    Toast.makeText(context, "Saving Changes...", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    Log.e("UserProfile", "Error saving profile: ${e.message}")
                    Toast.makeText(context, "Error saving profile", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = try { GreenPrimary } catch (e: Exception) { Color(0xFF4CAF50) }
            ),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
        ) {
            Text("Save Changes", fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
        }
        Spacer(Modifier.height(16.dp))
    }

    // Photo Dialog
    if (showPhotoDialog) {
        AlertDialog(
            onDismissRequest = { showPhotoDialog = false },
            title = { Text("Profile Photo") },
            text = { Text("Choose an action for your profile photo.") },
            confirmButton = {
                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(onClick = {
                        photoPickerLauncher.launch("image/*")
                        showPhotoDialog = false
                    }) {
                        Text("Add New", color = try { GreenPrimary } catch (e: Exception) { Color(0xFF4CAF50) })
                    }
                    TextButton(onClick = {
                        profilePhotoUri = null
                        showPhotoDialog = false
                    }) {
                        Text("Delete", color = Color.Red)
                    }
                }
            }
        )
    }
}

// --- HELPER COMPOSABLES ---

@Composable
fun SectionHeader(title: String) {
    Text(
        text = title,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        color = try { TextDark } catch (e: Exception) { Color.Black },
        modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)
    )
}

@Composable
fun GenderSelector(selectedGender: String, onGenderSelect: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    val genders = listOf("Male", "Female", "Other")

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = selectedGender,
            onValueChange = {},
            readOnly = true,
            label = { Text("Gender", color = try { TextDark.copy(alpha = 0.6f) } catch (e: Exception) { Color.Black.copy(alpha = 0.6f) }) },
            leadingIcon = { Icon(Icons.Default.Person, contentDescription = "Gender", tint = try { GreenPrimary } catch (e: Exception) { Color(0xFF4CAF50) }) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = try { GreenPrimary } catch (e: Exception) { Color(0xFF4CAF50) },
                unfocusedBorderColor = try { GreenLight } catch (e: Exception) { Color(0xFF81C784) },
                focusedLabelColor = try { GreenPrimary } catch (e: Exception) { Color(0xFF4CAF50) },
                cursorColor = try { GreenPrimary } catch (e: Exception) { Color(0xFF4CAF50) },
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White,
                errorContainerColor = Color.White
            ),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth().menuAnchor()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            genders.forEach { gender ->
                DropdownMenuItem(
                    text = { Text(gender) },
                    onClick = {
                        onGenderSelect(gender)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun ProfileTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    leadingIcon: ImageVector,
    readOnly: Boolean = false
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, color = try { TextDark.copy(alpha = 0.6f) } catch (e: Exception) { Color.Black.copy(alpha = 0.6f) }) },
        leadingIcon = { Icon(leadingIcon, contentDescription = label, tint = try { GreenPrimary } catch (e: Exception) { Color(0xFF4CAF50) }) },
        readOnly = readOnly,
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = try { GreenPrimary } catch (e: Exception) { Color(0xFF4CAF50) },
            unfocusedBorderColor = try { GreenLight } catch (e: Exception) { Color(0xFF81C784) },
            focusedLabelColor = try { GreenPrimary } catch (e: Exception) { Color(0xFF4CAF50) },
            cursorColor = try { GreenPrimary } catch (e: Exception) { Color(0xFF4CAF50) },
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            disabledContainerColor = Color.White,
            errorContainerColor = Color.White
        ),
        shape = RoundedCornerShape(12.dp)
    )
}