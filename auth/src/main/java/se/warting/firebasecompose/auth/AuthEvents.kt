package se.warting.firebasecompose.auth

sealed class AuthEvents {
    data class FirebaseSignedIn(val provider: String) : AuthEvents()
    object FirebaseSignedOut : AuthEvents()
    object GoogleAuthenticated : AuthEvents()
}
