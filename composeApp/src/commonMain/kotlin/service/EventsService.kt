package service

import model.enumeration.Severity
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import model.dto.DeviceEventCreate
import java.time.Instant

class EventsService {

    private val client = HttpClient() {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
            })
        }
    }

    suspend fun  postEvent(
        title: String,
        info: String?,
        severity: Severity,
        groupName: String,
        deviceName: String
    ) {
        val deviceService = DeviceService()

        client.post(Constants.BACKEND_URL.plus("/events")) {
            contentType(ContentType.Application.Json)
            setBody(
                DeviceEventCreate(
                    title,
                    info,
                    Instant.now(),
                    severity,
                    deviceService.getCurrentDevice(deviceName, groupName)
                )
            )
        }
    }
}
