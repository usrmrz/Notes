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

## Getting Started

### Prerequisites

Android Studio Ladybug | 2024.2.1<br>
Build #AI-242.21829.142.2421.12409432, built on September 24, 2024<br>
Runtime version: 21.0.3+-12282718-b509.11 amd64<br>
VM: OpenJDK 64-Bit Server VM by JetBrains s.r.o.<br>
Toolkit: sun.awt.windows.WToolkit<br>
Windows 10.0

### Project Structure

- **`app`**: Contains the main Android application code.
  - `schemas`: Db schemas.
  - `src.main`: Main package.
    - `java`: Java packages.
      - `dev.usrmrz.notes`: App package.
        - `di`: Package where we implement Dependency Injection with  Hilt
	    - `feature_note`: Main package set as feature
          - `data`: Data package.
	        - `data_source`: Data source.
            - `repository`: Data layer implemented in the repositories.
          - `domain`: Domain package.
            - `model`: Data models.
	        - `repository`: Domain layer implemented in the repositories.
	        - `use_case`: Notes use cases.
	        - `util`: Utils class and code for the domain
          - `presentation`: User Interface.
            - `add_edit_note`: Class and code for the AddEditNote.
	          - `components`: Components for the AddEditNote.
            - `notes`: Class and code for the Notes.
	          - `components`: Components for the Notes.
	        - `util`: Utils class and code for the presentation
        - `ui`: Package's theme
    - `res`: Android resources.
- **`gradle`**: Gradle wrapper and configuration files.




