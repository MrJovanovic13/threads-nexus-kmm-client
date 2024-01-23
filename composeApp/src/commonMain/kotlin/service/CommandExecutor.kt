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
    }
}
