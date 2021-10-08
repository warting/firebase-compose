package se.warting.firebasecompose.annotation

/**
 * Marks declarations that are part of Firebae-compose's internal API.
 * They should not be used outside of the `firebasecompose` package
 * as their signatures and semantics will change between future releases
 * without any warnings and without providing
 * any migration aids.
 */
@MustBeDocumented
@Retention(value = AnnotationRetention.BINARY)
@RequiresOptIn(
    level = RequiresOptIn.Level.ERROR,
    message = "This is an internal FirebaseCompose API that should not be " +
            "used from outside of the `firebasecompose` package. " +
            "No compatibility guarantees are provided."
)
annotation class InternalFirebaseComposeApi
