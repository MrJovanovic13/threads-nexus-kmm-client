package service

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import model.Command
import model.enumeration.CommandType
import model.enumeration.DeviceEventName
import model.enumeration.Severity

fun executeCommand(command: Command) {
    val eventsService = EventsService()
    val fileTransferService = FileTransferService()

    when (command.commandType) {
        CommandType.LOCK_DEVICE -> {
            lockThisDevice(command.device.type)
            CoroutineScope(Dispatchers.IO).launch {
                eventsService.postEvent(
                    DeviceEventName.DEVICE_LOCKED.name,
                    null,
                    Severity.MEDIUM
                )
            }
        }

        CommandType.HEARTBEAT -> Unit
        CommandType.DOWNLOAD_PENDING_FILE_FROM_CONTEXT -> {
            CoroutineScope(Dispatchers.IO).launch {
                println("Executing COMMAND")
                fileTransferService.downloadFileFromBackend()
            }
        }

        else -> {}
    }
}
