package screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import service.DeviceService

class LandingScreen() : Screen {

    @Composable
    override fun Content() {
        MaterialTheme {
            TwoEditableFieldsWindow()
        }
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

@Composable
fun EditableFieldWithLabel(label: String, text: String) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        var textFieldText by remember { mutableStateOf(text) }

        Spacer(modifier = Modifier.height(4.dp))

        TextField(
            value = textFieldText,
            onValueChange = { textFieldText = it },
            label = { Text(label) },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Text,
                capitalization = KeyboardCapitalization.Words
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

