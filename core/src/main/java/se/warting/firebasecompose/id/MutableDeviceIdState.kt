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
