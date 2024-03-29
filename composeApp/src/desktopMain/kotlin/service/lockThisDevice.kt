package service

import model.enumeration.DeviceType
import oshi.SystemInfo


actual fun lockThisDevice(deviceType: DeviceType) {
    when (deviceType) {
        DeviceType.WINDOWS -> lockWindowsDevice()
        DeviceType.MAC -> lockMacDevice()
        DeviceType.LINUX -> lockLinuxDevice()
        DeviceType.MOBILE_ANDROID -> TODO()
        DeviceType.MOBILE_IOS -> TODO()
        DeviceType.UNKNOWN -> TODO()
    }
}

private fun lockWindowsDevice() {
    Runtime.getRuntime().exec("C:/WINDOWS/System32/rundll32.exe user32.dll,LockWorkStation")
}

// TODO Test this on linux devices
private fun lockLinuxDevice() {
    val pb = ProcessBuilder(
        "/bin/bash",
        "-c",
        "xdg-screensaver lock"
    )
    pb.start()
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
