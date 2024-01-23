import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import com.russhwolf.settings.Settings
import screens.LandingScreen
import service.generateDeviceId


@Composable
fun App() {
    initKoin()
    initializeDeviceId()

    MaterialTheme {
        Navigator(LandingScreen())
    }
}

fun initializeDeviceId() {
    val settings = Settings()

    settings.putString("deviceId", generateDeviceId())
}
