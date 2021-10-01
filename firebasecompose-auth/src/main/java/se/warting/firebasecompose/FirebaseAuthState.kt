package se.warting.firebasecompose

import androidx.compose.runtime.Composable
import com.google.firebase.auth.FirebaseAuth


@Composable
fun rememberFirebaseAuthState(): FirebaseAuthState {
    return rememberMutableFirebaseAuthState()
}

interface FirebaseAuthState {

    val isLoggedIn: Boolean

    fun logout()

    fun signInAnonymously()

    fun addAuthStateListener(listener: FirebaseAuth.AuthStateListener)
    fun removeAuthStateListener(listener: FirebaseAuth.AuthStateListener)
    fun updateLoggedInState()

    suspend fun getItToken(forceRefresh: Boolean): String?
    fun getUserId(): String?
}
