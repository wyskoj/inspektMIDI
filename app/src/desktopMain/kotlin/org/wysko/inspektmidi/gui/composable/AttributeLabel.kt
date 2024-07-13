package org.wysko.inspektmidi.gui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AttributeLabel(text: String) {
    Box(
        Modifier.background(MaterialTheme.colorScheme.primaryContainer, MaterialTheme.shapes.small),
    ) {
        Text(text, color = MaterialTheme.colorScheme.onPrimaryContainer, modifier = Modifier.padding(4.dp))
    }
}
