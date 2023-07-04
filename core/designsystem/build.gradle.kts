plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.android)
    kotlin("kapt")
}

android {
    namespace = "com.wei.amazingtalker_recruit.core.designsystem"
    compileSdk = 33

    defaultConfig {
        minSdk = 23

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
        isCoreLibraryDesugaringEnabled = true
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.androidxComposeCompiler.get()
    }

    buildFeatures {
        viewBinding = true
        compose = true
    }
}

dependencies {

    // PublicLibs
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.tracing.ktx)
    coreLibraryDesugaring(libs.android.desugarJdkLibs)
    testImplementation(libs.junit4)
    androidTestImplementation(libs.androidx.test.ext)
    androidTestImplementation(libs.androidx.test.espresso.core)

    // Coroutines
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.coroutines.core)
    testImplementation(libs.kotlinx.coroutines.test)

    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    // Timber
    implementation(libs.timber)

    // Compose
    val composeBom = platform(libs.androidx.compose.bom)
    implementation(composeBom)
    androidTestImplementation(composeBom)

    // Material Design 3
    implementation(libs.androidx.compose.material3.core)
    implementation(libs.androidx.compose.material3.windowSizeClass)

    // main APIs for the underlying toolkit systems,
    // such as input and measurement/layout
    implementation(libs.androidx.compose.ui.core)
    implementation(libs.androidx.compose.ui.util)
    implementation(libs.androidx.compose.foundation)

    // Integration with Navigation and Hilt
    implementation(libs.androidx.hilt.navigation.compose)

    // Android Studio Preview support
    implementation(libs.androidx.compose.ui.tooling.preview)
    debugImplementation(libs.androidx.compose.ui.ui.tooling)

    // UI Tests
    implementation(libs.androidx.compose.ui.ui.test.junit4)
    androidTestImplementation(libs.androidx.compose.ui.test)
    debugImplementation(libs.androidx.compose.ui.testManifest)

    // Optional - Integration with activities
    implementation(libs.androidx.activity.compose)

    // Optional - Integration with LiveData
    implementation(libs.androidx.compose.runtime.livedata)
}