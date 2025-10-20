package com.basic.notes.feature_note.data.repository

import com.basic.notes.feature_note.data.data_source.NoteLocalDataSource
import com.basic.notes.feature_note.domain.entity.Note
import com.basic.notes.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow


class NoteRepositoryImpl(private val dao: NoteLocalDataSource) : NoteRepository {

    override suspend fun insert(note: Note) {
        dao.insert(note)
    }

    override suspend fun delete(note: Note) {
        dao.delete(note)
    }

    override suspend fun getNoteById(id: Int): Note? {
        return dao.getNoteById(id)
    }

    override fun getNotes(): Flow<List<Note>> {
        return dao.getNotes()
    }

}