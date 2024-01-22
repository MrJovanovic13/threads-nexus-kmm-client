package model

import model.enumeration.CommandType
import kotlinx.serialization.Serializable

@Serializable
data class Command(
    var id: String? = null,
    var commandType: CommandType,
    var device: Device
)
