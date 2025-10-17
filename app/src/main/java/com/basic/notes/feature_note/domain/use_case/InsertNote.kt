package com.basic.notes.feature_note.domain.use_case

import com.basic.notes.feature_note.domain.model.InvalidNoteException
import com.basic.notes.feature_note.domain.model.Note
import com.basic.notes.feature_note.domain.repository.NoteRepository

class InsertNote(private val repository: NoteRepository) {

    suspend operator fun invoke(note: Note?) : Result<Unit> {
        if (note == null ) return Result.failure(InvalidNoteException("Must be not null"))
        if (note.title.isBlank()) return Result.failure(InvalidNoteException("not Title"))
        if (note.content.isBlank()) return Result.failure(InvalidNoteException("not Content"))
        println("NOTE INSERTED "+ note.toString())
        repository.insert(note)
       return Result.success(Unit)
    }
}