package screens.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
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

@Composable
fun GlobalActionsBar(
    isAllChecked: Boolean,
    onAllCheckedChange: (Boolean) -> Unit,
    onFilePickerShownChange: (Boolean) -> Unit
) {

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
            IconButton(onClick = {
                onFilePickerShownChange(true)
            }) {
                // TODO Use different icon
                Icon(imageVector = Icons.Default.AddCircle, contentDescription = "Test")
            }

            IconButton(onClick = {
                CoroutineScope(Dispatchers.IO).launch {
//                    commandsService.postEvent(CommandType.LOCK_DEVICE, item)
                }
            }) {
                Icon(imageVector = Icons.Default.Lock, contentDescription = "LockDevice")
            }

            IconButton(onClick = {
                CoroutineScope(Dispatchers.IO).launch {
//                    deviceService.detachDeviceFromGroup(item)
//                    isLoading.value = true
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