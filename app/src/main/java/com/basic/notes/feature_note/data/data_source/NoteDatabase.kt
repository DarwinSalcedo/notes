package com.basic.notes.feature_note.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.basic.notes.feature_note.domain.model.Note

@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {

    abstract val dao: NoteLocalDataSource

    companion object{
        const val NAME_DB = "note_db"
    }
}