package se.warting.firebasecompose.auth

import androidx.compose.runtime.Composable
import com.google.firebase.auth.FirebaseAuth

@Composable
fun rememberFirebaseAuthState(eventListener: (AuthEvents) -> Unit = {}): FirebaseAuthState {
    return rememberMutableFirebaseAuthState(eventListener)
}

interface FirebaseAuthState {

    val isLoggedIn: Boolean

    fun logout()

    fun signInWithGoogle(requestIdToken: String)

    fun signInAnonymously()

    fun addAuthStateListener(listener: FirebaseAuth.AuthStateListener)
    fun removeAuthStateListener(listener: FirebaseAuth.AuthStateListener)
    fun updateLoggedInState()

    suspend fun getItToken(forceRefresh: Boolean): String?
    fun getUserId(): String?
}
