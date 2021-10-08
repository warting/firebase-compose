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

package se.warting.firebasecompose.messaging

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import se.warting.firebasecompose.LoadingState
import se.warting.firebasecompose.annotation.ExperimentalFirebaseComposeApi

@ExperimentalFirebaseComposeApi
@Composable
internal fun rememberMutableDynamicLinksState(): MessagingState {

    val messaging = LocalFirebaseMessaging.current
    val scope = rememberCoroutineScope()

    val firebaseAuthState = remember {
        MutableMessagingState(messaging)
    }

    DisposableEffect(firebaseAuthState) {
        val refreshTokenJob: Job = scope.launch {
            firebaseAuthState.updateToken()
        }
        onDispose {
            refreshTokenJob.cancel(
                "rememberMutableDynamicLinksState disposed"
            )
        }
    }

    return firebaseAuthState
}

@ExperimentalFirebaseComposeApi
internal class MutableMessagingState(
    private val firebaseDynamicLinks: FirebaseMessaging,
) : MessagingState {

    private var _token by mutableStateOf<LoadingState<String>>(LoadingState.Loading())

    override val token: LoadingState<String>
        get() = _token

    @Suppress("TooGenericExceptionCaught")
    internal suspend fun updateToken() {
        _token = try {
            val token = firebaseDynamicLinks.token.await()
            LoadingState.Success(token)
        } catch (e: Exception) {
            LoadingState.Failed(e)
        }
    }
}
