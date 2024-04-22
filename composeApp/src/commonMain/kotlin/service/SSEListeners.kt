package service

import Constants
import com.russhwolf.settings.Settings
import io.ktor.client.HttpClient
import io.ktor.client.plugins.sse.SSE
import io.ktor.client.plugins.sse.serverSentEventsSession
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import model.Command
import model.enumeration.CommandType

private val client = HttpClient() {
    install(SSE)
}

fun startupDeviceEventListener() {
    CoroutineScope(Dispatchers.IO).launch {
        client.serverSentEventsSession(Constants.BACKEND_URL.plus("/events/stream"))
            .incoming
            .onEach { println(it) } // TODO Transform and show events as notifications
            .collect()
    }
}

// TODO Implement robust reconnect mechanism
fun startupCommandListener() {
    val settings = Settings()
    val deviceId = settings.getString("deviceId", "UNKNOWN")

    CoroutineScope(Dispatchers.IO).launch {
        client.serverSentEventsSession(
            Constants.BACKEND_URL
                .plus("/devices/")
                .plus(deviceId)
                .plus("/commands")
        )
            .incoming
            .map { Json.decodeFromString<Command>(it.data!!) }
            .filter { it.commandType != CommandType.HEARTBEAT }
            .onEach { executeCommand(it)}
            .onEach { println(it) }
            .collect()
    }
}
