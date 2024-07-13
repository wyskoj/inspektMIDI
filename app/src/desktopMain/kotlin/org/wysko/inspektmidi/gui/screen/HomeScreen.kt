package org.wysko.inspektmidi.gui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.model.rememberNavigatorScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.darkrockstudios.libraries.mpfilepicker.FilePicker
import org.wysko.inspektmidi.InspektMidiViewModel
import java.io.File

object HomeScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = navigator.rememberNavigatorScreenModel { InspektMidiViewModel() }
        var isShowFilePicker by remember { mutableStateOf(false) }

        LaunchedEffect(Unit) {
            screenModel.loadMidiFile(File("/home/jacob/Dropbox/MIDI/MIDI Files/Jimmy Eat World - The Middle.mid"))
            navigator.push(InspectScreen)
        }

        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            Column(horizontalAlignment = CenterHorizontally) {
                Button(onClick = { isShowFilePicker = true }) {
                    Text("Open")
                }
                Text("or drag and drop")
            }
        }

        FilePicker(
            isShowFilePicker,
            fileExtensions = listOf("mid", "midi"),
        ) { path ->
            isShowFilePicker = false
            path?.let {
                screenModel.loadMidiFile(File(it.path))
                navigator.push(InspectScreen)
            }
        }
    }
}
