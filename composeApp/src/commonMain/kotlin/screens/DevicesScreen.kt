package screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.russhwolf.settings.Settings
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import model.Device
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import screens.composables.CustomTopBar
import screens.composables.GlobalActionsBar
import screens.composables.DashboardItem
import screens.composables.LoadingIndicator
import screens.composables.SearchField
import service.DeviceService
import service.startupCommandListener
import service.startupDeviceEventListener

class DevicesScreen() : Screen, KoinComponent {
    private var availableDevices = mutableStateListOf<Device>()
    private var isLoading = mutableStateOf(true)
    private var isSearching = mutableStateOf(false)
    private var isCheckboxVisible = mutableStateOf(false)
    private val deviceService: DeviceService by inject()
    private val settings = Settings()
    private val groupName = settings.getString("groupName", "UNKNOWN")
    private val searchText = mutableStateOf("")

    @Composable
    override fun Content() {
        MaterialTheme {}

        if(isSearching.value) {
            CoroutineScope(Dispatchers.IO).launch {
                val devices = deviceService.getAllDevicesByGroupIdAndSearchText(searchText.value)
                availableDevices.clear()
                availableDevices.addAll(devices)
                isSearching.value = false
            }
            LoadingIndicator()
        }
        if (isLoading.value) {
            CoroutineScope(Dispatchers.IO).launch {
                deviceService.postCurrentDevice()
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
                Spacer(modifier = Modifier.height(16.dp))
                CustomTopBar(groupName, isLoading)
                Spacer(modifier = Modifier.height(16.dp))
                GlobalActionsBar()
                Spacer(modifier = Modifier.height(4.dp))
                SearchField(
                    searchText = searchText.value,
                    onSearchTextChange = ::onSearchTextChanged
                )

                LazyColumn {
                    items(availableDevices) { result ->
                        DashboardItem(item = result, isLoading = isLoading, isCheckboxVisible.value)
                    }
                }
            }
        }
    }

    // TODO Implement debounce mechanism
    private fun onSearchTextChanged(text: String) {
        searchText.value = text
        isSearching.value = true
    }
}
