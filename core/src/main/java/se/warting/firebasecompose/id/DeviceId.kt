package se.warting.firebasecompose.id

import androidx.compose.runtime.Composable
import kotlinx.coroutines.flow.Flow
import se.warting.firebasecompose.LoadingState

@Composable
fun rememberDeviceIdState(): DeviceIdState {

    return rememberMutableDeviceId()
}

interface DeviceIdState {

    val deviceId: LoadingState<String>

    val deviceIdInPrefs: Flow<LoadingState<String>>
}
