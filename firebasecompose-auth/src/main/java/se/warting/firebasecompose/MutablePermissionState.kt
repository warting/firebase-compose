package se.warting.firebasecompose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await


@Composable
internal fun rememberMutableFirebaseAuthState(): FirebaseAuthState {

    val firebaseAuthState = remember {
        MutableFirebaseAuthState()
    }

    val authStateListener = remember {
        FirebaseAuth.AuthStateListener {
            firebaseAuthState.updateLoggedInState()
        }
    }

    DisposableEffect(firebaseAuthState) {
        firebaseAuthState.addAuthStateListener(authStateListener)
        onDispose {
            firebaseAuthState.removeAuthStateListener(authStateListener)
        }
    }

    return firebaseAuthState
}


internal class MutableFirebaseAuthState : FirebaseAuthState {

    private val f = Firebase.auth

    private var _isLoggedIn by mutableStateOf(f.currentUser != null)

    override val isLoggedIn: Boolean
        get() = _isLoggedIn

    override fun logout() {
        f.signOut()
    }

    override fun signInAnonymously() {
        f.signInAnonymously()
    }

    override fun addAuthStateListener(listener: FirebaseAuth.AuthStateListener) {
        f.addAuthStateListener(listener)
    }

    override fun removeAuthStateListener(listener: FirebaseAuth.AuthStateListener) {
        f.removeAuthStateListener(listener)
    }

    override fun updateLoggedInState() {
        _isLoggedIn = f.currentUser != null
    }

    override suspend fun getItToken(forceRefresh: Boolean): String? {
        return f.currentUser?.getIdToken(forceRefresh)?.await()?.token
    }

    override fun getUserId(): String? {
        return f.currentUser?.uid
    }

}
