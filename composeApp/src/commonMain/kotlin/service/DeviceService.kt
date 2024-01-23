package service

import Constants
import com.russhwolf.settings.Settings
import getDeviceType
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import io.ktor.client.statement.bodyAsText
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import model.Device
import model.enumeration.DeviceStatus
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation as ClientContentNegotiation

class DeviceService {
    private val settings = Settings()
    private val groupName = settings.getString("groupName", "UNKNOWN")
    private val deviceName = settings.getString("deviceName", "UNKNOWN")

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

    private suspend fun getCurrentIp(): String {
        val response = client.get("https://checkip.amazonaws.com")
        return response.bodyAsText()
    }

    suspend fun getCurrentDevice(): Device {
        return Device(
            Constants.CURRENT_DEVICE_ID,
            deviceName,
            getDeviceType(),
            DeviceStatus.ONLINE,
            getCurrentIp(),
            groupName
        )
    }

    suspend fun getAllDevicesByGroupId(): List<Device> {
        return client.get {
            url(baseUrl)
            parameter("groupId", groupName)
        }.body()
    }
}
