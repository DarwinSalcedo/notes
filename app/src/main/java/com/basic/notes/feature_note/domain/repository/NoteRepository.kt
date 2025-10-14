package com.basic.notes.feature_note.domain.repository

import com.basic.notes.feature_note.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    suspend fun insert(note: Note)

    suspend fun delete(note: Note)

    suspend fun getNoteById(id: Int): Note?

    fun getNotes(): Flow<List<Note>>
}