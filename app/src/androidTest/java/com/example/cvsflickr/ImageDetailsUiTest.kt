package com.example.cvsflickr

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.example.cvsflickr.ui.theme.CVSFlickrTheme
import com.example.cvsflickr.view.ImageDetailsScreen
import org.junit.Rule
import org.junit.Test
import com.example.cvsflickr.model.Image
import com.example.cvsflickr.model.Media
import com.example.cvsflickr.view.TestTags

class ImageDetailsUiTest {
    @get: Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testUiElementsExist() {
        composeTestRule.setContent {
            CVSFlickrTheme {
                ImageDetailsScreen(image = Image(title = "CVS", media = Media("")))
            }
        }
        composeTestRule.apply{
            onNodeWithTag(TestTags.IMAGE).assertIsDisplayed()
            onNodeWithTag(TestTags.TITLE).assertIsDisplayed()
        }
    }
}