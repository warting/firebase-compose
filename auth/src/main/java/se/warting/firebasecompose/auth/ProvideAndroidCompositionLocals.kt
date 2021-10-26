package se.warting.firebasecompose.auth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

val LocalFirebaseAuthState = staticCompositionLocalOf<FirebaseAuthState> {
    error("CompositionLocal FirebaseAuth not present")
}

val LocalFirebaseAuth = staticCompositionLocalOf {
    Firebase.auth // Default instance of auth
}

@Composable
fun ProvideFirebaseComposeAuthLocals(
    eventListener: (AuthEvents) -> Unit = {},
    content: @Composable () -> Unit
) {
    val state: FirebaseAuthState = rememberFirebaseAuthState(eventListener)
    CompositionLocalProvider(
        LocalFirebaseAuthState provides state,
        content = content
    )
}
