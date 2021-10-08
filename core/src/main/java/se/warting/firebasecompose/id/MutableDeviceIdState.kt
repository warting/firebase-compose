package se.warting.firebasecompose.id

import android.content.Context
import android.content.res.Resources
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import java.util.UUID
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import se.warting.firebasecompose.LoadingState

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@Composable
internal fun rememberMutableDeviceId(): DeviceIdState {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val firebaseAuthState = remember {
        MutableDeviceIdState(scope, context)
    }
    return firebaseAuthState
}

internal class MutableDeviceIdState(scope: CoroutineScope, context: Context) : DeviceIdState {

    private var _deviceId by mutableStateOf<LoadingState<String>>(LoadingState.Loading())

    override val deviceIdInPrefs = context.dataStore.data
        .map { preferences ->
            val data: String? = preferences[deviceIdKey]
            if (data != null) {
                LoadingState.Success(data)
            } else {
                LoadingState.Failed(Resources.NotFoundException())
            }
        }

    init {
        scope.launch {
            deviceIdInPrefs.collect {
                when (it) {
                    is LoadingState.Failed -> {
                        if (it.error is Resources.NotFoundException) {
                            context.dataStore.edit { settings ->
                                settings[deviceIdKey] = UUID.randomUUID().toString()
                            }
                        }
                    }
                    is LoadingState.Loading -> {
                        _deviceId = it
                    }
                    is LoadingState.Success -> {
                        _deviceId = it
                    }
                }
            }
        }
    }

    override val deviceId: LoadingState<String>
        get() = _deviceId

    private val deviceIdKey = stringPreferencesKey("deviceId")
}
