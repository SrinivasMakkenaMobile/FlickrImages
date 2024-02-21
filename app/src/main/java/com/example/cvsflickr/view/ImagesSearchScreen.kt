package com.example.cvsflickr.view

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.cvsflickr.R
import com.example.cvsflickr.model.Image
import com.example.cvsflickr.viewmodel.ImagesViewModel
import kotlinx.coroutines.Dispatchers

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImagesSearchScreen(
    viewModel: ImagesViewModel = hiltViewModel(),
    navigateToDetails: (Image) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val query by viewModel.query.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = null) {
        viewModel.onQueryChange() // If needs to load the default data on launch, need to pass the query
    }
    Scaffold(
        topBar = {
            SearchBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp),
                query = query,
                onQueryChange = viewModel::onQueryChange,
                placeholder = { Text(text = stringResource(id = R.string.input_placeholder)) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null
                    )
                },
                trailingIcon = {
                    Icon(
                        modifier = Modifier.clickable { viewModel.onQueryChange("") },
                        imageVector = Icons.Default.Clear,
                        contentDescription = null
                    )
                },
                onSearch = viewModel::onQueryChange,
                active = true,
                onActiveChange = {}
            ) {

            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            when (uiState) {
                ImagesViewModel.UiState.Loading -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(modifier = Modifier.size(48.dp))
                    }
                }

                is ImagesViewModel.UiState.ShowImages -> {
                    ImagesList(
                        images = (uiState as ImagesViewModel.UiState.ShowImages).images,
                        navigateToDetails = navigateToDetails
                    )
                }

                is ImagesViewModel.UiState.Error -> {
                    Toast.makeText(
                        LocalContext.current,
                        (uiState as ImagesViewModel.UiState.Error).errorMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                }

                else -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(id = R.string.search_empty_state),
                            style = TextStyle(
                                fontSize = 24.sp,
                                textAlign = TextAlign.Center
                            )
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ImagesList(
    images: List<Image>,
    navigateToDetails: (Image) -> Unit
) {
    val context = LocalContext.current
    LazyVerticalGrid(
        columns = GridCells.Adaptive(120.dp),
        state = rememberLazyGridState()
    ) {
        images.forEach { image ->
            val model = ImageRequest
                .Builder(context)
                .data(image.media?.url)
                .dispatcher(Dispatchers.IO)
                .diskCacheKey(image.media?.url)
                .build()

            item {
                Card(
                    modifier = Modifier
                        .size(120.dp)
                        .padding(8.dp)
                        .clickable {
                            navigateToDetails.invoke(image)
                        },
                    shape = RoundedCornerShape(4.dp)
                ) {
                    SubcomposeAsyncImage(
                        modifier = Modifier.fillMaxSize(),
                        model = model,
                        contentScale = ContentScale.None,
                        contentDescription = "Some alt text for accessibility"
                    )
                }
            }
        }
    }
}

