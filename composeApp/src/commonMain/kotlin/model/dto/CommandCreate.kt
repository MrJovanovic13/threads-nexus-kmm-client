package model.dto

import kotlinx.serialization.Serializable
import model.Device
import model.enumeration.CommandType

@Serializable
data class CommandCreate(
    var commandType: CommandType,
    var device: Device
)
