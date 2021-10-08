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

package se.warting.firebasecompose.dynamiclinks

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.tasks.Task
import com.google.firebase.dynamiclinks.DynamicLink
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.google.firebase.dynamiclinks.PendingDynamicLinkData
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import se.warting.firebasecompose.annotation.InternalFirebaseComposeApi
import se.warting.firebasecompose.findActivity

@Composable
internal fun rememberMutableDynamicLinksState(
    getPendingDynamicLinkDataOnStart: Boolean
): DynamicLinksState {

    val firebaseAuth = LocalFirebaseDynamicLinks.current
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val firebaseAuthState = remember {
        MutableDynamicLinksState(context, firebaseAuth)
    }
    if (getPendingDynamicLinkDataOnStart) {
        DisposableEffect(firebaseAuthState) {
            val refreshDynamicLink: Job = scope.launch {

                firebaseAuthState.updateDynamicLink()
            }
            onDispose {
                refreshDynamicLink.cancel(
                    "rememberMutableDynamicLinksState disposed"
                )
            }
        }
    }

    return firebaseAuthState
}

internal class MutableDynamicLinksState(
    private val context: Context,
    private val firebaseDynamicLinks: FirebaseDynamicLinks,
) : DynamicLinksState {

    private var _pendingDynamicLinkData by mutableStateOf<PendingDynamicLinkData?>(null)

    override val pendingDynamicLinkData: PendingDynamicLinkData?
        get() = _pendingDynamicLinkData

    @OptIn(InternalFirebaseComposeApi::class)
    private fun getDynamicLinkTask(): Task<PendingDynamicLinkData> {
        return firebaseDynamicLinks.getDynamicLink(context.findActivity().intent)
    }

    internal suspend fun updateDynamicLink() {
        _pendingDynamicLinkData = getDynamicLink()
    }

    // This could be null! https://github.com/firebase/firebase-android-sdk/pull/2629
    @Suppress("RedundantNullableReturnType")
    override suspend fun getDynamicLink(): PendingDynamicLinkData? {
        // This could be null! https://github.com/firebase/firebase-android-sdk/pull/2629
        return getDynamicLinkTask().await()
    }

    override suspend fun createDynamicLink(): DynamicLink.Builder {
        return firebaseDynamicLinks.createDynamicLink()
    }
}
