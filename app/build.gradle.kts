plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.myapplication"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.myapplication"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        viewBinding= true
    }
    packaging {
        resources {
            excludes += "META-INF/INDEX.LIST"
            excludes += "META-INF/DEPENDENCIES"
            excludes += "META-INF/io.netty.versions.properties"
            // It's good practice to also exclude license files
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation("androidx.activity:activity-compose:1.9.3")
    implementation("androidx.compose.ui:ui:1.7.0")
    implementation("androidx.compose.material3:material3:1.3.0")
    implementation("androidx.compose.ui:ui-tooling-preview:1.7.0")

    // Navigation for Compose
    implementation("androidx.navigation:navigation-compose:2.8.0")

    // Coil for image loading
    implementation("io.coil-kt:coil-compose:2.6.0")
    implementation ("androidx.activity:activity-compose:1.9.3")
    implementation ("androidx.compose.ui:ui:1.7.0")
    implementation ("androidx.compose.ui:ui-tooling-preview:1.7.0")
    implementation ("androidx.compose.material3:material3:1.3.0")
    implementation ("androidx.navigation:navigation-compose:2.8.0")

    // Icons
    implementation ("androidx.compose.material:material-icons-extended:1.7.0")

    // Coil (if you later add images)
    implementation ("io.coil-kt:coil-compose:2.6.0")
    implementation(libs.androidx.ui.text)
    implementation(libs.generativeai)
    implementation(libs.play.services.games)
    implementation(libs.firebase.appdistribution.gradle)

    // Debug tools
    debugImplementation ("androidx.compose.ui:ui-tooling:1.7.0")
    debugImplementation ("androidx.compose.ui:ui-test-manifest:1.7.0")

// Retrofit for networking
    implementation("com.squareup.retrofit2:retrofit:2.9.0")

// Gson converter for serializing and deserializing JSON
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

// OkHttp Logging Interceptor for debugging network requests
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")

// Kotlin Coroutines for asynchronous programming
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1")

    implementation("androidx.datastore:datastore-preferences:1.0.0")
// ViewModel and LiveData (good practice for managing UI-related data)
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}