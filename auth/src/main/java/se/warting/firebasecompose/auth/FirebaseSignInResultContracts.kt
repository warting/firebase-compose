package se.warting.firebasecompose.auth

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import androidx.annotation.CallSuper
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.GoogleAuthProvider

class FirebaseSignInResultContracts {

    class SignInWithGoogle :
        ActivityResultContract<String, AuthCredential?>() {
        @CallSuper
        override fun createIntent(context: Context, requestIdToken: String): Intent {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(requestIdToken)
                .requestEmail()
                .build()

            val googleSignInClient = GoogleSignIn.getClient(context, gso)

            return googleSignInClient.signInIntent
        }

        override fun getSynchronousResult(
            context: Context,
            input: String,
        ): SynchronousResult<AuthCredential?>? {
            return null
        }

        override fun parseResult(resultCode: Int, intent: Intent?): AuthCredential? {
            if (intent != null || resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(intent)
                val account = task.getResult(ApiException::class.java)
                return GoogleAuthProvider.getCredential(account.idToken!!, null)
            }
            return null
        }
    }
}
