package com.basic.notes.feature_note.domain.use_case

import com.basic.notes.feature_note.domain.entity.InvalidNoteException
import com.basic.notes.feature_note.domain.entity.Note
import com.basic.notes.feature_note.domain.repository.NoteRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test


class InsertNoteTest {

    @RelaxedMockK
    private lateinit var fakeRepository: NoteRepository

    private lateinit var insertNoteUseCase: InsertNote

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        insertNoteUseCase = InsertNote(fakeRepository)
    }

    @Test
    fun `Given a null note when Insert is called then Return a error validation`() = runBlocking {
        val result = insertNoteUseCase.invoke(null)
        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()).isInstanceOf(InvalidNoteException::class.java)
        assertThat(result.exceptionOrNull()?.message).isEqualTo("Must be not null")

    }

    @Test
    fun `Given a empty title note when Insert is called then Return a error validation`() =
        runBlocking {
            val result = insertNoteUseCase.invoke(Note(1, "", "dsadsa", 1L, 1))
            assertThat(result.isFailure).isTrue()
            assertThat(result.exceptionOrNull()).isInstanceOf(InvalidNoteException::class.java)
            assertThat(result.exceptionOrNull()?.message).isEqualTo("Not Title")

        }

    @Test
    fun `Given a empty content note when Insert is called then Return a error validation`() =
        runBlocking {
            val result = insertNoteUseCase.invoke(Note(1, "dasd", "", 1L, 1))
            assertThat(result.isFailure).isTrue()
            assertThat(result.exceptionOrNull()).isInstanceOf(InvalidNoteException::class.java)
            assertThat(result.exceptionOrNull()?.message).isEqualTo("Not Content")

        }

    @Test
    fun `Given a valid note when Insert is called then Return a error validation`() =
        runBlocking {
            val note = Note(1, "dasd", "dasda", 1L, 1)
            coEvery { fakeRepository.insert(note) } returns Unit

            val result = insertNoteUseCase.invoke(note)
            assertThat(result.isSuccess).isTrue()
            assertThat(result.isFailure).isFalse()

        }


}