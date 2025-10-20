package com.basic.notes.feature_note.domain.use_case

import com.basic.notes.feature_note.domain.entity.Note
import com.basic.notes.feature_note.domain.repository.NoteRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockkClass
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test


class DeleteNoteTest {

    @RelaxedMockK
    private lateinit var fakeRepository: NoteRepository

    private lateinit var deleteUseCase: DeleteNote

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        fakeRepository = mockkClass(NoteRepository::class)
        deleteUseCase = DeleteNote(fakeRepository)
    }

    @Test
    fun `Given a note When deleted is called Then the use case will be called once`() =
        runBlocking {
            val dummieNote = Note(1, "title", "content", 1L, 1)
            coEvery { fakeRepository.delete(dummieNote) } returns (Unit)

            deleteUseCase.invoke(dummieNote)

            coVerify(exactly = 1) { fakeRepository.delete(dummieNote) }

        }


}