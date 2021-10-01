package se.warting.firebasecompose

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

private fun noLocalProvidedFor(name: String): Nothing {
    error("CompositionLocal $name not present")
}
