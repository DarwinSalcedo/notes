package com.basic.notes.feature_note.presentation.note.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.basic.notes.feature_note.domain.util.NoteOrder
import com.basic.notes.feature_note.domain.util.OrderType

@Composable
fun OrderSection(
    modifier: Modifier,
    noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    onOrderChange: (NoteOrder) -> Unit
) {
    Column(modifier = modifier) {
        Row(Modifier.fillMaxWidth()) {
            DefaultRadioButton(
                text = "Title",
                checked = noteOrder is NoteOrder.Title,
                onChecked = { onOrderChange(NoteOrder.Title(noteOrder.orderType)) })
            Spacer(Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Date",
                checked = noteOrder is NoteOrder.Date,
                onChecked = { onOrderChange(NoteOrder.Date(noteOrder.orderType)) })
            Spacer(Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Color",
                checked = noteOrder is NoteOrder.Color,
                onChecked = { onOrderChange(NoteOrder.Color(noteOrder.orderType)) })
        }
        Spacer(Modifier.height(16.dp))
        Row(Modifier.fillMaxWidth()) {
            DefaultRadioButton(
                text = "Ascending",
                checked = noteOrder.orderType is OrderType.Ascending,
                onChecked = { onOrderChange(noteOrder.copy(OrderType.Ascending)) })
            Spacer(Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Descending",
                checked = noteOrder.orderType is OrderType.Descending,
                onChecked = { onOrderChange(noteOrder.copy(OrderType.Descending)) })
        }
    }
}