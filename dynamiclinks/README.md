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