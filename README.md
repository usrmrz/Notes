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

### How to separate entity from domain model

1. Change Note.kt in domain layer:
```bash
data class Note(
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int,
    val id: Int? = null
) {
    companion object {
        val noteColors = listOf(RedOrange, LightGreen, Violet, BabyBlue, RedPink)
    }
}

class InvalidNoteException(message: String) : Exception(message)
  ```
2. Create NoteEntity in data/data_source
```bash
@Entity(tableName = "notes")
data class NoteEntity(
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int,
    @PrimaryKey val id: Int? = null
)
  ```
3. Add extension functions and edit repository implementation
```bash
class NoteRepositoryImpl(
    private val dao: NoteDao
) : NoteRepository {

    private fun NoteEntity.toDomainModel(): Note {
        return Note(
            title = this.title,
            content = this.content,
            timestamp = this.timestamp,
            color = this.color,
            id = this.id
        )
    }

    private fun Note.toEntity(): NoteEntity {
        return NoteEntity(
            title = this.title,
            content = this.content,
            timestamp = this.timestamp,
            color = this.color,
            id = this.id
        )
    }

    override fun getNotes(): Flow<List<Note>> {
        return dao.getNotes().map { entities ->
            entities.map { it.toDomainModel() }
        }
    }

    override suspend fun getNoteById(id: Int): Note? {
        return dao.getNoteById(id)?.toDomainModel()
    }

    override suspend fun insertNote(note: Note) {
        dao.insertNote(note.toEntity())
    }

    override suspend fun deleteNote(note: Note) {
        dao.deleteNote(note.toEntity())
    }
}
  ```




### libs.versions.toml

```bash
[versions]
agp = "8.7.3"
kotlin = "2.1.0"
coreKtx = "1.15.0"
junit = "4.13.2"
junitVersion = "1.2.1"
espressoCore = "3.6.1"
androidxLifecycle = "2.8.7"
activityCompose = "1.9.3"
navigationCompose = "2.8.5"
composeBom = "2024.12.01"
compileSdk = "35"
targetSdk = "35"
minSdk = "21"
coroutines = "1.9.0"
hilt = "2.53.1"
androidxHilt = "1.2.0"
ksp = "2.1.0-1.0.29"
room = "2.6.1"
appcompat = "1.7.0"

[libraries]
androidx-core-ktx = { group = "androidx.core", name = "core-ktx", version.ref = "coreKtx" }
androidx-lifecycle-runtime-ktx = { group = "androidx.lifecycle", name = "lifecycle-runtime-ktx", version.ref = "androidxLifecycle" }
androidx-activity-compose = { group = "androidx.activity", name = "activity-compose", version.ref = "activityCompose" }
androidx-navigation-compose = { group = "androidx.navigation", name = "navigation-compose", version.ref = "navigationCompose" }
junit = { group = "junit", name = "junit", version.ref = "junit" }
androidx-junit = { group = "androidx.test.ext", name = "junit", version.ref = "junitVersion" }
androidx-espresso-core = { group = "androidx.test.espresso", name = "espresso-core", version.ref = "espressoCore" }
androidx-compose-bom = { group = "androidx.compose", name = "compose-bom", version.ref = "composeBom" }
androidx-ui = { group = "androidx.compose.ui", name = "ui" }
androidx-ui-graphics = { group = "androidx.compose.ui", name = "ui-graphics" }
androidx-ui-geometry = { group = "androidx.compose.ui", name = "ui-geometry" }
androidx-ui-tooling = { group = "androidx.compose.ui", name = "ui-tooling" }
androidx-ui-tooling-preview = { group = "androidx.compose.ui", name = "ui-tooling-preview" }
androidx-ui-test-manifest = { group = "androidx.compose.ui", name = "ui-test-manifest" }
androidx-ui-test-junit4 = { group = "androidx.compose.ui", name = "ui-test-junit4" }
androidx-material3 = { group = "androidx.compose.material3", name = "material3" }
compose-material-icons-extended = { group = "androidx.compose.material", name = "material-icons-extended" }
androidx-appcompat = { group = "androidx.appcompat", name = "appcompat", version.ref = "appcompat" }
#Coroutines
kotlinx-coroutines-core = { group = "org.jetbrains.kotlinx", name = "kotlinx-coroutines-core", version.ref = "coroutines" }
kotlinx-coroutines-android = { group = "org.jetbrains.kotlinx", name = "kotlinx-coroutines-android", version.ref = "coroutines"}
#Dagger-Hilt
hilt-android-core = { group = "com.google.dagger", name = "hilt-android", version.ref = "hilt" }
hilt-android-compiler = { group = "com.google.dagger", name = "hilt-android-compiler", version.ref = "hilt" }
androidx-hilt-navigation-compose = { group = "androidx.hilt", name = "hilt-navigation-compose", version.ref = "androidxHilt" }
androidx-hilt-compiler = { group = "androidx.hilt", name = "hilt-compiler", version.ref = "androidxHilt" }
#Room
androidx-room-runtime = { group = "androidx.room", name = "room-runtime", version.ref = "room" }
androidx-room-compiler = { group = "androidx.room", name = "room-compiler", version.ref = "room" }
androidx-room-ktx = { group = "androidx.room", name = "room-ktx", version.ref = "room" }

[plugins]
android-application = { id = "com.android.application", version.ref = "agp" }
kotlin-android = { id = "org.jetbrains.kotlin.android", version.ref = "kotlin" }
kotlin-compose = { id = "org.jetbrains.kotlin.plugin.compose", version.ref = "kotlin" }
hilt = { id = "com.google.dagger.hilt.android", version.ref = "hilt" }
ksp = { id = "com.google.devtools.ksp", version.ref = "ksp" }

[bundles]
compose = [
    "androidx-activity-compose",
    "androidx-navigation-compose",
    "androidx-ui",
    "androidx-ui-graphics",
    "androidx-ui-geometry",
    "androidx-ui-tooling-preview",
    "androidx-material3",
    "compose-material-icons-extended",
    "androidx-appcompat"
]
coroutines = [
    "kotlinx-coroutines-core",
    "kotlinx-coroutines-android",
]
hilt = [
    "hilt-android-core",
    "androidx-hilt-navigation-compose",
]
room = [
    "androidx-room-runtime",
    "androidx-room-ktx",
]
ksp = [
    "hilt-android-compiler",
    "androidx-hilt-compiler",
    "androidx-room-compiler",
]
compose-debug = [
    "androidx-ui-tooling",
    "androidx-ui-test-manifest",
]
android-test = [
    "androidx-junit",
    "androidx-espresso-core",
    "androidx-ui-test-junit4",
]
  ```

### build.gradle.kts(Notes)

```bash
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.ksp) apply false
}
  ```

### build.gradle.kts(:app)

```bash
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt)
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

        ksp {
            arg("room.schemaLocation", "$projectDir/schemas")
        }
    }
    sourceSets {
        getByName("main").java.srcDirs("build/generated/ksp/main/kotlin")
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
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.compose)
    implementation(libs.bundles.coroutines)
    implementation(libs.bundles.hilt)
    implementation(libs.bundles.room)
    ksp(libs.bundles.ksp)

    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.bundles.android.test)
    debugImplementation(libs.bundles.compose.debug)
    testImplementation(libs.junit)
}	
  ```










