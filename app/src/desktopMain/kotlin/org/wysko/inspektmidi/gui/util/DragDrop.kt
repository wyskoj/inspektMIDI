package org.wysko.inspektmidi.gui.util

import androidx.compose.ui.window.FrameWindowScope
import java.awt.datatransfer.DataFlavor
import java.awt.dnd.DnDConstants
import java.awt.dnd.DropTarget
import java.awt.dnd.DropTargetDropEvent
import java.io.File

/**
 * Registers a drag and drop listener on the window's content pane.
 *
 * @param onFileDrop The function to call when a file is dropped.
 */
fun FrameWindowScope.registerDragAndDrop(onFileDrop: (File) -> Unit) {
    this.window.contentPane.dropTarget =
        object : DropTarget() {
            @Synchronized
            override fun drop(event: DropTargetDropEvent) {
                event.acceptDrop(DnDConstants.ACTION_REFERENCE)
                val transferData = event.transferable.getTransferData(DataFlavor.javaFileListFlavor) as? List<*>
                val file = transferData?.firstOrNull() as? File ?: return
                onFileDrop(file)
            }
        }
}
