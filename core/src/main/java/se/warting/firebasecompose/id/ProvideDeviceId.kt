package se.warting.firebasecompose.id

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import se.warting.firebasecompose.LoadingState

@Composable
fun ProvideDeviceId(
    errorContent: @Composable (Exception) -> Unit = {},
    loadingContent: @Composable () -> Unit = {},
    content: @Composable () -> Unit,
) {
    val deviceIdState = rememberDeviceIdState()
    when (val deviceId = deviceIdState.deviceId) {
        is LoadingState.Failed -> errorContent(deviceId.error)
        is LoadingState.Loading -> loadingContent()
        is LoadingState.Success -> CompositionLocalProvider(
            LocalDeviceId providesDefault deviceId.data,
            content = content
        )
    }
}
