package dev.usrmrz.notes.feature_note.data.repository

import dev.usrmrz.notes.feature_note.data.data_source.NoteDao
import dev.usrmrz.notes.feature_note.data.data_source.NoteEntity
import dev.usrmrz.notes.feature_note.domain.model.Note
import dev.usrmrz.notes.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NoteRepositoryImpl(
    private val dao: NoteDao
) : NoteRepository {

    private fun NoteEntity.toDomainModel(): Note {
        return Note(
            title = this.title,
            content = this.content,
            timestamp = this.timestamp,
            color = this.color,
            id = this.id
        )
    }

    private fun Note.toEntity(): NoteEntity {
        return NoteEntity(
            title = this.title,
            content = this.content,
            timestamp = this.timestamp,
            color = this.color,
            id = this.id
        )
    }

    override fun getNotes(): Flow<List<Note>> {
        return dao.getNotes().map { entities ->
            entities.map { it.toDomainModel() }
        }
    }

    override suspend fun getNoteById(id: Int): Note? {
        return dao.getNoteById(id)?.toDomainModel()
    }

    override suspend fun insertNote(note: Note) {
        dao.insertNote(note.toEntity())
    }

    override suspend fun deleteNote(note: Note) {
        dao.deleteNote(note.toEntity())
    }
}