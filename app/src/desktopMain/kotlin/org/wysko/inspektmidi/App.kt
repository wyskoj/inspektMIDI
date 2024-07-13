package org.wysko.inspektmidi

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.Navigator
import org.wysko.inspektmidi.gui.screen.HomeScreen
import org.wysko.inspektmidi.gui.theme.AppTheme

@Composable
fun App() {
    AppTheme(useDarkTheme = true) {
        Scaffold(Modifier.fillMaxSize()) {
            Navigator(HomeScreen)
        }
    }
}
