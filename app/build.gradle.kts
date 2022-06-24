plugins {
    id("com.android.application")
    id("kotlin-android")
    id("com.google.gms.google-services")
}

val composeVersion = "1.2.0-beta02"


val DEFAULT_WEB_CLIENT_ID: String =
    com.android.build.gradle.internal.cxx.configure.gradleLocalProperties(rootDir)
        .getProperty("DEFAULT_WEB_CLIENT_ID", "CHANGE_ME")


android {
    compileSdk = 31

    defaultConfig {
        applicationId = "se.warting.firebasecompose"
        minSdk = 21
        targetSdk = 31
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        resValue("string", "web_client_id", DEFAULT_WEB_CLIENT_ID)
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            resValue("string", "web_client_id", DEFAULT_WEB_CLIENT_ID)
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
        freeCompilerArgs = listOfNotNull(
            "-Xopt-in=kotlin.RequiresOptIn",
            "-Xskip-prerelease-check"
        )
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = composeVersion
    }

    lint {
        baseline = file("lint-baseline.xml")
        checkReleaseBuilds = true
        checkAllWarnings = true
        warningsAsErrors = true
        abortOnError = true
        disable.add("LogConditional")
        disable.add("GradleDependency")
        checkDependencies = true
        checkGeneratedSources = false
        sarifOutput = file("../lint-results-app.sarif")
    }
}


dependencies {
    implementation(platform("com.google.firebase:firebase-bom:30.2.0"))
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    implementation("androidx.core:core-ktx:1.8.0")
    implementation(project(":auth"))
    implementation(project(":dynamiclinks"))
    implementation(project(":messaging"))
    implementation(project(":messagingfirestore"))
    implementation("androidx.appcompat:appcompat:1.4.2")
    implementation("com.google.android.material:material:1.6.1")
    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.compose.material:material:$composeVersion")
    implementation("androidx.compose.ui:ui-tooling-preview:$composeVersion")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.4.1")
    implementation("androidx.activity:activity-compose:1.4.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$composeVersion")
    debugImplementation("androidx.compose.ui:ui-tooling:$composeVersion")
}
