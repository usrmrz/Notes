# Notes

Recreated Philipp Lackner's YouTube project<br>
"How to Make a Clean Architecture Note App<br>
(MVVM / CRUD / Jetpack Compose) - Android Studio Tutorial"<br>
with Android Studio Ladybug 2024.2.1 Patch 3,<br>
Kotlin 2.1.0 and many others modern updates.

Listing from https://www.youtube.com/watch?v=8YPXv7xKh2w <br><br>
https://docs.google.com/document/d/1mu3nGSc9ibjN64HHSlWRICWXKvbAVcjOqtSvMyXNjGA/edit?usp=sharing <br>


## Features

- **Mocked Notating**: Simulates a notating environment for testing and learning purposes.
- **Clean Architecture**: Demonstrates the use of Clean architecture, separating concerns to enhance
  maintainability and scalability.
- **Kotlin Language**: Developed entirely in Kotlin, promoting concise and expressive code.

## Prerequisites

Android Studio Ladybug | 2024.2.1 Patch 3<br>
Build #AI-242.23339.11.2421.12700392, built on November 22, 2024<br>
Runtime version: 21.0.3+-12282718-b509.11 amd64<br>
VM: OpenJDK 64-Bit Server VM by JetBrains s.r.o.<br>
Toolkit: sun.awt.windows.WToolkit<br>
Windows 10.0<br>
Kotlin plugin: K2 mode (Beta)

## Getting Started

### Project Structure

- **`app`**: Contains the main Android application code.
    - `schemas`: Db schemas.
    - `src.main`: Main package.
        - `java.dev.usrmrz.notes`: Java packages.
            - `di`: Package where we implement Dependency Injection with Hilt.
            - `feature_note`: Main package set as feature
                - `data`: Data layer.
                    - `data_source`: Data source.
                    - `repository`: Data layer implemented in the repositories.
                - `domain`: Domain layer.
                    - `model`: Data models.
                    - `repository`: Domain layer implemented in the repositories.
                    - `use_case`: Notes use cases.
                    - `util`: Utils class and code for the domain.
                - `presentation`: User Interface layer.
                    - `add_edit_note`: Class and code for the AddEditNote.
                        - `components`: Components for the AddEditNote.
                    - `notes`: Class and code for the Notes.
                        - `components`: Components for the Notes.
                    - `util`: Utils classes and code for the presentation.
            - `ui`: Package's theme.
        - `res`: Android resources.
- **`gradle`**: Gradle wrapper and configuration files.

## How to separate entity from domain model

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

2. Create NoteEntity in data/data_source:

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

3. Change the table name and type of function arguments in dao queries:

```bash
@Dao
interface NoteDao {

    @Query("SELECT * FROM notes")
    fun getNotes(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes WHERE id = :id")
    suspend fun getNoteById(id: Int): NoteEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: NoteEntity)

    @Delete
    suspend fun deleteNote(note: NoteEntity)
}
  ```

4. Add extension functions and edit repository implementation:

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
## Configuration files

### libs.versions.toml

