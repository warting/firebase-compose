[![Maven Central](https://maven-badges.herokuapp.com/maven-central/se.warting.firebase-compose/core/badge.png)](https://maven-badges.herokuapp.com/maven-central/se.warting.firebase-compose/core)

# Firebase Compose - Dynamic Links

A common core library used by all firebase compose modules

## How to include in your project

You should use any of the other libraries, but if you for some reason need something only 
from this library it is is available via MavenCentral:

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
    implementation("se.warting.firebase-compose:core:<latest_version>")
}
```

<details>
<summary>Snapshots of the development version are available in Sonatype's snapshots repository.</summary>
<p>

[![Sonatype Nexus (Snapshots)](https://img.shields.io/nexus/s/se.warting.firebase-compose/core?server=https%3A%2F%2Foss.sonatype.org)](https://oss.sonatype.org/content/repositories/snapshots/se/warting/firebase-compose/core/)

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

## Whats included

* Annotations
* DeviceId
* LoadingState
* And Other internal utils
