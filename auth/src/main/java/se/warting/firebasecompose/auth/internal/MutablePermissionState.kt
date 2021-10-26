package se.warting.firebasecompose.auth.internal

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
import se.warting.firebasecompose.auth.AuthEvents
import se.warting.firebasecompose.auth.FirebaseAuthState
import se.warting.firebasecompose.auth.LocalFirebaseAuth

private const val ANONYMOUS_PROVIDER = "anonymous"

@Composable
internal fun rememberMutableFirebaseAuthState(eventListener: (AuthEvents) -> Unit = {}): FirebaseAuthState {

    val firebaseAuth = LocalFirebaseAuth.current

    var mutableCredential by remember {
        mutableStateOf<AuthenticateResult>(AuthenticateResult.None)
    }

    val linkGoogleContract =
        rememberLauncherForActivityResult(
            FirebaseSignInResultContracts.SignInWithGoogle()
        ) { credentials ->
            eventListener(AuthEvents.GoogleAuthenticated)
            mutableCredential = credentials
        }

    val firebaseAuthState = remember {
        MutableFirebaseAuthState(
            firebaseAuth = firebaseAuth,
            launchGoogleSignIn = linkGoogleContract::launch,
            eventListener = eventListener
        )
    }

    val authStateListener = remember {
        FirebaseAuth.AuthStateListener {
            firebaseAuthState.updateLoggedInState()
        }
    }

    LaunchedEffect(mutableCredential) {
        val result = mutableCredential
        if (result is AuthenticateResult.Success) {
            val fine = firebaseAuthState.signInWithCredential(result.credential)
            if (fine.credential != null) {
                eventListener(AuthEvents.FirebaseSignedIn(result.credential.provider))
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
    private val launchGoogleSignIn: (requestIdToken: String) -> Unit,
    private val eventListener: (AuthEvents) -> Unit
) : FirebaseAuthState {

    private var _isLoggedIn by mutableStateOf(firebaseAuth.currentUser != null)

    override val isLoggedIn: Boolean
        get() = _isLoggedIn

    override fun logout() {
        firebaseAuth.signOut()
        eventListener(AuthEvents.FirebaseSignedOut)
    }

    override fun signInWithGoogle(requestIdToken: String) = launchGoogleSignIn(requestIdToken)

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
