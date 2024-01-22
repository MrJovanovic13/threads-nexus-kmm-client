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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import screens.composables.EditableFieldWithLabel
import service.startupCommandListener
import service.startupDeviceEventListener

class LandingScreen() : Screen {

    @Composable
    override fun Content() {
        MaterialTheme {}
        TwoEditableFieldsWindow()

        startupDeviceEventListener()
        startupCommandListener()
//        lockThisDevice(DeviceType.MAC)
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun TwoEditableFieldsWindow() {

    val navigator = LocalNavigator.currentOrThrow

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
        var deviceName by rememberSaveable { mutableStateOf("") }
        EditableFieldWithLabel("Device name", deviceName, onTextChange = { deviceName = it })

        Spacer(modifier = Modifier.height(16.dp))
        var groupName by rememberSaveable { mutableStateOf("") }
        EditableFieldWithLabel("Group name", groupName, onTextChange = { groupName = it })

        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = {
            navigator.push(
                DevicesScreen(
                    groupName = groupName,
                    deviceName = deviceName
                )
            )
        }) {
            Text("Connect")
        }
    }
}
