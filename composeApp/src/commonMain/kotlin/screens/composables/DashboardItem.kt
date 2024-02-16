package screens.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import model.Device
import model.Option
import model.enumeration.CommandType
import model.enumeration.DeviceStatus
import screens.ControllsScreen
import service.CommandsService
import service.DeviceService

@Composable
fun DashboardItem(item: Device, isLoading: MutableState<Boolean>) {
    // TODO Troubleshoot and use KOIN here
    val commandsService = CommandsService()
    val deviceService = DeviceService()
    val navigator = LocalNavigator.currentOrThrow

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .clickable {
                    navigator.push(ControllsScreen(item,options = listOf(
                        Option("Option 1"),
                        Option("Option 2"),
                        Option("Option 3")
                    )))
                }
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
                IconButton(onClick = {
                    CoroutineScope(Dispatchers.IO).launch {
                        commandsService.postEvent(CommandType.LOCK_DEVICE, item)
                    }
                }) {
                    Icon(imageVector = Icons.Default.Lock, contentDescription = "LockDevice")
                }

                IconButton(onClick = {
                    CoroutineScope(Dispatchers.IO).launch {
                        deviceService.detachDeviceFromGroup(item)
                        isLoading.value = true
                    }
                }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "DeleteDevice"
                    )
                }
                Icon(
                    // TODO There is code duplication here
                    imageVector = when (item.status) {
                        DeviceStatus.ONLINE -> Icons.Default.CheckCircle
                        // TODO Consider different icon for offline client
                        DeviceStatus.OFFLINE -> Icons.Default.Close
                    },
                    contentDescription = "Status",
                    tint = when (item.status) {
                        DeviceStatus.ONLINE -> Color.Green
                        DeviceStatus.OFFLINE -> Color.Red
                    }
                )
            }
        }
    }
}
