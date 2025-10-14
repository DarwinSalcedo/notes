package com.basic.notes.feature_note.domain.use_case

import com.basic.notes.feature_note.domain.model.Note
import com.basic.notes.feature_note.domain.repository.NoteRepository

class InsertNote(private val repository: NoteRepository) {
    suspend operator fun invoke(note: Note) {
        repository.insert(note)
    }
}