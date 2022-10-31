plugins {
    id("com.gradle.enterprise") version "3.11.3"
}

buildCache {
    local {
        isEnabled = true
        directory = File(rootDir, "build-cache")
        removeUnusedEntriesAfterDays = 30
    }
    remote<HttpBuildCache> {
        isEnabled = false
    }
}

rootProject.name = "Firebase Compose"
include(":app")
include(":auth")
include(":core")
include(":dynamiclinks")
include(":messaging")
include(":messagingfirestore")
