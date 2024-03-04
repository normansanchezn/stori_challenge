plugins {
    kotlin("kapt")
    id("kotlin-kapt")
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.storichallenge"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.storichallenge"
        minSdk = 24
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    val navVersion = "2.7.7"
    val multidexVersion = "2.0.1"
    val lifeDataVersion = "2.7.0"
    val viewModelVersion = "2.7.0"
    val activityVersion = "1.8.2"
    val fragmentVersion = "1.6.2"
    // val hiltVersion = "2.51"
    val roomVersion = "2.6.1"

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // Navigation Controller
    implementation("androidx.navigation:navigation-fragment-ktx:$navVersion")
    implementation("androidx.navigation:navigation-ui-ktx:$navVersion")

    // Fragment
    implementation("androidx.fragment:fragment-ktx:$fragmentVersion")

    // Activity
    implementation("androidx.activity:activity-ktx:$activityVersion")

    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$viewModelVersion")

    // LiveData
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifeDataVersion")

    // MultidexApplication
    implementation("androidx.multidex:multidex:$multidexVersion")

    // Room
    implementation("androidx.room:room-ktx:$roomVersion")

    // Biometric Authentication
    implementation("androidx.biometric:biometric-ktx:1.2.0-alpha05")

    implementation("com.google.dagger:hilt-android:2.44")
    kapt("com.google.dagger:hilt-android-compiler:2.44")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}

kapt {
    correctErrorTypes = true
}