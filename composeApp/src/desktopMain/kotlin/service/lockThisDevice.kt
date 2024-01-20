package service

import model.Device
import model.enumeration.DeviceType

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
