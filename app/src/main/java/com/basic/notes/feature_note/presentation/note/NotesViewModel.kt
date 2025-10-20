package com.basic.notes.feature_note.presentation.note

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.basic.notes.feature_note.domain.entity.Note
import com.basic.notes.feature_note.domain.use_case.NoteUseCases
import com.basic.notes.feature_note.domain.util.NoteOrder
import com.basic.notes.feature_note.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(private val useCases: NoteUseCases) : ViewModel() {

    private val _state = mutableStateOf(NoteState())
    val state: State<NoteState> = _state

    private var noteDeleted: Note? = null

    private var getNotesJob: Job? = null


    init {
        getNotes(NoteOrder.Date(OrderType.Descending))
    }

    private fun getNotes(noteOrder: NoteOrder) {
        getNotesJob?.cancel()
        getNotesJob = useCases.getNotes(noteOrder).onEach { _notes ->
            _state.value = state.value.copy(notes = _notes, noteOrder = noteOrder)
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: NoteEvent) {
        when (event) {
            is NoteEvent.DeleteNote -> {
                viewModelScope.launch {
                    useCases.deleteNote(event.note)
                    noteDeleted = event.note
                }
            }

            is NoteEvent.Order -> {
                viewModelScope.launch {
                    if (_state.value.noteOrder::class == event.noteOrder::class &&
                        _state.value.noteOrder.orderType == event.noteOrder.orderType
                    ) return@launch

                    getNotes(event.noteOrder)
                }
            }

            NoteEvent.RestartNote -> {
                viewModelScope.launch {
                    val result = useCases.insertNote(noteDeleted)
                    if (result.isSuccess)
                        noteDeleted = null
                }
            }

            NoteEvent.ToggleOrderSection -> {
                _state.value =
                    state.value.copy(isOrderSectionVisible = !state.value.isOrderSectionVisible)
            }
        }

    }
}