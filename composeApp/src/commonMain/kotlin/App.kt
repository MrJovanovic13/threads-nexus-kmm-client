import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import screens.LandingScreen


@Composable
fun App() {
    MaterialTheme {
        Navigator(LandingScreen())
    }
}
