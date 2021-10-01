package se.warting.firebasecompose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf

val LocalFirebaseAuthState = staticCompositionLocalOf<FirebaseAuthState> {
    noLocalProvidedFor("FirebaseAuth")
}

@Composable
fun ProvideFirebaseComposeAuthLocals(
    content: @Composable () -> Unit
) {
    val state: FirebaseAuthState = rememberFirebaseAuthState()
    CompositionLocalProvider(
        LocalFirebaseAuthState provides state,
        content = content
    )
}

private fun noLocalProvidedFor(name: String): Nothing {
    error("CompositionLocal $name not present")
}
