@file:OptIn(ExperimentalMaterial3Api::class)



package com.example.myapplication



import android.net.Uri

import androidx.activity.compose.rememberLauncherForActivityResult

import androidx.activity.result.contract.ActivityResultContracts

import androidx.compose.animation.animateColorAsState

import androidx.compose.foundation.BorderStroke

import androidx.compose.foundation.Image

import androidx.compose.foundation.background

import androidx.compose.foundation.border

import androidx.compose.foundation.gestures.detectTapGestures

import androidx.compose.foundation.layout.Arrangement

import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.Column

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

import androidx.compose.material.icons.filled.AccountCircle

import androidx.compose.material.icons.filled.ExitToApp

import androidx.compose.material.icons.filled.Menu

import androidx.compose.material.icons.filled.Settings

import androidx.compose.material.icons.outlined.Dashboard

import androidx.compose.material3.Button

import androidx.compose.material3.ButtonDefaults

import androidx.compose.material3.Card

import androidx.compose.material3.CardDefaults

import androidx.compose.material3.CenterAlignedTopAppBar

import androidx.compose.material3.DrawerValue

import androidx.compose.material3.ExperimentalMaterial3Api

import androidx.compose.material3.FabPosition

import androidx.compose.material3.FloatingActionButton

import androidx.compose.material3.FloatingActionButtonDefaults

import androidx.compose.material3.HorizontalDivider

import androidx.compose.material3.Icon

import androidx.compose.material3.IconButton

import androidx.compose.material3.ModalDrawerSheet

import androidx.compose.material3.ModalNavigationDrawer

import androidx.compose.material3.NavigationDrawerItem

import androidx.compose.material3.NavigationDrawerItemDefaults

import androidx.compose.material3.Scaffold

import androidx.compose.material3.Text

import androidx.compose.material3.TopAppBarDefaults

import androidx.compose.material3.rememberDrawerState

import androidx.compose.runtime.Composable

import androidx.compose.runtime.getValue

import androidx.compose.runtime.mutableStateOf

import androidx.compose.runtime.remember

import androidx.compose.runtime.rememberCoroutineScope

import androidx.compose.runtime.setValue

import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier

import androidx.compose.ui.draw.clip

import androidx.compose.ui.draw.shadow

import androidx.compose.ui.graphics.Color

import androidx.compose.ui.input.pointer.pointerInput

import androidx.compose.ui.layout.ContentScale

import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.res.painterResource

import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.text.style.TextAlign

import androidx.compose.ui.unit.dp

import androidx.compose.ui.unit.sp

import androidx.navigation.NavController

import coil.compose.rememberAsyncImagePainter

import com.example.myapplication.ui.theme.CalculatorGreen

import com.example.myapplication.ui.theme.CreamBackground

import com.example.myapplication.ui.theme.GreenLight

import com.example.myapplication.ui.theme.GreenPrimary

import com.example.myapplication.ui.theme.TextDark

import kotlinx.coroutines.launch

import java.text.SimpleDateFormat

import java.util.Date

import java.util.Locale





@Composable

