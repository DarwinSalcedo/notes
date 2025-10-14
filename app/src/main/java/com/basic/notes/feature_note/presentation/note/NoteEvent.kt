package com.basic.notes.feature_note.presentation.note

import com.basic.notes.feature_note.domain.model.Note
import com.basic.notes.feature_note.domain.util.NoteOrder

sealed class NoteEvent {
    data class Order(val noteOrder: NoteOrder) : NoteEvent()
    data class DeleteNote(val note: Note) : NoteEvent()
    object RestartNote : NoteEvent()
    object ToggleOrderSection : NoteEvent()
}