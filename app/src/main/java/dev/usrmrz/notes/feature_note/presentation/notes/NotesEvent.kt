package dev.usrmrz.notes.feature_note.presentation.notes

import dev.usrmrz.notes.feature_note.domain.model.Note
import dev.usrmrz.notes.feature_note.domain.util.NoteOrder

sealed class NotesEvent {
    data class Order(val noteOrder: NoteOrder) : NotesEvent()
    data class DeleteNote(val note: Note) : NotesEvent()
    object RestoreNote : NotesEvent()
    object ToggleOrderSection : NotesEvent()
}