fun HomeScreen(navController: NavController) {

    val drawerState = rememberDrawerState(DrawerValue.Closed)

    val scope = rememberCoroutineScope()

    val context = LocalContext.current

    val sessionManager = remember { SessionManager(context) }



// This part is a placeholder for a real user data model.

// In a real app, this would come from a ViewModel that gets the user's data after login.

    val userName = "Shahu Jadhav"

    val userPhotoUri by remember { mutableStateOf<Uri?>(null) }





    ModalNavigationDrawer(

        drawerState = drawerState,

        drawerContent = {

            ModalDrawerSheet(

                modifier = Modifier

                    .width(280.dp),

                drawerContainerColor = Color.White,

                drawerShape = RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp)

            ) {

// Header with User Photo and Name

                Column(

                    modifier = Modifier

                        .fillMaxWidth()

                        .background(CreamBackground)

                        .padding(24.dp),

                    horizontalAlignment = Alignment.CenterHorizontally

                ) {

                    Image(

                        painter = if (userPhotoUri != null) {

                            rememberAsyncImagePainter(model = userPhotoUri)

                        } else {

                            painterResource(id = R.drawable.ic_launcher_foreground) // Placeholder

                        },

                        contentDescription = "User Photo",

                        contentScale = ContentScale.Crop,

                        modifier = Modifier

                            .size(80.dp)

                            .clip(CircleShape)

                            .border(2.dp, GreenPrimary, CircleShape)

                            .background(Color.Gray.copy(alpha = 0.2f))

                    )

                    Spacer(Modifier.height(16.dp))

                    Text(

                        userName,

                        fontWeight = FontWeight.Bold,

                        color = TextDark,

                        fontSize = 20.sp

                    )

                    Spacer(Modifier.height(4.dp))

                    Text(

                        "Agricultural Assistant",

                        color = TextDark.copy(alpha = 0.7f),

                        fontSize = 14.sp

                    )

                }



                HorizontalDivider(color = Color.LightGray.copy(alpha = 0.4f))



// Menu Items

                Column(modifier = Modifier.padding(12.dp)) {

                    NavigationDrawerItem(

                        label = { Text("Dashboard") },

                        icon = { Icon(Icons.Outlined.Dashboard, contentDescription = "Dashboard") },

                        selected = false,

                        onClick = { scope.launch { drawerState.close() } },

                        colors = NavigationDrawerItemDefaults.colors(unselectedTextColor = TextDark)

                    )

// --- CORRECTED PROFILE BUTTON ---

                    NavigationDrawerItem(

                        label = { Text("My Profile") },

                        icon = { Icon(Icons.Default.AccountCircle, contentDescription = "Profile") },

                        selected = false,

                        onClick = {

                            scope.launch { drawerState.close() }

                            navController.navigate("profile")

                        },

                        colors = NavigationDrawerItemDefaults.colors(unselectedTextColor = TextDark)

                    )

                    NavigationDrawerItem(

                        label = { Text("Settings") },

                        icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },

                        selected = false,

                        onClick = {

                            scope.launch { drawerState.close() }

// You would add "settings" to your NavGraph

                            navController.navigate("settings")

                        },

                        colors = NavigationDrawerItemDefaults.colors(unselectedTextColor = TextDark)

                    )

                }



                Spacer(modifier = Modifier.weight(1f)) // Pushes logout to the bottom



                HorizontalDivider(color = Color.LightGray.copy(alpha = 0.4f))

                NavigationDrawerItem(

                    label = { Text("Logout") },

                    icon = { Icon(Icons.Default.ExitToApp, contentDescription = "Logout") },

                    selected = false,

                    onClick = {

                        scope.launch {

                            sessionManager.clearAuthToken()

                            navController.navigate("login") {

                                popUpTo(0)

                            }

                        }

                    },

                    modifier = Modifier.padding(12.dp),

                    colors = NavigationDrawerItemDefaults.colors(unselectedTextColor = TextDark)

                )

            }

        }

    ) {

// Your Scaffold and main screen content remains unchanged...

        Scaffold(

            topBar = {

                CenterAlignedTopAppBar(

                    title = {

                        Text(

                            "Agri Helper",

                            color = Color.White,

                            fontWeight = FontWeight.SemiBold,

                            fontSize = 20.sp

                        )

                    },

                    navigationIcon = {

                        IconButton(

                            onClick = { scope.launch { drawerState.open() } },

                            modifier = Modifier

                                .background(

                                    Color.White.copy(alpha = 0.2f),

                                    RoundedCornerShape(12.dp)

                                )

                                .padding(4.dp)

                                .size(40.dp)

                        ) {

                            Icon(

                                Icons.Default.Menu,

                                contentDescription = "Menu",

                                tint = Color.White

                            )

                        }

                    },

                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(

                        containerColor = GreenPrimary

                    )

                )

            },

            floatingActionButton = {

                Box(

                    modifier = Modifier

                        .fillMaxWidth()

                        .padding(bottom = 16.dp),

                    contentAlignment = Alignment.Center

                ) {

                    FloatingActionButton(

                        onClick = { navController.navigate("chatbot")},

                        containerColor = CalculatorGreen,

                        modifier = Modifier

                            .size(72.dp)

                            .shadow(

                                elevation = 8.dp,

                                shape = CircleShape,

                                clip = false

                            ),

                        elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 0.dp)

                    ) {

                        Column(

                            horizontalAlignment = Alignment.CenterHorizontally,

                            modifier = Modifier.padding(6.dp)

                        ) {

                            Image(

                                painter = painterResource(id = R.drawable.chatbot_image_prakriti),

                                contentDescription = "Prakriti Chatbot",

                                modifier = Modifier.size(400.dp)

                            )

                        }

                    }

                }

            },

            floatingActionButtonPosition = FabPosition.Center,

            containerColor = CreamBackground

        ) { padding ->

            Column(

                modifier = Modifier

                    .padding(padding)

                    .fillMaxSize()

                    .verticalScroll(rememberScrollState())

                    .background(CreamBackground)

                    .padding(horizontal = 20.dp, vertical = 12.dp)

            ) {

                DashboardContent(navController = navController)

            }

        }

    }

}



