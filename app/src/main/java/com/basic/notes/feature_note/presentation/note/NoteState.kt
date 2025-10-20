package com.basic.notes.feature_note.presentation.note

import com.basic.notes.feature_note.domain.entity.Note
import com.basic.notes.feature_note.domain.util.NoteOrder
import com.basic.notes.feature_note.domain.util.OrderType

data class NoteState(
    val notes: List<Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false
)
