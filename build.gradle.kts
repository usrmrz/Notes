// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
//    add these lines
//    alias(libs.plugins.kapt) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.hilt) apply false
//    id("com.google.dagger.hilt.android") version "2.52" apply false
//    id("com.google.devtools.ksp") version "2.0.20-1.0.24"
}