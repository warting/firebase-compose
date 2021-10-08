package se.warting.firebasecompose.messaging

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import se.warting.firebasecompose.annotation.ExperimentalFirebaseComposeApi

@ExperimentalFirebaseComposeApi
val LocalFirebaseFirestore: ProvidableCompositionLocal<FirebaseFirestore> =
    staticCompositionLocalOf {
        Firebase.firestore // default instance of dynamic links
    }
