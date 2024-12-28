package dev.usrmrz.notes.feature_note.domain.model

import dev.usrmrz.notes.ui.theme.BabyBlue
import dev.usrmrz.notes.ui.theme.LightGreen
import dev.usrmrz.notes.ui.theme.RedOrange
import dev.usrmrz.notes.ui.theme.RedPink
import dev.usrmrz.notes.ui.theme.Violet

data class Note(
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int,
    val id: Int? = null
) {
    companion object {
        val noteColors = listOf(RedOrange, LightGreen, Violet, BabyBlue, RedPink)
    }
}

class InvalidNoteException(message: String) : Exception(message)
