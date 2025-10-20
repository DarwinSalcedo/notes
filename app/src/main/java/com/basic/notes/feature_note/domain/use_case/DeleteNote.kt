package com.basic.notes.feature_note.domain.use_case

import com.basic.notes.feature_note.domain.entity.Note
import com.basic.notes.feature_note.domain.repository.NoteRepository

class DeleteNote(private val repository: NoteRepository) {
    suspend operator fun invoke(note: Note) {
        repository.delete(note)
    }
}