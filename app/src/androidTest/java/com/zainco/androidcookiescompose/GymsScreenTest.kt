package com.zainco.androidcookiescompose

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.zainco.androidcookiescompose.gyms.domain.Gym
import com.zainco.androidcookiescompose.gyms.presentation.SemanticDescription
import com.zainco.androidcookiescompose.gyms.presentation.gymslist.GymScreenState
import com.zainco.androidcookiescompose.gyms.presentation.gymslist.GymsScreen
import com.zainco.androidcookiescompose.ui.theme.AndroidCookiesComposeTheme
import org.junit.Rule
import org.junit.Test

class GymsScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun loadingState_isActive() {
        composeTestRule.setContent {
            AndroidCookiesComposeTheme {
                GymsScreen(state = GymScreenState(
                    gymsList = emptyList(),
                    isLoading = true
                ),
                    onItemClick = {}, onFavouriteIconClick = { _: Int, _: Boolean -> })
            }
        }
        composeTestRule.onNodeWithContentDescription(SemanticDescription.GYMS_LIST_LOADING)
            .assertIsDisplayed()
    }

    @Test
    fun loadContentState_isActive() {
        val gymsList = getDummyGymsList()
        composeTestRule.setContent {
            AndroidCookiesComposeTheme {
                GymsScreen(state = GymScreenState(
                    gymsList = gymsList,
                    isLoading = false
                ),
                    onItemClick = {}, onFavouriteIconClick = { _: Int, _: Boolean -> })
            }
        }
        composeTestRule.onNodeWithText(gymsList[0].name).assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription(SemanticDescription.GYMS_LIST_LOADING)
            .assertDoesNotExist()
    }

    @Test
    fun errorState_isActive() {
        val errorText = "Failed to load data"
        composeTestRule.setContent {
            AndroidCookiesComposeTheme {
                GymsScreen(state = GymScreenState(
                    gymsList = emptyList(),
                    isLoading = false,
                    error = errorText
                ),
                    onItemClick = {}, onFavouriteIconClick = { _: Int, _: Boolean -> })
            }
        }
        composeTestRule.onNodeWithText(errorText).assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription(SemanticDescription.GYMS_LIST_LOADING)
            .assertDoesNotExist()
    }

    @Test
    fun onItemClick_idIsPassedCorrectly() {
        val gymsList = getDummyGymsList()
        val gymItem = gymsList[0]
        composeTestRule.setContent {
            AndroidCookiesComposeTheme {
                GymsScreen(state = GymScreenState(
                    gymsList = gymsList,
                    isLoading = false
                ),
                    onItemClick = { id ->
                        assert(id == gymItem.id)
                    }, onFavouriteIconClick = { _: Int, _: Boolean -> })
            }
        }
        composeTestRule.onNodeWithText(gymItem.name).performClick()
    }

    private fun getDummyGymsList() = arrayListOf(
        Gym(0, "n0", "p0", false),
        Gym(1, "n1", "p1", false),
        Gym(2, "n2", "p2", false),
        Gym(3, "n3", "p3", false),
        Gym(4, "n4", "p4", false),
    )
}