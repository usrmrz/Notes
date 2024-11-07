package dev.usrmrz.notes.feature_note.presentation.util

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun NoteCommonFAB(
    onClick: () -> Unit,
    containerColor: Color,
    icon: ImageVector,
    contentDescription: String,
    tint: Color? = null
) {
    FloatingActionButton(
        onClick = onClick,
        modifier = Modifier.padding(16.dp),
        shape = CircleShape,
        containerColor = containerColor,
        contentColor = contentColorFor(FloatingActionButtonDefaults.containerColor),
        elevation = FloatingActionButtonDefaults.elevation()
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = tint ?: LocalContentColor.current
        )
    }
}