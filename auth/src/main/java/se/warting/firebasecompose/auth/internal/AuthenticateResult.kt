package se.warting.firebasecompose.auth.internal

import android.content.Intent
import com.google.firebase.auth.AuthCredential

internal sealed class AuthenticateResult {
    data class Success(val credential: AuthCredential) : AuthenticateResult()
    data class Failed(val resultCode: Int, val intent: Intent?) : AuthenticateResult()
    object None : AuthenticateResult()
}
