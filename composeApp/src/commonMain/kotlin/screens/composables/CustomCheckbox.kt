package screens.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import model.Device

@Composable
fun CustomCheckbox(
    isCheckboxShown: Boolean,
    device: Device,
    onSingleCheckedChange: (Boolean, Device) -> Unit
) {
    Column {
        Checkbox(
            checked = isCheckboxShown,
            onCheckedChange = { onSingleCheckedChange(it, device) },
        )
    }
}

@Composable
fun CustomCheckboxWithText(
    text: String,
    isAllChecked: Boolean,
    onAllCheckedChange: (Boolean) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = isAllChecked,
            onCheckedChange = onAllCheckedChange
        )
        Text(text = text)
    }
}
