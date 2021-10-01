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

## License

```
MIT License

Copyright (c) 2021 Stefan Wärting

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```