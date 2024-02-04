package screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.russhwolf.settings.Settings
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import model.Device
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import screens.composables.DashboardItem
import screens.composables.LoadingIndicator
import service.DeviceService
import service.startupCommandListener
import service.startupDeviceEventListener

class DevicesScreen : Screen, KoinComponent {
    private var availableDevices = mutableStateListOf<Device>()
    private var isLoading = mutableStateOf(true)
    private val deviceService: DeviceService by inject()
    private val settings = Settings()
    private val groupName = settings.getString("groupName", "UNKNOWN")

    @Composable
    override fun Content() {
        MaterialTheme {}

        CoroutineScope(Dispatchers.IO).launch {
            deviceService.postCurrentDevice()
        }

        if (isLoading.value) {
            CoroutineScope(Dispatchers.IO).launch {
                val devices = deviceService.getAllDevicesByGroupId()
                availableDevices.clear()
                availableDevices.addAll(devices)
                isLoading.value = false
            }
            LoadingIndicator()
        } else {
            startupDeviceEventListener()
            startupCommandListener()
            Dashboard()
        }
    }

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
                var searchResults by remember { mutableStateOf(availableDevices) }

                TopAppBar(
                    title = { Text(groupName) },
                    actions = {
                        IconButton(onClick = { isLoading.value = true }) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "Refresh"
                            )
                        }
                        IconButton(onClick = { /* Handle settings click */ }) {
                            Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = "Settings"
                            )
                        }
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                BasicTextField(
                    value = textFieldText,
                    onValueChange = {
                        textFieldText = it
                        val filteredItems = filterListByNamesAndReturn(textFieldText)
                        searchResults = filteredItems.toMutableStateList()
                    },
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

                LazyColumn {
                    items(searchResults) { result ->
                        DashboardItem(item = result, isLoading = isLoading)
                    }
                }
            }
        }
    }

    private fun filterListByNamesAndReturn(query: String): List<Device> {
        return availableDevices.filter { it.name.contains(query, ignoreCase = true) }
    }
}
