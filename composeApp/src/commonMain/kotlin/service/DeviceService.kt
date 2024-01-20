package service

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import model.Device
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation as ClientContentNegotiation

class DeviceService {

    companion object {
        const val baseUrl = "https://7722-2a06-5b05-8c04-2f00-f997-a316-de91-6a44.ngrok-free.app/api/devices"
    }

    private val client = HttpClient() {
        install(ClientContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
            })
        }
    }

    suspend fun getAllDevices(): List<Device> {
        return client.get(baseUrl).body()
    }
}
