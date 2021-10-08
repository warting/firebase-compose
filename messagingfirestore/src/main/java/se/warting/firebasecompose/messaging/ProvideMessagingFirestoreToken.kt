package se.warting.firebasecompose.messaging

import androidx.compose.runtime.Composable
import se.warting.firebasecompose.annotation.ExperimentalFirebaseComposeApi
import se.warting.firebasecompose.id.ProvideDeviceId

@ExperimentalFirebaseComposeApi
@Composable
fun ProvideMessagingFirestoreToken(
    errorContent: @Composable (Exception) -> Unit = {},
    loadingContent: @Composable () -> Unit = {},
    content: @Composable () -> Unit,
) {
    ProvideDeviceId(
        errorContent = errorContent,
        loadingContent = loadingContent,
        content = {
            ProvideMessagingToken {
                ProvideMessagingToken(
                    errorContent = errorContent,
                    loadingContent = loadingContent,
                    content = {
                        rememberMessagingFirestoreToken()
                        content()
                    }
                )
            }
        }
    )
}
