package se.warting.firebasecompose.dynamiclinks

import android.content.Context
import android.util.Log
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
                try {
                    firebaseAuthState.updateDynamicLink()
                } catch (e: com.google.android.gms.common.api.ApiException) {
                    Log.e("ApiException", e.localizedMessage, e)
                }
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
