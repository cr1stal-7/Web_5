plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    namespace = "com.example.airplaneapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.airplaneapp"
        minSdk = 21
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        // Для доступа к интернету
        manifestPlaceholders["appPackageName"] = "com.example.airplaneapp"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    // Использование Kotlin Compose
    buildFeatures {
        compose = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    composeOptions {

        kotlinCompilerExtensionVersion = "1.4.7"


    }

    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation(platform("androidx.compose:compose-bom:2023.09.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")

    // Retrofit и Gson
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.google.android.material:material:1.9.0")
    // Coil для загрузки изображений
    implementation("io.coil-kt:coil-compose:2.4.0")

    // Навигация Compose
    implementation("androidx.navigation:navigation-compose:2.7.0")

    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")

    // Корутины
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.2")

    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}