package se.warting.firebasecompose.auth

import androidx.compose.runtime.Composable

@Composable
fun FirebaseComposeAuth(
    loggedInContent: @Composable () -> Unit,
    loggedOutContent: @Composable () -> Unit,
) {
    ProvideFirebaseComposeAuthLocals {
        val state = LocalFirebaseAuthState.current
        when {
            state.isLoggedIn -> loggedInContent()
            else -> loggedOutContent()
        }
    }
}
