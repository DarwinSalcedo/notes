package com.basic.notes.feature_note.domain.use_case

import com.basic.notes.feature_note.domain.entity.Note
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test


class GetNoteByIdTest {
    private lateinit var fakeRepository: FakeRepository

    private lateinit var getNoteUseCase: GetNoteById

    @Before
    fun setup() {
        fakeRepository = FakeRepository()
        getNoteUseCase = GetNoteById(fakeRepository)

        runBlocking {
            ('a'..'z').forEachIndexed { i, n ->

                fakeRepository.insert(
                    Note(
                        id = i,
                        title = n.toString(),
                        content = "$n content",
                        timestamp = i.toLong(),
                        color = i
                    )
                )
            }
        }
    }


    @Test
    fun `Gives a valid id When getNotes is called Then it return the proper note`() =
        runBlocking {

            val result = getNoteUseCase(1)

            assertThat(result).isNotNull()

        }


    @Test
    fun `Gives a non-valid id When getNotes is called Then it return the proper note`() =
        runBlocking {

            val result = getNoteUseCase(-1)

            assertThat(result).isNull()

        }
}