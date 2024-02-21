package com.example.cvsflickr

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cvsflickr.ui.theme.CVSFlickrTheme
import com.example.cvsflickr.utils.imageToString
import com.example.cvsflickr.utils.stringToImageObject
import com.example.cvsflickr.view.ImageDetailsScreen
import com.example.cvsflickr.view.ImagesSearchScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CVSFlickrTheme {
                val navController = rememberNavController()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = Destinations.GRID.value
                    ) {
                        composable(route = Destinations.GRID.value) {
                            ImagesSearchScreen(
                                navigateToDetails = { image ->
                                    navController.navigate(
                                        Destinations.DETAILS.value.replace(
                                            "{image}",
                                            image.imageToString()
                                        )
                                    )
                                }
                            )
                        }
                        composable(route = Destinations.DETAILS.value) { entry ->
                            val image = entry.arguments?.getString("image")?.stringToImageObject()
                            image?.let {
                                ImageDetailsScreen(it)
                            } ?: Toast.makeText(
                                LocalContext.current,
                                stringResource(id = R.string.generic_error),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }
}

enum class Destinations(val value: String) {
    GRID("grid"),
    DETAILS("details?image={image}")
}

