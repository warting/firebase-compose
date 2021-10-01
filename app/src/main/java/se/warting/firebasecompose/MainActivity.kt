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

package se.warting.firebasecompose

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface {
                    HighLevel()
                }
            }
        }
    }
}

@Suppress("unused")
@Composable
fun LowLevel() {
    val state = rememberFirebaseAuthState()

    CompositionLocalProvider(
        LocalFirebaseAuthState provides state,
        content = {
            when {
                state.isLoggedIn -> {
                    LoggedIn()
                }
                else -> {
                    LoggedOut()
                }
            }
        }
    )
}

@Composable
fun MidLevel() {
    ProvideFirebaseComposeAuthLocals {
        // LocalFirebaseAuthState.current is now available
        val state = LocalFirebaseAuthState.current
        when {
            state.isLoggedIn -> LoggedIn()
            else -> LoggedOut()
        }
    }
}

@Composable
fun HighLevel() {
    // LocalFirebaseAuthState.current is now available
    FirebaseComposeAuth(
        loggedInContent = { LoggedIn() },
        loggedOutContent = { LoggedOut() }
    )
}

@Composable
fun LoggedIn() {
    val f = LocalFirebaseAuthState.current

    val scope = rememberCoroutineScope()
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = "Hello anonymous!")
        Text(text = f.getUserId() ?: "")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { f.logout() }) {
            Text(text = "Logout!")
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            scope.launch {
                try {
                    f.getItToken(true)
                } catch (e: FirebaseAuthInvalidUserException) {
                    Log.e(
                        "FirebaseCompose",
                        "Could not refresh token. The user may have been deleted",
                        e
                    )
                }
            }
        }) {
            Text(text = "Force refresh token!")
        }
    }
}

@Composable
fun LoggedOut() {
    val f = LocalFirebaseAuthState.current
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = "Hello logged out user!")
        Button(onClick = { f.signInAnonymously() }) {
            Text(text = "Sign in Anonymously!")
        }
    }
}
