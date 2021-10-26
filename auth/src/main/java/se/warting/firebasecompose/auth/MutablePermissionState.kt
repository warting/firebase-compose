package se.warting.firebasecompose.auth

import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

private const val ANONYMOUS_PROVIDER = "anonymous"

@Composable
internal fun rememberMutableFirebaseAuthState(eventListener: (AuthEvents) -> Unit = {}): FirebaseAuthState {

    val firebaseAuth = LocalFirebaseAuth.current

    var mutableCredential by remember {
        mutableStateOf<AuthCredential?>(null)
    }

    val linkGoogleContract =
        rememberLauncherForActivityResult(
            FirebaseSignInResultContracts.SignInWithGoogle()
        ) { credentials ->
            eventListener(AuthEvents.GoogleAuthenticated)
            mutableCredential = credentials
        }

    val firebaseAuthState = remember {
        MutableFirebaseAuthState(firebaseAuth, linkGoogleContract, eventListener)
    }

    val authStateListener = remember {
        FirebaseAuth.AuthStateListener {
            firebaseAuthState.updateLoggedInState()
        }
    }

    LaunchedEffect(mutableCredential) {
        val credentials = mutableCredential
        if (credentials != null) {
            val fine = firebaseAuthState.signInWithCredential(credentials)
            if (fine.credential != null) {
                eventListener(AuthEvents.FirebaseSignedIn(credentials.provider))
            }
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
    private val firebaseAuth: FirebaseAuth,
    private val linkGoogleContract: ManagedActivityResultLauncher<String, AuthCredential?>,
    private val eventListener: (AuthEvents) -> Unit
) : FirebaseAuthState {

    private var _isLoggedIn by mutableStateOf(firebaseAuth.currentUser != null)

    override val isLoggedIn: Boolean
        get() = _isLoggedIn

    override fun logout() {
        firebaseAuth.signOut()
        eventListener(AuthEvents.FirebaseSignedOut)
    }

    override fun signInWithGoogle(requestIdToken: String) {
        linkGoogleContract.launch(requestIdToken)
    }

    override fun signInAnonymously() {
        firebaseAuth.signInAnonymously()
        eventListener(AuthEvents.FirebaseSignedIn(ANONYMOUS_PROVIDER))
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

    suspend fun signInWithCredential(credentials: AuthCredential): AuthResult {
        return firebaseAuth.signInWithCredential(credentials).await()
    }
}
