package org.wysko.inspektmidi.gui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarViewWeek
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberNavigatorScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.wysko.inspektmidi.InspektMidiViewModel
import org.wysko.kmidi.midi.event.ControlChangeEvent
import org.wysko.kmidi.midi.event.ProgramEvent

object InspectScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = navigator.rememberNavigatorScreenModel { InspektMidiViewModel() }
        val smf = screenModel.smf.value

        if (smf == null) {
            Box {
                Text("No MIDI file loaded")
            }
            return
        }

        Row {
            LazyColumn(
                Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp).weight(1f),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                item {
                    Card(
                        Modifier.fillMaxWidth(),
                    ) {
                        Column(
                            Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            val headerInformation =
                                listOf(
                                    "Format ${smf.header.format.ordinal}",
                                    "${smf.header.trackCount} tracks",
                                    "${smf.header.division.ticksPerQuarterNote} ticks per quarter note",
                                )
                            Text("Header", style = typography.titleLarge)
                            Text(headerInformation.joinToString(" • "))
                        }
                    }
                }
                smf.tracks.forEachIndexed { index, track ->
                    item {
                        Card(
                            Modifier.fillMaxWidth(),
                        ) {
                            Column(
                                Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                            ) {
                                Text(
                                    listOfNotNull("Track $index", track.name).joinToString(" – "),
                                    style = typography.titleLarge,
                                )
                                Text("${track.events.size} events")
                            }
                        }
                    }
                }
            }
            LazyColumn(
                Modifier.weight(1f),
            ) {
                items(smf.tracks[3].events) {
                    when (it) {
                        is ProgramEvent -> {
                            EventCard(it.tick) {
                                EventAttribute(
                                    Icons.Default.CalendarViewWeek,
                                    "Channel",
                                    "${it.channel}",
                                )
                                EventValue("${it.program}")
                            }
                        }
                        is ControlChangeEvent -> {
                            EventCard(it.tick) {
                                EventAttribute(
                                    Icons.Default.Tune,
                                    "Controller",
                                    "${it.controller}",
                                )
                                EventAttribute(
                                    Icons.Default.CalendarViewWeek,
                                    "Channel",
                                    "${it.channel}",
                                )
                                EventValue("${it.value}")
                            }
                        }

                        else -> {
                            Text(it.toString())
                        }
                    }
                    HorizontalDivider()
                }
            }
        }
    }
}

@Composable
fun RowScope.EventValue(value: String) {
    Text(
        value,
        Modifier.weight(1f),
        fontFamily = FontFamily.Monospace,
    )
}

@Composable
fun RowScope.EventAttribute(
    icon: ImageVector,
    description: String,
    value: String,
) {
    Row(
        Modifier.Companion.weight(1f),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Icon(icon, description)
        Text(value, style = typography.labelLarge)
    }
}

@Composable
fun EventCard(
    tick: Int,
    content: @Composable () -> Unit,
) {
    Row(
        Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        EventAttribute(Icons.Default.Schedule, "Tick", "$tick")
        content()
    }
}
