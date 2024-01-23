package service

import model.enumeration.DeviceType

expect fun lockThisDevice(deviceType: DeviceType)

expect fun generateDeviceId(): String
