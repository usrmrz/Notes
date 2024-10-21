package dev.usrmrz.notes.feature_note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.usrmrz.notes.ui.theme.BabyBlue
import dev.usrmrz.notes.ui.theme.LightGreen
import dev.usrmrz.notes.ui.theme.RedOrange
import dev.usrmrz.notes.ui.theme.RedPink
import dev.usrmrz.notes.ui.theme.Violet

@Entity
data class Note(
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int,
    @PrimaryKey val id: Int? = null
) {
    companion object {
        val noteColors = listOf(RedOrange, LightGreen, Violet, BabyBlue, RedPink)
    }
}
