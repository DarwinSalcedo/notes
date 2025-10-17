package com.basic.notes.feature_note.presentation.util

sealed class Screen(val route: String) {
    object Home : Screen("home_screen")
    object AddEdit : Screen("add_edit_scree")
}