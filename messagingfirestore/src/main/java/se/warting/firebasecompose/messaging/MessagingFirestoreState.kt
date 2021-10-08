package se.warting.firebasecompose.messaging

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.SetOptions
import se.warting.firebasecompose.annotation.ExperimentalFirebaseComposeApi
import se.warting.firebasecompose.id.LocalDeviceId

@ExperimentalFirebaseComposeApi
@Composable
fun rememberMessagingFirestoreToken(): String {

    val firestore = LocalFirebaseFirestore.current
    val deviceId = LocalDeviceId.current
    val token = LocalFirebaseMessagingToken.current

    val documentData = hashMapOf(
        "tokenRefreshed" to FieldValue.serverTimestamp(),
        "messagingToken" to token,
        "deviceId" to deviceId
    )
    remember {
        firestore
            .collection("messagingTokens")
            .document(deviceId)
            .set(
                documentData, SetOptions.merge()
            )
    }
    return token
}
