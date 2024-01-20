package screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import model.Device
import service.DeviceService

data class DevicesScreen( var groupName: String): Screen {
    var availableDevices = mutableStateOf<List<Device>>(emptyList())
    @Composable
    override fun Content() {
        MaterialTheme {
            LoadingScreen()

        }
    }

    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    fun Dashboard() {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                var textFieldText by remember { mutableStateOf("") }
                var searchResults by remember { mutableStateOf(availableDevices.value) }

                TopAppBar(
                    title = { Text(groupName) },
                    actions = {
                        IconButton(onClick = { /* Handle settings click */ }) {
                            Icon(imageVector = Icons.Default.Settings, contentDescription = "Settings")
                        }
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                BasicTextField(
                    value = textFieldText,
                    onValueChange = { textFieldText = it
                        searchResults = performSearch(textFieldText)},
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = {
                        }
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Gray)
                        .padding(8.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Dashboard Content
                LazyColumn {
                    items(searchResults) { result ->
                    DashboardItem(item = result)
                    }
                }
            }
        }
    }

    @Composable
    fun DashboardItem(item: Device) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            elevation = 4.dp
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                Text(
                    item.name,
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.weight(1f)
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { /* Handle settings click */ }) {
                        Icon(imageVector = Icons.Default.Lock, contentDescription = "LockDevice")
                    }

                    IconButton(onClick = { /* Handle settings click */ }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "DeleteDevice"
                        )
                    }
                    Icon(imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Status",
                        tint = Color.Green
                    )
                }

            }
        }
    }

    @Composable
    fun LoadingScreen() {
        var isLoading by remember { mutableStateOf(true) }

        val deviceService = DeviceService()
        var devices: List<Device>

        CoroutineScope(Dispatchers.IO).launch {
            devices = deviceService.getAllDevicesByGroupId("TEST")
            availableDevices.value = devices
            isLoading  = false
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            if (isLoading) {
                // Loading Indicator
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(50.dp)
                        .align(Alignment.Center),
                    color = Color.Black
                )
            } else {
                Dashboard()
            }
        }
    }
    fun performSearch(query: String): List<Device> {
        return availableDevices.value.filter { it.name.contains(query, ignoreCase = true)}

    }

}