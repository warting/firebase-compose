package se.warting.firebasecompose.messaging

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import se.warting.firebasecompose.LoadingState
import se.warting.firebasecompose.annotation.ExperimentalFirebaseComposeApi

@ExperimentalFirebaseComposeApi
@Composable
fun ProvideMessagingToken(
    errorContent: @Composable (Exception) -> Unit = {},
    loadingContent: @Composable () -> Unit = {},
    content: @Composable () -> Unit,
) {
    val deviceIdState = rememberMessagingState()
    when (val deviceId = deviceIdState.token) {
        is LoadingState.Failed -> errorContent(deviceId.error)
        is LoadingState.Loading -> loadingContent()
        is LoadingState.Success -> CompositionLocalProvider(
            LocalFirebaseMessagingToken providesDefault deviceId.data,
            content = content
        )
    }
}
