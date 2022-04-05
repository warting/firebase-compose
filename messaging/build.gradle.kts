plugins {
    id("com.android.library")
    id("kotlin-android")
    id("maven-publish")
    id("signing")
    id("org.jetbrains.dokka") version "1.6.10"
}

val PUBLISH_GROUP_ID: String by extra("se.warting.firebase-compose")
val PUBLISH_VERSION: String by extra(rootProject.version as String)
val PUBLISH_ARTIFACT_ID by extra("messaging")

apply(from = "${rootProject.projectDir}/gradle/publish-module.gradle")

val composeVersion = "1.2.0-alpha06"

android {
    compileSdk = 31

    defaultConfig {
        minSdk = 21
        targetSdk = 31
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
        kotlinCompilerExtensionVersion = composeVersion
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
        freeCompilerArgs = listOfNotNull(
            "-Xopt-in=kotlin.RequiresOptIn",
            "-Xskip-prerelease-check"
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
}

kotlin {
    // https://kotlinlang.org/docs/whatsnew14.html#explicit-api-mode-for-library-authors
    explicitApi()
}


dependencies {

    val coroutineVersion = "1.6.1"

    api(project(":core"))
    api(platform("com.google.firebase:firebase-bom:29.3.0"))
    api("com.google.firebase:firebase-messaging-ktx")

    api("androidx.compose.runtime:runtime:$composeVersion")
    api("androidx.compose.ui:ui:$composeVersion")
    api("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:$coroutineVersion")

    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.appcompat:appcompat:1.4.1")
    implementation("com.google.android.material:material:1.5.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}