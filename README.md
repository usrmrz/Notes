# Notes

How I recreated Philipp Lackner's YouTube project<br>
"How to Make a Clean Architecture Note App<br>
(MVVM / CRUD / Jetpack Compose) - Android Studio Tutorial"<br>
with Android Studio Ladybug 2024.2.1,<br>
Kotlin 2.0.20 and many others modern updates.

Listing from https://www.youtube.com/watch?v=8YPXv7xKh2w <br><br>
https://docs.google.com/document/d/1mu3nGSc9ibjN64HHSlWRICWXKvbAVcjOqtSvMyXNjGA/edit?usp=sharing <br>


## Features

- **Mocked Notating**: Simulates a notating environment for testing and learning purposes.
- **Clean Architecture**: Demonstrates the use of Clean architecture, separating concerns to enhance
  maintainability and scalability.
- **Kotlin Language**: Developed entirely in Kotlin, promoting concise and expressive code.

## Prerequisites

Android Studio Ladybug | 2024.2.1<br>
Build #AI-242.21829.142.2421.12409432, built on September 24, 2024<br>
Runtime version: 21.0.3+-12282718-b509.11 amd64<br>
VM: OpenJDK 64-Bit Server VM by JetBrains s.r.o.<br>
Toolkit: sun.awt.windows.WToolkit<br>
Windows 10.0

## Getting Started

### Project Structure

- **`app`**: Contains the main Android application code.
    - `schemas`: Db schemas.
    - `src.main`: Main package.
        - `java.dev.usrmrz.notes`: Java packages..
            - `di`: Package where we implement Dependency Injection with Hilt.
            - `feature_note`: Main package set as feature
                - `data`: Data package.
                    - `data_source`: Data source.
                    - `repository`: Data layer implemented in the repositories.
                - `domain`: Domain package.
                    - `model`: Data models.
                    - `repository`: Domain layer implemented in the repositories.
                    - `use_case`: Notes use cases.
                    - `util`: Utils class and code for the domain.
                - `presentation`: User Interface.
                    - `add_edit_note`: Class and code for the AddEditNote.
                        - `components`: Components for the AddEditNote.
                    - `notes`: Class and code for the Notes.
                        - `components`: Components for the Notes.
                    - `util`: Utils class and code for the presentation.
            - `ui`: Package's theme.
        - `res`: Android resources.
- **`gradle`**: Gradle wrapper and configuration files.

### libs.versions.toml

