# Notes

How I recreated Philipp Lackner's YouTube project<br>
"How to Make a Clean Architecture Note App<br>
(MVVM / CRUD / Jetpack Compose) - Android Studio Tutorial"<br>
with Android Studio Ladybug 2024.2.1,<br>
Kotlin 2.0.20 and many others modern updates.

## Features

- **Mocked Notating**: Simulates a notating environment for testing and learning purposes.
- **Clean Architecture**: Demonstrates the use of Clean architecture, separating concerns to enhance maintainability and scalability.
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
      - `di`: Package where we implement Dependency Injection with  Hilt.
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
  agp = "8.7.0"
  kotlin = "2.0.20"
  coreKtx = "1.13.1"
  junit = "4.13.2"
  junitVersion = "1.2.1"
  espressoCore = "3.6.1"
  lifecycleRuntimeKtx = "2.8.6"
  activityCompose = "1.9.2"
  composeBom = "2024.09.03"
  androidx-ui = "1.7.3"
  material3 = "1.3.0"
  
  #add these lines:
  
  #Compose Dependency
  navigation-compose = "2.8.2"
  #Coroutines
  ktx-coroutines = "1.9.0"
  #Dagger-Hilt
  hilt-android = "2.52"
  androidx-hilt = "1.2.0"
  #Room
  room = "2.6.1"
  annotation = "1.8.2"  
 
  [libraries]
  androidx-core-ktx = { group = "androidx.core", name = "core-ktx", version.ref = "coreKtx" }
  junit = { group = "junit", name = "junit", version.ref = "junit" }
  androidx-junit = { group = "androidx.test.ext", name = "junit", version.ref = "junitVersion" }
  androidx-espresso-core = { group = "androidx.test.espresso", name = "espresso-core", version.ref = "espressoCore" }
  androidx-compose-bom = { group = "androidx.compose", name = "compose-bom", version.ref = "composeBom" }
  androidx-lifecycle-runtime-ktx = { group = "androidx.lifecycle", name = "lifecycle-runtime-ktx", version.ref = "lifecycleRuntimeKtx" }
  androidx-activity-compose = { group = "androidx.activity", name = "activity-compose", version.ref = "activityCompose" }
  androidx-ui = { group = "androidx.compose.ui", name = "ui", version.ref = "androidx-ui" }
  androidx-ui-graphics = { group = "androidx.compose.ui", name = "ui-graphics", version.ref = "androidx-ui" }
  androidx-ui-tooling = { group = "androidx.compose.ui", name = "ui-tooling", version.ref = "androidx-ui" }
  androidx-ui-tooling-preview = { group = "androidx.compose.ui", name = "ui-tooling-preview", version.ref = "androidx-ui" }
  androidx-ui-test-manifest = { group = "androidx.compose.ui", name = "ui-test-manifest", version.ref = "androidx-ui" }
  androidx-ui-test-junit4 = { group = "androidx.compose.ui", name = "ui-test-junit4", version.ref = "androidx-ui" }
  androidx-material3 = { group = "androidx.compose.material3", name = "material3", version.ref = "material3" }
  
  #Add next libriaries
  androidx-material-icons-extended = { group = "androidx.compose.material", name = "material-icons-extended", version.ref = "androidx-ui" }
  lifecycle-viewmodel-compose = { group = "androidx.lifecycle", name = "lifecycle-viewmodel-compose", version.ref = "lifecycle" }
  androidx-navigation-compose = { group = "androidx.navigation", name = "navigation-compose", version.ref = "navigation-compose" }
  androidx-annotation = { group = "androidx.annotation", name = "annotation", version.ref = "annotation" }
  #Coroutines
  kotlinx-coroutines-core = { module = "org.jetbrains.kotlinx:kotlinx-coroutines-core", version.ref = "ktx-coroutines" }
  kotlinx-coroutines-android = { module = "org.jetbrains.kotlinx:kotlinx-coroutines-android", version.ref = "ktx-coroutines" }
  #Dagger-Hilt
  hilt-android = { module = "com.google.dagger:hilt-android", version.ref = "hilt-android" }
  hilt-android-compiler = { module = "com.google.dagger:hilt-android-compiler", version.ref = "hilt-android" }
  androidx-hilt-compiler = { group = "androidx.hilt", name = "hilt-compiler", version.ref = "androidx-hilt" }
  androidx-hilt-navigation-compose = { module = "androidx.hilt:hilt-navigation-compose", version.ref = "androidx-hilt" }
  #Room
  androidx-room-runtime = { group = "androidx.room", name = "room-runtime", version.ref = "room" }
  androidx-room-compiler = { group = "androidx.room", name = "room-compiler", version.ref = "room" }
  androidx-room-ktx = { group = "androidx.room", name = "room-ktx", version.ref = "room" }
  
  [plugins]
  android-application = { id = "com.android.application", version.ref = "agp" }
  kotlin-android = { id = "org.jetbrains.kotlin.android", version.ref = "kotlin" }
  kotlin-compose = { id = "org.jetbrains.kotlin.plugin.compose", version.ref = "kotlin" }
  
  ```

### build.gradle.kts(Notes)

```bash
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
  //Dagger-Hilt with KSP
    id("com.google.dagger.hilt.android") version "2.52" apply false
    id("com.google.devtools.ksp") version "2.0.20-1.0.24" apply false
}
  ```

### build.gradle.kts(:app)

```bash
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
  //Dagger-Hilt with KSP
    id("com.google.devtools.ksp") version "2.0.20-1.0.24"
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "dev.usrmrz.notes"
    compileSdk = 34

    defaultConfig {
        applicationId = "dev.usrmrz.notes"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
		vectorDrawables {
            useSupportLibrary = true
        }
  //Add this to use KSP
        ksp {
            arg("room.shemaLocation", "$projectDir/shemas")
        }
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
  //Add next lines	
	composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    sourceSets {
        getByName("main").java.srcDirs("build/generated/ksp/main/cotlin")
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    debugImplementation(libs.androidx.ui.tooling)
    implementation(libs.androidx.ui.tooling.preview)
    debugImplementation(libs.androidx.ui.test.manifest)
    androidTestImplementation(libs.androidx.ui.test.junit4)
    implementation(libs.androidx.material3)
	//Add these ones
	implementation(libs.androidx.material.icons.extended)
    implementation(libs.lifecycle.viewmodel.compose)
    implementation(libs.androidx.navigation.compose)
	implementation(libs.androidx.annotation)
    //Coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)
    //Dagger-Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
	ksp(libs.androidx.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)
	//Room
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
	implementation(libs.androidx.room.ktx)
    
}
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

hilt {
    enableAggregatingTask = true
}
	
  ```










