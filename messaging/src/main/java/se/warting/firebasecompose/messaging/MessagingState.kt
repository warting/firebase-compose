package se.warting.firebasecompose.messaging

import androidx.compose.runtime.Composable
import se.warting.firebasecompose.LoadingState
import se.warting.firebasecompose.annotation.ExperimentalFirebaseComposeApi

@Composable
@ExperimentalFirebaseComposeApi
fun rememberMessagingState(): MessagingState {
    return rememberMutableDynamicLinksState()
}

@ExperimentalFirebaseComposeApi
interface MessagingState {
    val token: LoadingState<String>
}
