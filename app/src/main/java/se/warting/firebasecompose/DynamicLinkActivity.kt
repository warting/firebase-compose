package se.warting.firebasecompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalUriHandler
import kotlin.system.exitProcess
import se.warting.firebasecompose.dynamiclinks.rememberDynamicLinkState

class DynamicLinkActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface {
                    DynamicLinkSample {
                        finishAffinity()
                        exitProcess(0)
                    }
                }
            }
        }
    }
}

@Composable
fun DynamicLinkSample(closeApp: () -> Unit) {

    val dynamicLinkState = rememberDynamicLinkState(getPendingDynamicLinkDataOnStart = true)

    val uriHandler = LocalUriHandler.current

    Column {
        Text(text = "Getting the link...")

        Text(text = dynamicLinkState.pendingDynamicLinkData?.link.toString())

        Button(onClick = {
            uriHandler.openUri(
                "https://firebasecompose.page.link/dynamiclinks?d=1"
            )
            closeApp()
        }) {
            Text(text = "open debug url")
        }
    }
}
