package com.basic.notes.feature_note.presentation.add_edit_note

import androidx.compose.ui.focus.FocusState

sealed class AddEditNoteEvent {
    data class EnteredTitleData(val value: String) : AddEditNoteEvent()
    data class ChangeTitleFocus(val focus: FocusState) : AddEditNoteEvent()
    data class EnteredContentData(val value: String) : AddEditNoteEvent()
    data class ChangeContentFocus(val focus: FocusState) : AddEditNoteEvent()
    data class ChangeColor(val color: Int) : AddEditNoteEvent()
    object SaveNote : AddEditNoteEvent()
}