package com.basic.notes.feature_note.presentation.note

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.basic.notes.feature_note.presentation.note.components.NoteItem
import com.basic.notes.feature_note.presentation.note.components.OrderSection
import com.basic.notes.feature_note.presentation.util.Screen
import com.basic.notes.feature_note.presentation.util.TestTagCompose.Companion.ORDER_SECTION
import kotlinx.coroutines.launch


@Composable
fun NoteScreen(navController: NavController, viewModel: NotesViewModel = hiltViewModel()) {

    val state = viewModel.state.value
    val scope = rememberCoroutineScope()

    val snackBarHostState: SnackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(true) {

    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackBarHostState) },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(route = Screen.AddEdit.route)
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
            }
        }) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                Alignment.CenterVertically
            ) {
                Text(text = "Notes", style = MaterialTheme.typography.headlineLarge)

                IconButton(
                    onClick = { viewModel.onEvent(NoteEvent.ToggleOrderSection) },
                ) {
                    Icon(imageVector = Icons.Default.Menu, "Sort")
                }
            }
            AnimatedVisibility(
                visible = state.isOrderSectionVisible,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                OrderSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp).
                        testTag(ORDER_SECTION),
                    noteOrder = state.noteOrder,
                    onOrderChange = {
                        viewModel.onEvent(NoteEvent.Order(it))
                    })
            }
            Spacer(Modifier.height(16.dp))
            LazyColumn(Modifier.fillMaxSize()) {
                items(state.notes) { note ->
                    NoteItem(
                        note = note,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { navController.navigate(Screen.AddEdit.route + "?noteId=${note.id}" + "?noteColor=${note.color}") },
                        onDeleteClick = {
                            viewModel.onEvent(NoteEvent.DeleteNote(note))
                            scope.launch {
                                val result = snackBarHostState.showSnackbar("Note deleted", "undo")
                                if (result == SnackbarResult.ActionPerformed) {
                                    viewModel.onEvent(NoteEvent.RestartNote)
                                }
                            }
                        }
                    )
                    Spacer(Modifier.height(8.dp))

                }
            }
            Spacer(Modifier.height(16.dp))
        }
    }
}