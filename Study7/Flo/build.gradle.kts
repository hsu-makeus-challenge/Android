// build.gradle.kts (Project: Flo)
// Top-level build file where you can add configuration options common to all sub-projects/modules.

plugins {
    alias(libs.plugins["android-application"]) apply false
    alias(libs.plugins["kotlin-android"]) apply false
    id("com.google.gms.google-services") version "4.4.2" apply false
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        // Firebase Google Services plugin 추가
        classpath("com.google.gms:google-services:4.3.15")
    }
}
