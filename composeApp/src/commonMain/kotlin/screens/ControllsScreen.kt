package screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import model.Device
import model.Option


data class ControllsScreen(var item: Device,var options: List<Option>) : Screen {
    @Composable
    override fun Content() {
        MaterialTheme {}
        Controlls()

    }

    fun isPC(): Boolean {
        return false
    }
    @Composable
    fun Controlls() {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                TopAppBar(
                    title = { Text(item.name) },
                    actions = {

                        IconButton(onClick = { /* Handle settings click */ }) {
                            Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = "Settings"
                            )
                        }
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))
                if (isPC()) {
                    // Prikazi po dvije opcije u redu na računaru
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(options.chunked(2)) { chunk ->
                            Column {
                                chunk.forEach { option ->
                                    OptionItem(option.name)
                                }
                            }
                        }
                    }
                } else {
                    LazyColumn {
                        items(options) { option ->
                            OptionItem(option.name)
                        }
                    }
                }

                }
            }
        }

    @Composable
    fun OptionItem(name: String){
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = name)
                Button(onClick = {  }) {
                    Text(text = "Izvrši")
                }
            }
        }
    }
    }


