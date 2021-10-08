plugins {
    id("com.android.library")
    id("kotlin-android")
    id("maven-publish")
    id("signing")
    id("org.jetbrains.dokka") version "1.5.30"
    id("com.gladed.androidgitversion") version "0.4.14"
}


androidGitVersion {
    tagPattern = "^v[0-9]+.*"
}


val PUBLISH_GROUP_ID: String by extra("se.warting.firebase-compose")
val PUBLISH_VERSION: String by extra(androidGitVersion.name().replace("v", ""))
val PUBLISH_ARTIFACT_ID by extra("dynamic-links")

apply(from = "${rootProject.projectDir}/gradle/publish-module.gradle")

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
        kotlinCompilerExtensionVersion = "1.0.3"
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
        freeCompilerArgs = listOfNotNull(
            "-Xopt-in=kotlin.RequiresOptIn",
            "-Xskip-prerelease-check"
        )
    }

    lint {
        lintConfig = file("$rootDir/config/lint/lint.xml")
        lintConfig = file("$rootDir/config/lint/lint.xml")
        //baseline(file("lint-baseline.xml"))
    }
}

kotlin {
    // https://kotlinlang.org/docs/whatsnew14.html#explicit-api-mode-for-library-authors
    explicitApi()
}


dependencies {

    val composeVersion = "1.0.3"
    val coroutineVersion = "1.5.2"

    implementation(project(":core"))
    api(platform("com.google.firebase:firebase-bom:28.4.2"))
    api("com.google.firebase:firebase-dynamic-links-ktx")

    api("androidx.compose.runtime:runtime:$composeVersion")
    api("androidx.compose.ui:ui:$composeVersion")
    api("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:$coroutineVersion")

    implementation("androidx.core:core-ktx:1.6.0")
    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation("com.google.android.material:material:1.4.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}