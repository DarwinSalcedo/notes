package com.basic.notes.feature_note.domain.model


import android.os.Message
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.basic.notes.ui.theme.Blue
import com.basic.notes.ui.theme.Red
import com.basic.notes.ui.theme.White
import com.basic.notes.ui.theme.Yellow

@Entity
data class Note(
    @PrimaryKey val id: Int?,
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int
) {
    companion object {
        val noteColors = listOf(White, Blue, Yellow, Red)
    }
}


class InvalidNoteException(message: String) : Exception(message)