package com.basic.notes.feature_note.domain.use_case

import com.basic.notes.feature_note.domain.entity.Note
import com.basic.notes.feature_note.domain.util.NoteOrder
import com.basic.notes.feature_note.domain.util.OrderType
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test


class GetNotesTest {

    private lateinit var fakeRepository: FakeRepository

    private lateinit var getNotes: GetNotes

    @Before
    fun setup() {
        fakeRepository = FakeRepository()
        getNotes = GetNotes(fakeRepository)

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
    fun `Gives a list of  notes When getNotes is called Then it return a list order by date and ascending - default`() =
        runBlocking {

            val list = getNotes().first()

            assertThat(list.size).isGreaterThan(2)
            for (i in 0..list.size - 2) {
                assertThat(list[i].timestamp).isLessThan(list[i + 1].timestamp)
            }
        }

    @Test
    fun `Gives a list of  notes When getNotes is called Then it return a list order by date and descending`() =
        runBlocking {

            val list = getNotes(NoteOrder.Date(OrderType.Descending)).first()

            assertThat(list.size).isGreaterThan(2)
            for (i in 0..list.size - 2) {
                assertThat(list[i].timestamp).isGreaterThan(list[i + 1].timestamp)
            }
        }


    @Test
    fun `Gives a list of  notes When getNotes is called Then it return a list order by Title and ascending`() =
        runBlocking {

            val list = getNotes(NoteOrder.Title(OrderType.Ascending)).first()

            assertThat(list.size).isGreaterThan(2)
            for (i in 0..list.size - 2) {
                assertThat(list[i].title).isLessThan(list[i + 1].title)
            }
        }

    @Test
    fun `Gives a list of  notes When getNotes is called Then it return a list order by Title and  descending`() =
        runBlocking {

            val list = getNotes(NoteOrder.Title(OrderType.Ascending)).first()

            assertThat(list.size).isGreaterThan(2)
            for (i in 0..list.size - 2) {
                assertThat(list[i].title).isLessThan(list[i + 1].title)
            }
        }

    @Test
    fun `Gives a list of  notes When getNotes is called Then it return a list order by Color and ascending`() =
        runBlocking {

            val list = getNotes(NoteOrder.Color(OrderType.Ascending)).first()

            assertThat(list.size).isGreaterThan(2)
            for (i in 0..list.size - 2) {
                assertThat(list[i].color).isLessThan(list[i + 1].color)
            }
        }

    @Test
    fun `Gives a list of  notes When getNotes is called Then it return a list order by Color and descending`() =
        runBlocking {

            val list = getNotes(NoteOrder.Color(OrderType.Descending)).first()

            assertThat(list.size).isGreaterThan(2)
            for (i in 0..list.size - 2) {
                assertThat(list[i].color).isGreaterThan(list[i + 1].color)
            }
        }
}

