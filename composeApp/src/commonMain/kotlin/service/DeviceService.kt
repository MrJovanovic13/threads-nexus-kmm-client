package service

import Constants
import getDeviceType
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import io.ktor.client.statement.bodyAsText
import model.Device
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import model.enumeration.DeviceStatus
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

    private suspend fun getCurrentIp(): String {
        val response = client.get("https://checkip.amazonaws.com")
        return response.bodyAsText()
    }

    // TODO Consider saving deviceName and groupName in some kind of shared data so it's not
    // passed around this function chain
    suspend fun getCurrentDevice(deviceName: String, groupName: String): Device {
        return Device(
            Constants.CURRENT_DEVICE_ID,
            deviceName,
            getDeviceType(),
            DeviceStatus.ONLINE,
            getCurrentIp(),
            groupName
        )
    }

    suspend fun getAllDevicesByGroupId(groupId: String): List<Device> {
        return client.get {
            url(baseUrl)
            parameter("groupId", groupId)
        }.body()
    }
}
