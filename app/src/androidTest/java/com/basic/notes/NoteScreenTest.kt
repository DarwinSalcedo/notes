package com.basic.notes

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.basic.notes.di.AppModule
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
class NoteScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @ExperimentalAnimationApi
    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun ClickOnOrderSection_isVisible() {
        composeRule.onNodeWithTag(TestTagCompose.ORDER_SECTION).assertDoesNotExist()
        composeRule.onNodeWithContentDescription("Sort").performClick()
        composeRule.onNodeWithTag(TestTagCompose.ORDER_SECTION).assertIsDisplayed()
    }

    @Test
    fun ClickOrderSectionTwiece_isHide() {
        composeRule.onNodeWithTag(TestTagCompose.ORDER_SECTION).assertDoesNotExist()
        composeRule.onNodeWithContentDescription("Sort").performClick()
        composeRule.onNodeWithTag(TestTagCompose.ORDER_SECTION).assertIsDisplayed()
        composeRule.onNodeWithContentDescription("Sort").performClick()
        composeRule.onNodeWithTag(TestTagCompose.ORDER_SECTION).assertDoesNotExist()
    }
}