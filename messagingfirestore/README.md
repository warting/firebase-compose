[![Maven Central](https://maven-badges.herokuapp.com/maven-central/se.warting.firebase-compose/messaging-firestore/badge.png)](https://maven-badges.herokuapp.com/maven-central/se.warting.firebase-compose/messaging-firestore)

# Firebase Compose - Dynamic Links

A highly Experimental library that helps asking for firebase messaging token and device id and 
storing it with a timestamp to Firebase Firestore. This library assumes some special configuration
to allow writes and updates in Firestore.

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
    implementation("se.warting.firebase-compose:messaging-firestore:<latest_version>")
}
```

## How to use

All you need to do is to use `ProvideMessagingFirestoreToken`:

```
ProvideMessagingFirestoreToken {
    Text(text = "Token: " + LocalFirebaseMessagingToken.current)
}
```

For a full implementation
see: [Full sample](../app/src/main/java/se/warting/firebasecompose/MessagingTokenStorageActivity.kt)

## Notes

You must install and initiate firebase in your app before using this
