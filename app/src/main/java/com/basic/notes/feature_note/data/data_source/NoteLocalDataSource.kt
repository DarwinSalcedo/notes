package com.basic.notes.feature_note.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.basic.notes.feature_note.domain.entity.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteLocalDataSource {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note)

    @Delete
    suspend fun delete(note: Note)

    @Query("Select * From note Where id = :id")
    suspend fun getNoteById(id: Int): Note?

    @Query("Select * From note")
    fun getNotes(): Flow<List<Note>>



}