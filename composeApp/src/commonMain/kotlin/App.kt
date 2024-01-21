import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import io.ktor.client.HttpClient
import io.ktor.client.plugins.sse.SSE
import io.ktor.client.plugins.sse.serverSentEventsSession
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import screens.LandingScreen


@OptIn(ExperimentalResourceApi::class)
@Composable
fun App() {
    MaterialTheme {
        Navigator(LandingScreen())

        startupEventListener()
    }
}

private fun startupEventListener() {
    CoroutineScope(Dispatchers.IO).launch {
        client.serverSentEventsSession(Constants.BACKEND_URL.plus("/events/stream"))
            .incoming
            .map { println(it) }
            .collect()
    }
}

private val client = HttpClient() {
    install(SSE)
}
