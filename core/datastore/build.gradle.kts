plugins {
    alias(libs.plugins.at.android.library)
    alias(libs.plugins.at.android.hilt)
}

android {
    namespace = "com.wei.amazingtalker.core.datastore"

    defaultConfig {
        consumerProguardFiles("consumer-proguard-rules.pro")
    }

    testOptions {
        unitTests {
            isReturnDefaultValues = true
        }
    }
}

dependencies {
    api(projects.core.datastoreProto)
    implementation(projects.core.common)
    implementation(projects.core.model)

    // DataStore
    implementation(libs.androidx.datastore)

    // Protobuf
    implementation(libs.protobuf.kotlin.lite)
}
