package service

import Constants
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import model.Device
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation as ClientContentNegotiation

class DeviceService {

    companion object {
        const val baseUrl = Constants.BACKEND_URL.plus("/devices")
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

    suspend fun getAllDevicesByGroupId(groupId: String): List<Device> {
        return client.get {
            url(baseUrl)
            parameter("groupId", groupId)
        }.body()
    }
}
