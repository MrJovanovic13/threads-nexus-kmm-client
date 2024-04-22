package screens.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import model.Device
import model.enumeration.CommandType
import service.CommandsService
import service.DeviceService

@Composable
fun GlobalActionsBar(
    isAllChecked: Boolean,
    onAllCheckedChange: (Boolean) -> Unit,
    onFilePickerShownChange: (Boolean) -> Unit,
    currentlyCheckedItemsList: List<Device>,
    isLoadingStateChange: (Boolean) -> Unit
) {

    val commandsService = CommandsService()
    val deviceService = DeviceService()

    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        CustomCheckboxWithText("Select all", isAllChecked, onAllCheckedChange)

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(Modifier.weight(1f))
            IconButton(onClick = {
                onFilePickerShownChange(true)
            }) {
                // TODO Use different icon
                Icon(imageVector = Icons.Default.AddCircle, contentDescription = "Test")
            }

            IconButton(onClick = {
                CoroutineScope(Dispatchers.IO).launch {
                    currentlyCheckedItemsList.forEach { device ->
                        commandsService.postEvent(CommandType.LOCK_DEVICE, device)
                    }
                }
            }) {
                Icon(imageVector = Icons.Default.Lock, contentDescription = "LockDevice")
            }

            IconButton(onClick = {
                CoroutineScope(Dispatchers.IO).launch {
                    currentlyCheckedItemsList.forEach { device ->
                        deviceService.detachDeviceFromGroup(device)
                    }
                    isLoadingStateChange(true)
                }
            }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "DeleteDevice"
                )
            }
        }
    }
}
