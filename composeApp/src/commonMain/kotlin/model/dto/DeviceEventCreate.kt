package model.dto

import model.enumeration.Severity
import kotlinx.serialization.Serializable
import model.Device
import model.serializer.InstantSerializer
import java.time.Instant

@Serializable
data class DeviceEventCreate(
    val title: String,
    val info: String?,
    @Serializable(InstantSerializer::class) val timestamp: Instant,
    val severity: Severity?,
    val device: Device
)
