//package com.example.myapplication
//
//
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Menu
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.navigation.NavController
//import kotlinx.coroutines.delay
//import kotlinx.coroutines.launch
//
//// ✅ Splash Screen
//@Composable
//fun SplashScreen(navController: NavController) {
//    LaunchedEffect(true) {
//        delay(2000)
//        navController.navigate("login") {
//            popUpTo("splash") { inclusive = true }
//        }
//    }
//    Box(
//        modifier = Modifier.fillMaxSize(),
//        contentAlignment = Alignment.Center
//    ) {
//        Text("🌱 Smart Farming", style = MaterialTheme.typography.headlineMedium, color = MaterialTheme.colorScheme.primary)
//    }
//}
//
//// ✅ Login Screen
//@Composable
//fun LoginScreen(navController: NavController) {
//    Column(
//        modifier = Modifier.fillMaxSize().padding(24.dp),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text("Login", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
//        Spacer(Modifier.height(16.dp))
//
//        var email by remember { mutableStateOf("") }
//        var password by remember { mutableStateOf("") }
//
//        OutlinedTextField(
//            value = email,
//            onValueChange = { email = it },
//            label = { Text("Email") },
//            modifier = Modifier.fillMaxWidth()
//        )
//        Spacer(Modifier.height(8.dp))
//        OutlinedTextField(
//            value = password,
//            onValueChange = { password = it },
//            label = { Text("Password") },
//            modifier = Modifier.fillMaxWidth()
//        )
//        Spacer(Modifier.height(16.dp))
//        Button(
//            onClick = { navController.navigate("home") },
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Text("Login")
//        }
//        TextButton(onClick = { navController.navigate("signup") }) {
//            Text("Don’t have an account? Sign Up")
//        }
//    }
//}
//
//// ✅ Signup Screen
//@Composable
//fun SignupScreen(navController: NavController) {
//    Column(
//        modifier = Modifier.fillMaxSize().padding(24.dp),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text("Sign Up", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
//        Spacer(Modifier.height(16.dp))
//
//        var email by remember { mutableStateOf("") }
//        var password by remember { mutableStateOf("") }
//
//        OutlinedTextField(
//            value = email,
//            onValueChange = { email = it },
//            label = { Text("Email") },
//            modifier = Modifier.fillMaxWidth()
//        )
//        Spacer(Modifier.height(8.dp))
//        OutlinedTextField(
//            value = password,
//            onValueChange = { password = it },
//            label = { Text("Password") },
//            modifier = Modifier.fillMaxWidth()
//        )
//        Spacer(Modifier.height(16.dp))
//        Button(
//            onClick = { navController.navigate("login") },
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Text("Sign Up")
//        }
//    }
//}
//
//// ✅ Home Screen with Dashboard + Drawer
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun HomeScreen(navController: NavController) {
//    val drawerState = rememberDrawerState(DrawerValue.Closed)
//    val scope = rememberCoroutineScope()
//
//    ModalNavigationDrawer(
//        drawerContent = {
//            ModalDrawerSheet {
//                Text("Menu", modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.titleMedium)
//                NavigationDrawerItem(label = { Text("Home") }, selected = false, onClick = {
//                    scope.launch { drawerState.close() }
//                })
//                NavigationDrawerItem(label = { Text("Settings") }, selected = false, onClick = {
//                    scope.launch { drawerState.close() }
//                    navController.navigate("settings")
//                })
//                NavigationDrawerItem(label = { Text("Logout") }, selected = false, onClick = {
//                    scope.launch { drawerState.close() }
//                    navController.navigate("login") {
//                        popUpTo("home") { inclusive = true }
//                    }
//                })
//            }
//        },
//        drawerState = drawerState
//    ) {
//        Scaffold(
//            topBar = {
//                TopAppBar(
//                    title = { Text("Smart Farming") },
//                    navigationIcon = {
//                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
//                            Icon(Icons.Default.Menu, contentDescription = "Menu")
//                        }
//                    }
//                )
//            }
//        ) { paddingValues ->
//            Column(
//                modifier = Modifier.padding(paddingValues).padding(16.dp)
//            ) {
//                Text("Welcome, Farmer!", style = MaterialTheme.typography.headlineSmall)
//                Spacer(Modifier.height(16.dp))
//
//                // Dashboard cards
//                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
//                    DashboardCard("🌡 Temperature", "25°C")
//                    DashboardCard("💧 Humidity", "60%")
//                }
//                Spacer(Modifier.height(12.dp))
//                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
//                    DashboardCard("☀ Sunlight", "8 hrs")
//                    DashboardCard("🌱 Soil", "Moist")
//                }
//            }
//        }
//    }
//}
//
//@Composable
//
//fun DashboardCard(title: String, value: String, modifier: Modifier = Modifier) {
//    Card(
//        modifier = modifier.padding(4.dp),
//        shape = RoundedCornerShape(16.dp)
//    ) {
//        Column(
//            modifier = Modifier.padding(16.dp),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Text(title, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
//            Spacer(Modifier.height(8.dp))
//            Text(value, style = MaterialTheme.typography.headlineSmall, color = MaterialTheme.colorScheme.primary)
//        }
//    }
//}
//
//
//// ✅ Settings Screen
//@Composable
//fun SettingsScreen(navController: NavController) {
//    Column(
//        modifier = Modifier.fillMaxSize().padding(24.dp),
//        verticalArrangement = Arrangement.Top
//    ) {
//        Text("Settings", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
//        Spacer(Modifier.height(16.dp))
//        Text("- Language: English")
//        Text("- Notifications: Enabled")
//        Spacer(Modifier.height(16.dp))
//        Button(onClick = { navController.popBackStack() }) {
//            Text("Back")
//        }
//    }
//}
