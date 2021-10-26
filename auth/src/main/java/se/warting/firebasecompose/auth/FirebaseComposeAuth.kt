package se.warting.firebasecompose.auth

import androidx.compose.runtime.Composable

@Composable
fun FirebaseComposeAuth(
    eventListener: (AuthEvents) -> Unit = {},
    loggedInContent: @Composable () -> Unit,
    loggedOutContent: @Composable () -> Unit,
) {
    ProvideFirebaseComposeAuthLocals(eventListener) {
        val state = LocalFirebaseAuthState.current
        when {
            state.isLoggedIn -> loggedInContent()
            else -> loggedOutContent()
        }
    }
}