```bash
[versions]
agp = "8.7.1"
kotlin = "2.0.20"
coreKtx = "1.15.0"
junit = "4.13.2"
junitVersion = "1.2.1"
espressoCore = "3.6.1"
androidxlifecycle = "2.8.7"
activityCompose = "1.9.3"
composeBom = "2024.10.01"
#androidx-ui = "1.7.4"
material3 = "1.3.1"
#Add this versions
#@keep
compileSdk = "35"
targetSdk = "34"
minSdk = "21"
androidxComposeCompiler = "1.5.1"
ktx-coroutines = "1.9.0"
hilt-android = "2.52"
#androidx-hilt = "1.2.0"
ksp = "2.0.20-1.0.24"
room = "2.6.1"

[libraries]
androidx-core-ktx = { group = "androidx.core", name = "core-ktx", version.ref = "coreKtx" }
junit = { group = "junit", name = "junit", version.ref = "junit" }
androidx-junit = { group = "androidx.test.ext", name = "junit", version.ref = "junitVersion" }
androidx-espresso-core = { group = "androidx.test.espresso", name = "espresso-core", version.ref = "espressoCore" }
androidx-lifecycle-runtime-ktx = { group = "androidx.lifecycle", name = "lifecycle-runtime-ktx", version.ref = "androidxlifecycle" }
androidx-activity-compose = { group = "androidx.activity", name = "activity-compose", version.ref = "activityCompose" }
androidx-compose-bom = { group = "androidx.compose", name = "compose-bom", version.ref = "composeBom" }
androidx-ui = { group = "androidx.compose.ui", name = "ui" }
androidx-ui-graphics = { group = "androidx.compose.ui", name = "ui-graphics" }
androidx-ui-tooling = { group = "androidx.compose.ui", name = "ui-tooling" }
androidx-ui-tooling-preview = { group = "androidx.compose.ui", name = "ui-tooling-preview" }
androidx-ui-test-manifest = { group = "androidx.compose.ui", name = "ui-test-manifest" }
androidx-ui-test-junit4 = { group = "androidx.compose.ui", name = "ui-test-junit4" }
androidx-material3 = { group = "androidx.compose.material3", name = "material3", version.ref = "material3" }
#Add next libraries
#lifecycle-viewmodel-compose = { group = "androidx.lifecycle", name = "lifecycle-viewmodel-compose", version.ref = "androidxlifecycle" }
androidx-material-icons-extended = { group = "androidx.compose.material", name = "material-icons-extended" }

#Coroutines
kotlinx-coroutines-core = { group = "org.jetbrains.kotlinx", name = "kotlinx-coroutines-core", version.ref = "ktx-coroutines" }
kotlinx-coroutines-android = { group = "org.jetbrains.kotlinx", name = "kotlinx-coroutines-android", version.ref = "ktx-coroutines"}
#Dagger-Hilt
hilt-android-core = { group = "com.google.dagger", name = "hilt-android", version.ref = "hilt-android" }
hilt-android-compiler = { group = "com.google.dagger", name = "hilt-android-compiler", version.ref = "hilt-android" }
#androidx-hilt-compiler = { group = "androidx.hilt", name = "hilt-compiler", version.ref = "androidx-hilt" }
#androidx-hilt-navigation-compose = { group = "androidx.hilt", name = "hilt-navigation-compose", version.ref = "androidx-hilt" }
#Room
androidx-room-runtime = { group = "androidx.room", name = "room-runtime", version.ref = "room" }
androidx-room-compiler = { group = "androidx.room", name = "room-compiler", version.ref = "room" }
androidx-room-ktx = { group = "androidx.room", name = "room-ktx", version.ref = "room" }

[plugins]
android-application = { id = "com.android.application", version.ref = "agp" }
kotlin-android = { id = "org.jetbrains.kotlin.android", version.ref = "kotlin" }
kotlin-compose = { id = "org.jetbrains.kotlin.plugin.compose", version.ref = "kotlin" }
hilt = { id = "com.google.dagger.hilt.android", version.ref = "hilt-android" }
#kapt = { id = "org.jetbrains.kotlin.kapt", version.ref = "kotlin" }
ksp = { id = "com.google.devtools.ksp", version.ref = "ksp" }

  ```

### build.gradle.kts(Notes)

```bash
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
//    add these lines
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.hilt) apply false
}
  ```

### build.gradle.kts(:app)

```bash
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    //add this to use hilt with KSP
    alias(libs.plugins.hilt)
//    alias(libs.plugins.kapt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "dev.usrmrz.notes"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "dev.usrmrz.notes"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
//        add this to use KSP
//        ksp {
//            arg("room.schemaLocation", "$projectDir/schemas")
//        }
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
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    kotlinOptions {
        jvmTarget = "21"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.androidxComposeCompiler.get().toString()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
//    sourceSets {
//        getByName("main").java.srcDirs("build/generated/ksp/main/kotlin")
//    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
//    add these dependencies
    implementation(libs.androidx.material.icons.extended)
//    implementation(libs.lifecycle.viewmodel.compose)
//    Coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)
//    Dagger-Hilt
    implementation(libs.hilt.android.core)
//    kapt(libs.hilt.android.compiler)
    ksp(libs.hilt.android.compiler)
//    ksp(libs.androidx.hilt.compiler)
//    implementation(libs.androidx.hilt.navigation.compose)
//    Room
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)

    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.test.manifest)
    debugImplementation(libs.androidx.ui.tooling)
    testImplementation(libs.junit)
}
	
  ```










