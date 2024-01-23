package service

import model.enumeration.DeviceType
import oshi.SystemInfo

actual fun lockThisDevice(deviceType: DeviceType) {
    when (deviceType) {
        DeviceType.WINDOWS -> TODO()
        DeviceType.MAC -> lockMacDevice()
        DeviceType.LINUX -> TODO()
        DeviceType.MOBILE_ANDROID -> TODO()
        DeviceType.MOBILE_IOS -> TODO()
        DeviceType.UNKNOWN -> TODO()
    }
}

private fun lockMacDevice() {
    val pb = ProcessBuilder(
        "/bin/bash",
        "-c",
        "osascript -e 'tell application \"Finder\" to sleep'"
    )
    pb.start()
}

actual fun generateDeviceId(): String {
    return SystemInfo().run {
        val vendor = operatingSystem.manufacturer
        val processorSerialNumber = hardware.computerSystem.serialNumber

        "$vendor\$$processorSerialNumber"
    }
}
