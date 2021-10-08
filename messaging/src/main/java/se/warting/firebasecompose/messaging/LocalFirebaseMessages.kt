package se.warting.firebasecompose.messaging

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.ktx.messaging
import se.warting.firebasecompose.annotation.ExperimentalFirebaseComposeApi

@ExperimentalFirebaseComposeApi
val LocalFirebaseMessaging: ProvidableCompositionLocal<FirebaseMessaging> =
    staticCompositionLocalOf {
        Firebase.messaging // default instance of dynamic links
    }

@ExperimentalFirebaseComposeApi
val LocalFirebaseMessagingToken: ProvidableCompositionLocal<String> =
    staticCompositionLocalOf {
        error("Token not provide")
    }
