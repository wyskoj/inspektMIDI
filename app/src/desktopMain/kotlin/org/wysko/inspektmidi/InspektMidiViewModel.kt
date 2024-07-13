package org.wysko.inspektmidi

import cafe.adriel.voyager.core.model.ScreenModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.wysko.kmidi.midi.StandardMidiFile
import org.wysko.kmidi.midi.StandardMidiFileReader
import org.wysko.kmidi.readFile
import java.io.File

class InspektMidiViewModel : ScreenModel {
    private val _smf = MutableStateFlow<StandardMidiFile?>(null)
    val smf: StateFlow<StandardMidiFile?> get() = _smf

    fun loadMidiFile(file: File) {
        _smf.value = reader.readFile(file)
    }

    companion object {
        private val reader = StandardMidiFileReader()
    }
}
