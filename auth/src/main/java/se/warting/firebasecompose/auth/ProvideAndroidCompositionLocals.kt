package se.warting.firebasecompose.auth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

val LocalFirebaseAuthState = staticCompositionLocalOf<FirebaseAuthState> {
    noLocalProvidedFor("FirebaseAuth")
}

val LocalFirebaseAuth = staticCompositionLocalOf {
    Firebase.auth // Default instance of auth
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
