package com.basic.notes.feature_note.domain.use_case

import com.basic.notes.feature_note.domain.entity.Note
import com.basic.notes.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class FakeRepository : NoteRepository {

    private val notes = mutableListOf<Note>()

    override suspend fun insert(note: Note) {
        notes.add(note)
    }

    override suspend fun delete(note: Note) {
        notes.remove(note)
    }

    override suspend fun getNoteById(id: Int): Note? {
        return notes.find { it.id == id }
    }

    override fun getNotes(): Flow<List<Note>> {
        return flow {
            emit(notes)
        }.flowOn(Dispatchers.IO)
    }
}