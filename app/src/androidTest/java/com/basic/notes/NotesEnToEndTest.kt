package com.basic.notes

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.basic.notes.di.AppModule
import com.basic.notes.feature_note.domain.entity.Note
import com.basic.notes.feature_note.presentation.MainActivity
import com.basic.notes.feature_note.presentation.util.TestTagCompose
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
class NotesEnToEndTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun saveNewNote() {
        composeRule.onNodeWithContentDescription("Add").performClick()

        composeRule.onNodeWithTag(TestTagCompose.TITLE).performTextInput("test-title")
        composeRule.onNodeWithTag(TestTagCompose.CONTENT).performTextInput("test-content")
        composeRule.onNodeWithTag(Note.noteColors[2].toString()).performClick()
        composeRule.onNodeWithContentDescription("Save").performClick()

        composeRule.onNodeWithText("test-title").assertIsDisplayed()
        composeRule.onNodeWithText("test-title").performClick()

        composeRule.onNodeWithTag(TestTagCompose.TITLE).assertTextEquals("test-title")


    }

    @Test
    fun saveNewNote_andEdit() {
        composeRule.onNodeWithContentDescription("Add").performClick()

        composeRule.onNodeWithTag(TestTagCompose.TITLE).performTextInput("test-title")
        composeRule.onNodeWithTag(TestTagCompose.CONTENT).performTextInput("test-content")
        composeRule.onNodeWithContentDescription("Save").performClick()

        composeRule.onNodeWithText("test-title").assertIsDisplayed()
        composeRule.onNodeWithText("test-title").performClick()

        composeRule.onNodeWithTag(TestTagCompose.TITLE).assertTextEquals("test-title")
        composeRule.onNodeWithTag(TestTagCompose.CONTENT).assertTextEquals("test-content")
        composeRule.onNodeWithTag(TestTagCompose.TITLE).performTextInput("edited-")
        composeRule.onNodeWithTag(TestTagCompose.CONTENT).performTextInput("edited-")
        composeRule.onNodeWithContentDescription("Save").performClick()

        composeRule.onNodeWithText("edited-test-title").assertIsDisplayed()
    }

    @Test
    fun saveNewNotes_and_orders_descending() {

        for (i in 0..3) {
            composeRule.onNodeWithContentDescription("Add").performClick()

            composeRule.onNodeWithTag(TestTagCompose.TITLE).performTextInput(i.toString())
            composeRule.onNodeWithTag(TestTagCompose.CONTENT)
                .performTextInput("test-content-" + i.toString())
            composeRule.onNodeWithTag(Note.noteColors.random().toString()).performClick()
            composeRule.onNodeWithContentDescription("Save").performClick()
        }

        composeRule.onNodeWithContentDescription("Sort").performClick()
        composeRule.onNodeWithContentDescription("Title").performClick()
        composeRule.onNodeWithContentDescription("Descending").performClick()

        composeRule.onAllNodesWithTag(TestTagCompose.NOTE_ITEM)[0].assertTextContains("3")
        composeRule.onAllNodesWithTag(TestTagCompose.NOTE_ITEM)[1].assertTextContains("2")
        composeRule.onAllNodesWithTag(TestTagCompose.NOTE_ITEM)[2].assertTextContains("1")
        composeRule.onAllNodesWithTag(TestTagCompose.NOTE_ITEM)[3].assertTextContains("0")


    }
}