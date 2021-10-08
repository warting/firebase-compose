package se.warting.firebasecompose.dynamiclinks

import androidx.compose.runtime.Composable
import com.google.firebase.dynamiclinks.DynamicLink
import com.google.firebase.dynamiclinks.PendingDynamicLinkData

@Composable
fun rememberDynamicLinkState(getPendingDynamicLinkDataOnStart: Boolean = false): DynamicLinksState {
    return rememberMutableDynamicLinksState(getPendingDynamicLinkDataOnStart)
}

interface DynamicLinksState {

    val pendingDynamicLinkData: PendingDynamicLinkData?

    suspend fun getDynamicLink(): PendingDynamicLinkData?
    suspend fun createDynamicLink(): DynamicLink.Builder
}
