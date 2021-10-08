package se.warting.firebasecompose

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.annotation.RestrictTo
import androidx.annotation.RestrictTo.Scope.LIBRARY_GROUP
import se.warting.firebasecompose.annotation.InternalFirebaseComposeApi

/**
 * Find the closest Activity in a given Context.
 */
@RestrictTo(LIBRARY_GROUP)
@InternalFirebaseComposeApi
fun Context.findActivity(): Activity {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    throw IllegalStateException("Permissions should be called in the context of an Activity")
}
