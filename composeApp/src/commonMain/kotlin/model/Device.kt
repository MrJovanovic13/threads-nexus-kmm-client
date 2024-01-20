package model

import kotlinx.serialization.Serializable
import model.enumeration.DeviceStatus
import model.enumeration.DeviceType


@Serializable
data class Device(
    var id: String? = null,
    var name: String,
    var type: DeviceType,
    val status: DeviceStatus,
    var ip: String?
)