// ... The rest of your Composables (DashboardContent, etc.) remain unchanged ...

@Composable

fun DashboardContent(navController: NavController) {

    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    val imagePicker = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->

        selectedImageUri = uri

    }



    val currentDate = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date())



// Header Section

    Column(

        modifier = Modifier.fillMaxWidth(),

        horizontalAlignment = Alignment.CenterHorizontally

    ) {

        Text(

            "Today's Overview",

            color = TextDark,

            fontSize = 22.sp,

            fontWeight = FontWeight.Bold,

            modifier = Modifier.padding(bottom = 4.dp)

        )

        Text(

            "Agricultural Management Dashboard",

            color = TextDark.copy(alpha = 0.7f),

            fontSize = 14.sp,

            modifier = Modifier.padding(bottom = 16.dp)

        )

    }



// Stats Cards

    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {

        UnifiedInfoCard("Date", currentDate, "Weather: Sunny | 28°C", Modifier.weight(1f))

        Spacer(Modifier.width(16.dp))

        UnifiedInfoCard("Condition", "Good", "Humidity: 55%", Modifier.weight(1f))

    }



    Spacer(Modifier.height(28.dp))



// Crop Health Section

    Text(

        "Crop Health Analysis",

        color = TextDark,

        fontSize = 20.sp,

        fontWeight = FontWeight.Bold,

        modifier = Modifier.padding(bottom = 12.dp)

    )



    Card(

        Modifier

            .fillMaxWidth()

            .height(if (selectedImageUri != null) 340.dp else 220.dp),

        shape = RoundedCornerShape(20.dp),

        colors = CardDefaults.cardColors(containerColor = Color.White),

        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)

    ) {

        Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {

            Column(

                Modifier.padding(28.dp),

                horizontalAlignment = Alignment.CenterHorizontally

            ) {

                Button(

                    onClick = { imagePicker.launch("image/*") },

                    colors = ButtonDefaults.buttonColors(containerColor = GreenPrimary),

                    shape = RoundedCornerShape(12.dp),

                    elevation = ButtonDefaults.buttonElevation(

                        defaultElevation = 4.dp,

                        pressedElevation = 2.dp

                    ),

                    modifier = Modifier.height(48.dp)

                ) {

                    Text("Upload Crop Photo", color = Color.White, fontSize = 15.sp)

                }



                selectedImageUri?.let { uri ->

                    Spacer(Modifier.height(20.dp))

                    Image(

                        painter = rememberAsyncImagePainter(uri),

                        contentDescription = "Selected Crop",

                        Modifier

                            .fillMaxWidth()

                            .height(180.dp)

                            .clip(RoundedCornerShape(14.dp)),

                        contentScale = ContentScale.Crop

                    )

                    Spacer(Modifier.height(12.dp))

                    Text(

                        "AI Analysis: Healthy Crop 🌱",

                        color = GreenPrimary,

                        fontWeight = FontWeight.Bold,

                        fontSize = 15.sp

                    )

                }



            }

        }

    }



    Spacer(Modifier.height(28.dp))



