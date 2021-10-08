package se.warting.firebasecompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import se.warting.firebasecompose.annotation.ExperimentalFirebaseComposeApi
import se.warting.firebasecompose.messaging.LocalFirebaseMessagingToken
import se.warting.firebasecompose.messaging.ProvideMessagingToken

@ExperimentalFirebaseComposeApi
class MessagingTokenActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface {
                    MessagingSample()
                }
            }
        }
    }
}

@ExperimentalFirebaseComposeApi
@Composable
fun MessagingSample() {
    ProvideMessagingToken {
        Text(text = "Token: " + LocalFirebaseMessagingToken.current)
    }
}
