package screens

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import org.koin.core.component.KoinComponent
import screens.composables.LandingScreenInputForm

class LandingScreen : Screen, KoinComponent {

    @Composable
    override fun Content() {
        MaterialTheme {}
        LandingScreenInputForm()
    }
}
