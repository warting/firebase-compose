package se.warting.firebasecompose.id

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf

val LocalDeviceId: ProvidableCompositionLocal<String> =
    staticCompositionLocalOf {
        error("device id not provided")
    }
