package screens.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.darkrockstudios.libraries.mpfilepicker.FilePicker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import service.FileTransferService

@Composable
fun CustomFilePicker(
    showFilePicker: Boolean,
    recipientDeviceIds: List<String>,
    onIsFilePickerShownChange: (Boolean) -> Unit
) {
    val fileTransferService = FileTransferService()
    var pathSingleChosen by remember { mutableStateOf("") }

    FilePicker(show = showFilePicker) { file ->
        onIsFilePickerShownChange(false)
        pathSingleChosen = file?.path ?: "none selected"

        CoroutineScope(Dispatchers.IO).launch {
            fileTransferService.uploadFileToBackend(pathSingleChosen, recipientDeviceIds)
        }
    }
}
