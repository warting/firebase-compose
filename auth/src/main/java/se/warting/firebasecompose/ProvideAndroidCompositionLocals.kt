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
