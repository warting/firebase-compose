package se.warting.firebasecompose.dynamiclinks

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.google.firebase.dynamiclinks.ktx.dynamicLinks
import com.google.firebase.ktx.Firebase

val LocalFirebaseDynamicLinks: ProvidableCompositionLocal<FirebaseDynamicLinks> =
    staticCompositionLocalOf {
        Firebase.dynamicLinks // default instance of dynamic links
    }
