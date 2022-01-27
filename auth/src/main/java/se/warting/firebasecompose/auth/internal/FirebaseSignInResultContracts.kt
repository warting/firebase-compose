package se.warting.firebasecompose.auth.internal

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import androidx.annotation.CallSuper
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider

internal class FirebaseSignInResultContracts {

    class SignInWithGoogle :
        ActivityResultContract<String, AuthenticateResult>() {
        @CallSuper
        override fun createIntent(context: Context, input: String): Intent {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(input)
                .requestEmail()
                .build()

            val googleSignInClient = GoogleSignIn.getClient(context, gso)

            return googleSignInClient.signInIntent
        }

        override fun getSynchronousResult(
            context: Context,
            input: String,
        ): SynchronousResult<AuthenticateResult>? {
            return null
        }

        override fun parseResult(resultCode: Int, intent: Intent?): AuthenticateResult {
            return if (intent != null && resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(intent)
                val account = task.getResult(ApiException::class.java)
                val credential = GoogleAuthProvider.getCredential(account.idToken!!, null)
                AuthenticateResult.Success(credential)
            } else {
                AuthenticateResult.Failed(resultCode, intent)
            }
        }
    }
}
