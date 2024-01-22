package service

import Constants
import io.ktor.client.HttpClient
import io.ktor.client.plugins.sse.SSE
import io.ktor.client.plugins.sse.serverSentEventsSession
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

private val client = HttpClient() {
    install(SSE)
}

fun startupDeviceEventListener() {
    CoroutineScope(Dispatchers.IO).launch {
        client.serverSentEventsSession(Constants.BACKEND_URL.plus("/events/stream"))
            .incoming
            .map { println(it) } // TODO Transform and show events as notifications
            .collect()
    }
}

// TODO Implement heartbeat on backend subscription so this doesn't timeout
// TODO Implement robust reconnect mechanism
fun startupCommandListener() {
    CoroutineScope(Dispatchers.IO).launch {
        client.serverSentEventsSession(
            Constants.BACKEND_URL
                .plus("/devices/")
                .plus(Constants.CURRENT_DEVICE_ID)
                .plus("/commands")
        )
            .incoming
            .map { println(it) } // TODO Transform and execute commands
            .collect()
    }
}