```bash
[versions]
activityCompose = "1.9.3"
agp = "8.7.3"
androidxHilt = "1.2.0"
androidxLifecycle = "2.8.7"
annotation = "1.9.1"
appcompat = "1.7.0"
autonomousapps = "2.6.1"
compileSdk = "35"
composeBom = "2024.12.01"
coreKtx = "1.15.0"
coroutines = "1.10.1"
fragment = "1.8.5"
hilt = "2.54"
javax = "1"
junit = "4.13.2"
junitVersion = "1.2.1"
kotlin = "2.1.0"
ksp = "2.1.0-1.0.29"
minSdk = "25"
navigationCompose = "2.8.5"
room = "2.6.1"
sqlite = "2.4.0"
targetSdk = "35"
test-monitor = "1.7.2"

[libraries]
androidx-annotation = { group = "androidx.annotation", name = "annotation", version.ref = "annotation" }
androidx-core = { group = "androidx.core", name = "core", version.ref = "coreKtx" }
androidx-fragment = { group = "androidx.fragment", name = "fragment", version.ref = "fragment" }
androidx-lifecycle = { group = "androidx.lifecycle", name = "lifecycle-viewmodel-compose", version.ref = "androidxLifecycle" }
androidx-navigation-common = { group = "androidx.navigation", name = "navigation-common", version.ref = "navigationCompose" }
androidx-navigation-compose = { group = "androidx.navigation", name = "navigation-compose", version.ref = "navigationCompose" }
androidx-navigation-runtime = { group = "androidx.navigation", name = "navigation-runtime", version.ref = "navigationCompose" }
androidx-savedstate = { group = "androidx.lifecycle", name = "lifecycle-viewmodel-savedstate", version.ref = "androidxLifecycle" }
androidx-sqlite = { group = "androidx.sqlite", name = "sqlite", version.ref = "sqlite" }
androidx-viewmodel = { group = "androidx.lifecycle", name = "lifecycle-viewmodel", version.ref = "androidxLifecycle" }
javax-inject = { group = "javax.inject", name = "javax.inject", version.ref = "javax" }

androidx-activity = { group = "androidx.activity", name = "activity", version.ref = "activityCompose" }
androidx-activity-compose = { group = "androidx.activity", name = "activity-compose", version.ref = "activityCompose" }
androidx-appcompat = { group = "androidx.appcompat", name = "appcompat", version.ref = "appcompat" }
androidx-compose-bom = { group = "androidx.compose", name = "compose-bom", version.ref = "composeBom" }
androidx-junit = { group = "androidx.test.ext", name = "junit", version.ref = "junitVersion" }
androidx-material3 = { group = "androidx.compose.material3", name = "material3" }
androidx-ui = { group = "androidx.compose.ui", name = "ui" }
androidx-ui-geometry = { group = "androidx.compose.ui", name = "ui-geometry" }
androidx-ui-graphics = { group = "androidx.compose.ui", name = "ui-graphics" }
androidx-ui-test-manifest = { group = "androidx.compose.ui", name = "ui-test-manifest" }
androidx-ui-text = { group = "androidx.compose.ui", name = "ui-text" }
androidx-ui-tooling = { group = "androidx.compose.ui", name = "ui-tooling" }
androidx-ui-unit = { group = "androidx.compose.ui", name = "ui-unit" }
animation = { group = "androidx.compose.animation", name = "animation" }
animation-core = { group = "androidx.compose.animation", name = "animation-core" }
compose-material-icons-core = { group = "androidx.compose.material", name = "material-icons-core" }
compose-material-icons-extended = { group = "androidx.compose.material", name = "material-icons-extended" }
foundation = { group = "androidx.compose.foundation", name = "foundation" }
foundation-layout = { group = "androidx.compose.foundation", name = "foundation-layout" }
junit = { group = "junit", name = "junit", version.ref = "junit" }
runtime = { group = "androidx.compose.runtime", name = "runtime" }
test-monitor = { group = "androidx.test", name = "monitor", version.ref = "test-monitor" }
#Coroutines
kotlinx-coroutines-android = { group = "org.jetbrains.kotlinx", name = "kotlinx-coroutines-android", version.ref = "coroutines"}
kotlinx-coroutines-core = { group = "org.jetbrains.kotlinx", name = "kotlinx-coroutines-core", version.ref = "coroutines" }
#Dagger-Hilt
androidx-hilt-compiler = { group = "androidx.hilt", name = "hilt-compiler", version.ref = "androidxHilt" }
androidx-hilt-navigation-compose = { group = "androidx.hilt", name = "hilt-navigation-compose", version.ref = "androidxHilt" }
dagger = { group = "com.google.dagger", name = "dagger", version.ref = "hilt" }
hilt-android-compiler = { group = "com.google.dagger", name = "hilt-android-compiler", version.ref = "hilt" }
hilt-android-core = { group = "com.google.dagger", name = "hilt-android", version.ref = "hilt" }
hilt-core = { group = "com.google.dagger", name = "hilt-core", version.ref = "hilt" }
#Room
androidx-room-common = { group = "androidx.room", name = "room-common", version.ref = "room" }
androidx-room-compiler = { group = "androidx.room", name = "room-compiler", version.ref = "room" }
androidx-room-ktx = { group = "androidx.room", name = "room-ktx", version.ref = "room" }
androidx-room-runtime = { group = "androidx.room", name = "room-runtime", version.ref = "room" }

[plugins]
android-application = { id = "com.android.application", version.ref = "agp" }
autonomousapps = { id = "com.autonomousapps.dependency-analysis", version.ref = "autonomousapps" }
hilt = { id = "com.google.dagger.hilt.android", version.ref = "hilt" }
kotlin-android = { id = "org.jetbrains.kotlin.android", version.ref = "kotlin" }
kotlin-compose = { id = "org.jetbrains.kotlin.plugin.compose", version.ref = "kotlin" }
ksp = { id = "com.google.devtools.ksp", version.ref = "ksp" }

[bundles]
androidx = [
    "androidx-annotation",
    "androidx-core",
    "androidx-fragment",
    "androidx-lifecycle",
    "androidx-navigation-common",
    "androidx-navigation-runtime",
    "androidx-savedstate",
    "androidx-sqlite",
    "androidx-viewmodel",
    "javax-inject",
]
compose = [
    "androidx-activity",
    "androidx-activity-compose",
    "androidx-appcompat",
    "androidx-material3",
    "androidx-navigation-compose",
    "androidx-ui",
    "androidx-ui-geometry",
    "androidx-ui-graphics",
    "androidx-ui-text",
    "androidx-ui-unit",
    "animation",
    "animation-core",
    "compose-material-icons-core",
    "compose-material-icons-extended",
    "foundation",
    "foundation-layout",
    "runtime",
]
coroutines = [
    "kotlinx-coroutines-core",
]
dagger-hilt = [
    "androidx-hilt-navigation-compose",
    "dagger",
    "hilt-android-core",
    "hilt-core",
]
room = [
    "androidx-room-common",
    "androidx-room-ktx",
    "androidx-room-runtime",
]
ksp = [
    "androidx-hilt-compiler",
    "androidx-room-compiler",
    "hilt-android-compiler",
]
compose-debug = [
    "androidx-ui-tooling",
]
android-test = [
    "androidx-junit",
    "junit",
    "test-monitor",
]
  ```

### build.gradle.kts(Notes)

```bash
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.autonomousapps) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.ksp) apply false
}
  ```

### build.gradle.kts(:app)

```bash
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.autonomousapps)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
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
        vectorDrawables {
            useSupportLibrary = true
        }

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
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
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

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.androidx)
    implementation(libs.bundles.compose)
    implementation(libs.bundles.coroutines)
    implementation(libs.bundles.dagger.hilt)
    implementation(libs.bundles.room)
    ksp(libs.bundles.ksp)

    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.bundles.android.test)
    debugImplementation(libs.bundles.compose.debug)
    debugRuntimeOnly(libs.androidx.ui.test.manifest)
    runtimeOnly(libs.kotlinx.coroutines.android)
    testImplementation(libs.junit)
}	
  ```