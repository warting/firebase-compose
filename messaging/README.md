[![Maven Central](https://maven-badges.herokuapp.com/maven-central/se.warting.firebase-compose/messaging/badge.png)](https://maven-badges.herokuapp.com/maven-central/se.warting.firebase-compose/messaging)

# Firebase Compose - Dynamic Links

A highly Experimental library that helps asking for firebase messaging token

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
    implementation("se.warting.firebase-compose:messaging:<latest_version>")
}
```

## How to use

All you need to do is to use `ProvideMessagingToken`:

```
ProvideMessagingToken {
    Text(text = "Token: " + LocalFirebaseMessagingToken.current)
}
```

For a full implementation
see: [Full sample](../app/src/main/java/se/warting/firebasecompose/MessagingTokenActivity.kt)

## Notes

You must install and initiate firebase in your app before using this
