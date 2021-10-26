[![Maven Central](https://maven-badges.herokuapp.com/maven-central/se.warting.firebase-compose/dynamic-links/badge.png)](https://maven-badges.herokuapp.com/maven-central/se.warting.firebase-compose/dynamic-links)

# Firebase Compose - Dynamic Links

A way of interacting with firebase dynamic links in a jetpack compose friendly way

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
    implementation("se.warting.firebase-compose:dynamic-links:<latest_version>")
}
```

<details>
<summary>Snapshots of the development version are available in Sonatype's snapshots repository.</summary>
<p>

[![Sonatype Nexus (Snapshots)](https://img.shields.io/nexus/s/se.warting.firebase-compose/dynamic-links?server=https%3A%2F%2Foss.sonatype.org)](https://oss.sonatype.org/content/repositories/snapshots/se/warting/firebase-compose/dynamic-links/)

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
val dynamicLinkState = rememberDynamicLinkState(getPendingDynamicLinkDataOnStart = true)

Column {
    Text(text = "Getting the link...")
    Text(text = dynamicLinkState.pendingDynamicLinkData?.link.toString())
}
```

For a full implementation
see: [Full sample](../app/src/main/java/se/warting/firebasecompose/DynamicLinkActivity.kt)

## Notes

You must install and initiate firebase in your app before using this
