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
