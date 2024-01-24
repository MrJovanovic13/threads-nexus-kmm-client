package service

import Constants
import com.russhwolf.settings.Settings
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import model.Device
import model.dto.CommandCreate
import model.enumeration.CommandType
import org.koin.core.component.KoinComponent

class CommandsService : KoinComponent {

    // This client is duplicated from the EventsService
    // Rewrite this to utilize the Singleton pattern
    private val client = HttpClient() {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
            })
        }
    }

    suspend fun postEvent(
        commandType: CommandType,
        device: Device
    ) {
        val settings = Settings()
        val deviceId = settings.getString("deviceId", "UNKNOWN")

        client.post(Constants.BACKEND_URL.plus("/devices/").plus(deviceId).plus("/publish-command")) {
            contentType(ContentType.Application.Json)
            setBody(
                CommandCreate(
                    commandType,
                    device
                )
            )
        }
    }
}
