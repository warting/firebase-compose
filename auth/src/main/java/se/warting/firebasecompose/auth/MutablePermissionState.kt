/*
 * MIT License
 *
 * Copyright (c) 2021 Stefan Wärting
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package se.warting.firebasecompose.auth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

@Composable
internal fun rememberMutableFirebaseAuthState(): FirebaseAuthState {

    val firebaseAuth = LocalFirebaseAuth.current

    val firebaseAuthState = remember {
        MutableFirebaseAuthState(firebaseAuth)
    }

    val authStateListener = remember {
        FirebaseAuth.AuthStateListener {
            firebaseAuthState.updateLoggedInState()
        }
    }

    DisposableEffect(firebaseAuthState) {
        firebaseAuthState.addAuthStateListener(authStateListener)
        onDispose {
            firebaseAuthState.removeAuthStateListener(authStateListener)
        }
    }

    return firebaseAuthState
}

internal class MutableFirebaseAuthState(
    private val firebaseAuth: FirebaseAuth
) : FirebaseAuthState {

    private var _isLoggedIn by mutableStateOf(firebaseAuth.currentUser != null)

    override val isLoggedIn: Boolean
        get() = _isLoggedIn

    override fun logout() {
        firebaseAuth.signOut()
    }

    override fun signInAnonymously() {
        firebaseAuth.signInAnonymously()
    }

    override fun addAuthStateListener(listener: FirebaseAuth.AuthStateListener) {
        firebaseAuth.addAuthStateListener(listener)
    }

    override fun removeAuthStateListener(listener: FirebaseAuth.AuthStateListener) {
        firebaseAuth.removeAuthStateListener(listener)
    }

    override fun updateLoggedInState() {
        _isLoggedIn = firebaseAuth.currentUser != null
    }

    override suspend fun getItToken(forceRefresh: Boolean): String? {
        return firebaseAuth.currentUser?.getIdToken(forceRefresh)?.await()?.token
    }

    override fun getUserId(): String? {
        return firebaseAuth.currentUser?.uid
    }
}
