package service

import model.Command
import model.enumeration.CommandType

fun executeCommand(command: Command) {
    println("Execute attempt")
    when (command.commandType) {
        CommandType.LOCK_DEVICE -> lockThisDevice(command.device.type)
        CommandType.HEARTBEAT -> Unit
    }
}