// Quick Tools Section

    Text(

        "Quick Tools",

        color = TextDark,

        fontSize = 20.sp,

        fontWeight = FontWeight.Bold,

        modifier = Modifier.padding(bottom = 12.dp)

    )



    Column {

        Row(Modifier.fillMaxWidth()) {

            UnifiedFeatureCard("Fertilizer\nCalculator", navController, Modifier.weight(1f))

            Spacer(Modifier.width(16.dp))

            UnifiedFeatureCard("Pests &\nDiseases", navController, Modifier.weight(1f))

        }

        Spacer(Modifier.height(16.dp))

        Row(Modifier.fillMaxWidth()) {

            UnifiedFeatureCard("Cultivation\nTips", navController, Modifier.weight(1f))

            Spacer(Modifier.width(16.dp))

            UnifiedFeatureCard("Pest\nAlerts", navController, Modifier.weight(1f))

        }

    }



}



@Composable

fun UnifiedInfoCard(title: String, value: String, subtitle: String, modifier: Modifier = Modifier) {

    Card(

        modifier = modifier,

        colors = CardDefaults.cardColors(containerColor = Color.White),

        shape = RoundedCornerShape(18.dp),

        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),

        border = BorderStroke(1.dp, GreenPrimary.copy(alpha = 0.2f))

    ) {

        Column(Modifier.padding(18.dp)) {

            Text(

                title,

                color = TextDark.copy(alpha = 0.7f),

                fontSize = 14.sp,

                fontWeight = FontWeight.Medium

            )

            Spacer(Modifier.height(6.dp))

            Text(

                value,

                color = GreenPrimary,

                fontSize = 18.sp,

                fontWeight = FontWeight.Bold

            )

            Spacer(Modifier.height(6.dp))

            Text(

                subtitle,

                color = TextDark.copy(alpha = 0.6f),

                fontSize = 13.sp

            )

        }

    }

}



@Composable

fun UnifiedFeatureCard(title: String, navController: NavController, modifier: Modifier = Modifier) {

    var isPressed by remember { mutableStateOf(false) }

    val bgColor by animateColorAsState(

        targetValue = if (isPressed) GreenLight.copy(alpha = 0.3f) else Color.White,

        label = ""

    )



    Card(

        modifier = modifier

            .height(130.dp)

            .pointerInput(Unit) {

                detectTapGestures(

                    onPress = {

                        isPressed = true

                        tryAwaitRelease()

                        isPressed = false

                    },

                    onTap = {

/*

when (title) {

"Fertilizer\nCalculator" -> navController.navigate("fertilizer_calculator")

"Pests &\nDiseases" -> navController.navigate("pests_diseases")

"Cultivation\nTips" -> navController.navigate("cultivation_tips")

"Pest\nAlerts" -> navController.navigate("pest_alerts")

}

*/

                    }

                )

            },

        colors = CardDefaults.cardColors(containerColor = bgColor),

        shape = RoundedCornerShape(18.dp),

        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),

        border = BorderStroke(1.dp, GreenPrimary.copy(alpha = 0.2f))

    ) {

        Box(

            Modifier

                .fillMaxSize()

                .padding(12.dp),

            contentAlignment = Alignment.Center

        ) {

            Text(

                title,

                color = TextDark,

                fontWeight = FontWeight.Medium,

                fontSize = 16.sp,

                lineHeight = 20.sp,

                textAlign = TextAlign.Center

            )

        }

    }

}