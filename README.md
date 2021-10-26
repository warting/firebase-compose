[![Maven Central](https://maven-badges.herokuapp.com/maven-central/se.warting.firebase-compose/auth/badge.png)](https://maven-badges.herokuapp.com/maven-central/se.warting.firebase-compose/auth)

# Firebase Compose

A set of Compose components to make it easier to use firebase in the composable world

## Firebase Compose Auth

A jetpack compose module to deal with firebase authentication

## How to include in your project

The library is available via MavenCentral:

```
allprojects {
    repositories {
        // ...
        mavenCentral()
    }
}
```

Add it to your module dependencies:

```
dependencies {
    implementation("se.warting.firebase-compose:auth:<latest_version>")
}
```

<details>
<summary>Snapshots of the development version are available in Sonatype's snapshots repository.</summary>
<p>

[![Sonatype Nexus (Snapshots)](https://img.shields.io/nexus/s/se.warting.firebase-compose/auth?server=https%3A%2F%2Foss.sonatype.org)](https://oss.sonatype.org/content/repositories/snapshots/se/warting/firebase-compose/auth/)
[![Sonatype Nexus (Snapshots)](https://img.shields.io/nexus/s/se.warting.firebase-compose/core?server=https%3A%2F%2Foss.sonatype.org)](https://oss.sonatype.org/content/repositories/snapshots/se/warting/firebase-compose/core/)
[![Sonatype Nexus (Snapshots)](https://img.shields.io/nexus/s/se.warting.firebase-compose/dynamic-links?server=https%3A%2F%2Foss.sonatype.org)](https://oss.sonatype.org/content/repositories/snapshots/se/warting/firebase-compose/dynamic-links/)
[![Sonatype Nexus (Snapshots)](https://img.shields.io/nexus/s/se.warting.firebase-compose/messaging?server=https%3A%2F%2Foss.sonatype.org)](https://oss.sonatype.org/content/repositories/snapshots/se/warting/firebase-compose/messaging/)
[![Sonatype Nexus (Snapshots)](https://img.shields.io/nexus/s/se.warting.firebase-compose/messaging-firestore?server=https%3A%2F%2Foss.sonatype.org)](https://oss.sonatype.org/content/repositories/snapshots/se/warting/firebase-compose/messaging-firestore/)

```groovy
allprojects {
    repositories {
        // ...
        maven {
            url 'https://oss.sonatype.org/content/repositories/snapshots/'
        }
    }
}
```
</p>
</details>

## How to use

All you need to do is to call `FirebaseComposeAuth`:

```
    FirebaseComposeAuth(
        loggedInContent = { LoggedIn() },
        loggedOutContent = { LoggedOut() }
    )
```

For a full implementation
see: [Full sample](app/src/main/java/se/warting/firebasecompose/MainActivity.kt)

## Notes

You must install and initiate firebase in your app before using this
