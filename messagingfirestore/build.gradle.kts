plugins {
    id("com.android.library")
    id("kotlin-android")
    id("maven-publish")
    id("signing")
    id("org.jetbrains.dokka") version "1.8.10"
}

val PUBLISH_GROUP_ID: String by extra("se.warting.firebase-compose")
val PUBLISH_VERSION: String by extra(rootProject.version as String)
val PUBLISH_ARTIFACT_ID by extra("messaging-firestore")

apply(from = "${rootProject.projectDir}/gradle/publish-module.gradle")

android {
    compileSdk = 31

    defaultConfig {
        minSdk = 21
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        viewBinding = true
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.2"
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
        freeCompilerArgs = listOfNotNull(
            "-Xopt-in=kotlin.RequiresOptIn",
            "-Xskip-prerelease-check",
        )
    }

    lint {
        baseline = file("lint-baseline.xml")
        checkReleaseBuilds = true
        checkAllWarnings = true
        warningsAsErrors = true
        abortOnError = true
        disable.add("GradleDependency")
        checkDependencies = true
        checkGeneratedSources = false
        sarifOutput = file("../lint-results-auth.sarif")
    }
    namespace = "se.warting.firebasecompose.messagingfirestore"
}

kotlin {
    // https://kotlinlang.org/docs/whatsnew14.html#explicit-api-mode-for-library-authors
    explicitApi()
}


dependencies {

    val coroutineVersion = "1.7.1"

    api(project(":core"))
    api(project(":messaging"))
    api(platform("com.google.firebase:firebase-bom:31.2.2"))
    val composeBom = platform("androidx.compose:compose-bom:2023.05.01")
    implementation(composeBom )
    androidTestImplementation(composeBom)

    api("androidx.compose.runtime:runtime")
    api("androidx.compose.ui:ui")
    api("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:$coroutineVersion")
    implementation("com.google.firebase:firebase-firestore-ktx")

    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}