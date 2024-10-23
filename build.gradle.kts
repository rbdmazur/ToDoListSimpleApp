// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    id("org.jetbrains.kotlin.kapt") version "2.0.20-RC2" apply false
    id ("androidx.navigation.safeargs.kotlin") version "2.7.7" apply false
}