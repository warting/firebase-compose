package se.warting.firebasecompose

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import kotlinx.coroutines.launch
import se.warting.firebasecompose.auth.AuthEvents
import se.warting.firebasecompose.auth.FirebaseComposeAuth
import se.warting.firebasecompose.auth.LocalFirebaseAuth
import se.warting.firebasecompose.auth.LocalFirebaseAuthState
import se.warting.firebasecompose.auth.ProvideFirebaseComposeAuthLocals
import se.warting.firebasecompose.auth.rememberFirebaseAuthState

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface {
                    HighLevel {
                        when (it) {
                            is AuthEvents.FirebaseSignedIn -> Log.d(
                                "AuthEvents",
                                "FirebaseSignedIn provider: " + it.provider
                            )
                            AuthEvents.FirebaseSignedOut -> Log.d(
                                "AuthEvents",
                                "FirebaseSignedOut"
                            )
                            AuthEvents.GoogleAuthenticated -> Log.d(
                                "AuthEvents",
                                "GoogleAuthenticated"
                            )
                        }
                    }
                }
            }
        }
    }
}

@Suppress("unused")
@Composable
fun LowLevel(eventListener: (AuthEvents) -> Unit = {}) {
    val state = rememberFirebaseAuthState(eventListener)

    CompositionLocalProvider(
        LocalFirebaseAuthState provides state,
        content = {
            when {
                state.isLoggedIn -> {
                    LoggedIn()
                }
                else -> {
                    LoggedOut()
                }
            }
        }
    )
}

@Composable
fun MidLevel(eventListener: (AuthEvents) -> Unit = {}) {
    ProvideFirebaseComposeAuthLocals(eventListener) {
        // LocalFirebaseAuthState.current is now available
        val state = LocalFirebaseAuthState.current
        when {
            state.isLoggedIn -> LoggedIn()
            else -> LoggedOut()
        }
    }
}

@Composable
fun HighLevel(eventListener: (AuthEvents) -> Unit = {}) {
    // LocalFirebaseAuthState.current is now available
    FirebaseComposeAuth(
        eventListener = eventListener,
        loggedInContent = { LoggedIn() },
        loggedOutContent = { LoggedOut() }
    )
}

@Composable
fun LoggedIn() {
    val firebaseAuthState = LocalFirebaseAuthState.current
    val firebaseAuth = LocalFirebaseAuth.current

    val scope = rememberCoroutineScope()
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (firebaseAuth.currentUser?.isAnonymous == true) {
            Text(text = "Hello anonymous!")
        } else {
            Text(text = "Hello " + firebaseAuth.currentUser?.displayName.toString() + "!")
        }
        Text(text = "UserId: " + firebaseAuthState.getUserId())
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { firebaseAuthState.logout() }) {
            Text(text = "Logout!")
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            scope.launch {
                try {
                    firebaseAuthState.getItToken(true)
                } catch (e: FirebaseAuthInvalidUserException) {
                    Log.e(
                        "FirebaseCompose",
                        "Could not refresh token. The user may have been deleted",
                        e
                    )
                }
            }
        }) {
            Text(text = "Force refresh token!")
        }
    }
}

@Composable
fun LoggedOut() {
    val firebaseAuthState = LocalFirebaseAuthState.current
    val clientId: String = stringResource(id = R.string.web_client_id)
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = "Hello logged out user!")
        Button(onClick = { firebaseAuthState.signInAnonymously() }) {
            Text(text = "Sign in Anonymously!")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            firebaseAuthState.signInWithGoogle(clientId)
        }) {
            Text(text = "Sign in with google!")
        }
    }
}
