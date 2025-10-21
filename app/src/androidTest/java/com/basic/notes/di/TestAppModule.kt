package com.basic.notes.di


import android.app.Application
import androidx.room.Room
import com.basic.notes.feature_note.data.data_source.NoteDatabase
import com.basic.notes.feature_note.data.repository.NoteRepositoryImpl
import com.basic.notes.feature_note.domain.repository.NoteRepository
import com.basic.notes.feature_note.domain.use_case.DeleteNote
import com.basic.notes.feature_note.domain.use_case.GetNoteById
import com.basic.notes.feature_note.domain.use_case.GetNotes
import com.basic.notes.feature_note.domain.use_case.InsertNote
import com.basic.notes.feature_note.domain.use_case.NoteUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): NoteDatabase {
        return Room.inMemoryDatabaseBuilder(app, NoteDatabase::class.java).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(db: NoteDatabase): NoteRepository {
        return NoteRepositoryImpl(db.dao)
    }


    @Provides
    @Singleton
    fun provideNoteUsesCases(repository: NoteRepository): NoteUseCases {
        return NoteUseCases(
            insertNote = InsertNote(repository),
            getNoteById = GetNoteById(repository),
            getNotes = GetNotes(repository),
            deleteNote = DeleteNote(repository)
        )
    }
}