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
    - `java`: Java packages.
      - `dev.usrmrz.notes`: App package.
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
  
  
  
  [plugins]
  
  
  ```

### build.gradle.kts(Notes)

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
  
  [libraries]
  
  
  
  [plugins]
  
  
  ```

### build.gradle.kts(:app)

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
  
  [libraries]
  
  
  
  [plugins]
  
  
  ```










