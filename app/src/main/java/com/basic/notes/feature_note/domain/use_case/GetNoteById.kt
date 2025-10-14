package com.basic.notes.feature_note.domain.use_case

import com.basic.notes.feature_note.domain.model.Note
import com.basic.notes.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class GetNoteById(private val repository: NoteRepository) {

    operator fun invoke(note: Note): Flow<Note?> {
        return flow {
            withContext(Dispatchers.IO) {
                try {
                    val fetchedNote = repository.getNoteById(note.id!!)
                    emit(fetchedNote)
                } catch (e: Exception) {
                    emit(null)
                }
            }
        }

    }
}