package com.basic.notes.feature_note.domain.use_case

data class NoteUseCases(
    val insertNote: InsertNote,
    val getNoteById: GetNoteById,
    val getNotes: GetNotes,
    val deleteNote: DeleteNote
)
