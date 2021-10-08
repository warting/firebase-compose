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
