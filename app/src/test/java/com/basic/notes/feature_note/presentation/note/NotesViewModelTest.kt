package com.basic.notes.feature_note.presentation.note

import com.basic.notes.feature_note.MainCoroutineRule
import com.basic.notes.feature_note.domain.entity.Note
import com.basic.notes.feature_note.domain.use_case.NoteUseCases
import com.basic.notes.feature_note.domain.util.NoteOrder
import com.basic.notes.feature_note.domain.util.OrderType
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@ExperimentalCoroutinesApi
class NotesViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @RelaxedMockK
    private lateinit var useCases: NoteUseCases

    private lateinit var noteViewModel: NotesViewModel


    @Before
    fun setup() {
        MockKAnnotations.init(this)
        val notes = mutableListOf<Note>()
        for (i in 0..5) {
            notes.add(Note(i, "test", "content", i.toLong(), i))
        }

        every { useCases.getNotes(any()) } returns flowOf(notes.toList())

        noteViewModel = NotesViewModel(useCases)


    }


    @Test
    fun `getNotes initial call loads notes with Date Descending order`() = runTest {
        verify(atLeast = 1) { useCases.getNotes(any(NoteOrder::class)) }

        // advanceUntilIdle()
        assertThat(
            NoteOrder.Date(OrderType.Descending).javaClass,
        ).isEqualTo(noteViewModel.state.value.noteOrder.javaClass)

        assertThat(
            noteViewModel.state.value.notes
        ).isNotEmpty()
    }

    @Test
    fun `onEvent Order changes note order and reloads notes`() = runTest {
        val newOrder = NoteOrder.Title(OrderType.Ascending)

        val newNotes = listOf(Note(title = "A", content = "...", timestamp = 0L, color = 1, id = 1))

        every { useCases.getNotes(eq(newOrder)) } returns flowOf(newNotes.toList())

        noteViewModel.onEvent(NoteEvent.Order(newOrder))


        verify { useCases.getNotes(eq(newOrder)) }


        assertThat(
            noteViewModel.state.value.noteOrder
        ).isEqualTo(newOrder)

        assertThat(
            noteViewModel.state.value.notes
        ).isEqualTo(newNotes)
    }

    @Test
    fun `onEvent DeleteNote and RestartNote works correctly`() = runTest {
        val noteToDelete = Note(title = "Test", content = "...", timestamp = 0L, color = 1, id = 1)

        coEvery { useCases.insertNote(any()) } returns (Result.success(Unit))

        noteViewModel.onEvent(NoteEvent.DeleteNote(noteToDelete))


        coVerify { useCases.deleteNote(noteToDelete) }

        noteViewModel.onEvent(NoteEvent.RestartNote)

        coVerify { useCases.insertNote(noteToDelete) }
    }
}
