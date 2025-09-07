import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.com.google.dagger)
    alias(libs.plugins.devtools.ksp)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.google.android.libraries.mapsplatform.secrets.gradle.plugin)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.jesuskrastev.ailingo"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.jesuskrastev.ailingo"
        minSdk = 35
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        val properties = Properties()
        properties.load(FileInputStream(rootProject.file("local.properties")))
        buildConfigField("String", "apiKey", "\"${properties.getProperty("apiKey")}\"")
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
        buildConfig = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.generativeai)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.compose.navigation)
    // Icons
    implementation(libs.androidx.material.icons.core)
    implementation(libs.androidx.material.icons.extended)
    // Coil
    implementation(libs.coil.compose)
    // Room
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)
    // Hilt
    implementation(libs.dagger.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)
    ksp(libs.dagger.hilt.android.compiler)
    kspAndroidTest(libs.dagger.hilt.android.compiler)
    // JSON parser
    implementation(libs.kotlinx.serialization.json)
    // Splash
    implementation(libs.androidx.core.splashscreen)
    // Charts
    implementation (libs.compose.charts)
    // Firebase
    implementation(libs.firebase.bom)
}