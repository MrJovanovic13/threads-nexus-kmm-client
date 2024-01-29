package screens.composables

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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import model.Device
import model.enumeration.CommandType
import service.CommandsService
import service.DeviceService

@Composable
fun DashboardItem(item: Device, devices: SnapshotStateList<Device>) {
    // TODO Troubleshoot and use KOIN here
    val commandsService = CommandsService()
    val deviceService = DeviceService()

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
                        devices.remove(item)
                    }
                }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "DeleteDevice"
                    )
                }
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Status",
                    tint = Color.Green
                )
            }
        }
    }
}
