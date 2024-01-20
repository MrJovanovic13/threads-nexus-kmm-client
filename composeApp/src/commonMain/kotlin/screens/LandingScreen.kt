package screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import screens.composables.EditableFieldWithLabel

class LandingScreen() : Screen {

    @Composable
    override fun Content() {
        MaterialTheme {}
        TwoEditableFieldsWindow()
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun TwoEditableFieldsWindow() {

    val navigator = LocalNavigator.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource("drawable/logo.png"),
            contentDescription = null,
            modifier = Modifier
                .size(120.dp)
                .clip(shape = MaterialTheme.shapes.medium)
                .padding(8.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))
        EditableFieldWithLabel("Device name", "")

        Spacer(modifier = Modifier.height(16.dp))
        EditableFieldWithLabel("Group name", "")

        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = { navigator?.push(DevicesScreen(groupName = "JovanovicDevices")) }) {
            Text("Connect")
        }
    }
}
