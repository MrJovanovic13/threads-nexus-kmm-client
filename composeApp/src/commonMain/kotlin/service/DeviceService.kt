package service

import Constants
import com.russhwolf.settings.Settings
import getDeviceType
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import model.Device
import model.enumeration.DeviceStatus
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation as ClientContentNegotiation

class DeviceService {
    private val settings = Settings()
    private val groupName = settings.getString("groupName", "UNKNOWN")
    private val deviceName = settings.getString("deviceName", "UNKNOWN")
    private val deviceId = settings.getString("deviceId", "UNKNOWN")

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

    suspend fun detachDeviceFromGroup(device: Device) {
        client.post(Constants.BACKEND_URL.plus("/devices")) {
            contentType(ContentType.Application.Json)
            setBody(
                Device(device.id, device.name, device.type, device.status, device.ip, null)
            )
        }
    }

    suspend fun postCurrentDevice() {
        client.post(Constants.BACKEND_URL.plus("/devices")) {
            contentType(ContentType.Application.Json)
            setBody(
                Device(deviceId, deviceName, getDeviceType(), DeviceStatus.ONLINE, null, groupName)
            )
        }
    }

    private suspend fun getCurrentIp(): String {
        val response = client.get("https://checkip.amazonaws.com")
        return response.bodyAsText()
    }

    suspend fun getCurrentDevice(): Device {
        return Device(
            deviceId,
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

    suspend fun getAllDevicesByGroupIdAndSearchText(searchText: String): List<Device> {
        return client.get {
            url(baseUrl)
            parameter("groupId", groupName)
            parameter("searchText", searchText)
        }.body()
    }
}
