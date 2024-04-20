package screens.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import model.Device

@Composable
fun SearchField(
    availableDevices: List<Device>
) {
    var searchFieldText by remember { mutableStateOf("") }

    BasicTextField(
        value = searchFieldText,
        onValueChange = {
            searchFieldText = it
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
}

fun filterListByNamesAndReturn(query: String, availableDevices: List<Device>): List<Device> {
    return availableDevices.filter { it.name.contains(query, ignoreCase = true) }
}
