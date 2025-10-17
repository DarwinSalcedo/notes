package com.basic.notes.feature_note.presentation.add_edit_note

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.basic.notes.feature_note.domain.model.Note
import com.basic.notes.feature_note.domain.use_case.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor
    (
    private val useCases: NoteUseCases,
    savedSateHandle: SavedStateHandle
) : ViewModel() {

    private var currentNoteId: Int? = null
    private val _noteTitle = mutableStateOf<NoteTextField>(NoteTextField(hint = "Enter title ..."))
    val noteTitle: State<NoteTextField> = _noteTitle

    private val _noteContent =
        mutableStateOf<NoteTextField>(NoteTextField(hint = "Enter content ..."))
    val noteContent: State<NoteTextField> = _noteContent

    private val _noteColor = mutableStateOf(0)
    var noteColor: State<Int> = _noteColor

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        savedSateHandle.get<Int>("noteId")?.let { _noteId ->
            if (_noteId != -1) {
                viewModelScope.launch {
                    useCases.getNoteById(_noteId)?.also { note ->
                        currentNoteId = note.id
                        _noteTitle.value = noteTitle.value.copy(
                            text = note.title,
                            isHintVisible = false
                        )
                        _noteContent.value = _noteContent.value.copy(
                            text = note.content,
                            isHintVisible = false
                        )
                        _noteColor.value = note.color
                    }

                }
            }
        }
    }

    fun onEvent(event: AddEditNoteEvent) {
        when (event) {
            is AddEditNoteEvent.EnteredTitleData -> {
                _noteTitle.value = noteTitle.value.copy(text = event.value)
            }

            is AddEditNoteEvent.EnteredContentData -> {
                _noteContent.value = noteContent.value.copy(text = event.value)
            }

            is AddEditNoteEvent.ChangeTitleFocus -> {
                _noteTitle.value =
                    noteTitle.value.copy(isHintVisible = !event.focus.isFocused && noteTitle.value.text.isBlank())
            }

            is AddEditNoteEvent.ChangeContentFocus -> {
                _noteContent.value =
                    noteContent.value.copy(isHintVisible = !event.focus.isFocused && noteContent.value.text.isBlank())
            }

            is AddEditNoteEvent.ChangeColor -> {
                _noteColor.value = event.color
            }

            AddEditNoteEvent.SaveNote -> {
                viewModelScope.launch {
                    val result = useCases.insertNote(
                        Note(
                            id = currentNoteId,
                            title = _noteTitle.value.text,
                            content = _noteContent.value.text,
                            timestamp = System.currentTimeMillis(),
                            color = _noteColor.value
                        )
                    )
                    if (result.isSuccess) {
                        _eventFlow.emit(UiEvent.SaveNote)
                    } else
                        _eventFlow.emit(UiEvent.ShowSnackBar("Couldnt save note"))
                }
            }
        }
    }
}