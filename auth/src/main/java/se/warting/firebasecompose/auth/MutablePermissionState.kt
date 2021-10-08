package se.warting.firebasecompose.auth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

@Composable
internal fun rememberMutableFirebaseAuthState(): FirebaseAuthState {

    val firebaseAuth = LocalFirebaseAuth.current

    val firebaseAuthState = remember {
        MutableFirebaseAuthState(firebaseAuth)
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

internal class MutableFirebaseAuthState(
    private val firebaseAuth: FirebaseAuth
) : FirebaseAuthState {

    private var _isLoggedIn by mutableStateOf(firebaseAuth.currentUser != null)

    override val isLoggedIn: Boolean
        get() = _isLoggedIn

    override fun logout() {
        firebaseAuth.signOut()
    }

    override fun signInAnonymously() {
        firebaseAuth.signInAnonymously()
    }

    override fun addAuthStateListener(listener: FirebaseAuth.AuthStateListener) {
        firebaseAuth.addAuthStateListener(listener)
    }

    override fun removeAuthStateListener(listener: FirebaseAuth.AuthStateListener) {
        firebaseAuth.removeAuthStateListener(listener)
    }

    override fun updateLoggedInState() {
        _isLoggedIn = firebaseAuth.currentUser != null
    }

    override suspend fun getItToken(forceRefresh: Boolean): String? {
        return firebaseAuth.currentUser?.getIdToken(forceRefresh)?.await()?.token
    }

    override fun getUserId(): String? {
        return firebaseAuth.currentUser?.uid
    }
}
