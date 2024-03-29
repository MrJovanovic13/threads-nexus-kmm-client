package org.wyvern.threadsnexus

import App
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import cafe.adriel.voyager.navigator.Navigator
import initKoin
import screens.LandingScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initKoin()

        setContent {
            Navigator(LandingScreen())
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}