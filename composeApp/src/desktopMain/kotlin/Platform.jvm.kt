import model.enumeration.DeviceType
import org.apache.commons.lang3.SystemUtils

actual fun getDeviceType(): DeviceType {
    return when {
        SystemUtils.IS_OS_WINDOWS -> DeviceType.WINDOWS
        SystemUtils.IS_OS_MAC -> DeviceType.MAC
        SystemUtils.IS_OS_LINUX -> DeviceType.LINUX
        else -> DeviceType.UNKNOWN
    }
}
