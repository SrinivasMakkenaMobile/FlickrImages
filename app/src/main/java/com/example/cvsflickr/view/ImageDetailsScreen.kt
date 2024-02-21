package com.example.cvsflickr.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.cvsflickr.R
import com.example.cvsflickr.model.Image
import com.example.cvsflickr.utils.formattedDate
import dev.jeziellago.compose.markdowntext.MarkdownText
import kotlinx.coroutines.Dispatchers

@Composable
fun ImageDetailsScreen(image: Image) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        val model = ImageRequest
            .Builder(LocalContext.current)
            .data(image.media?.url)
            .dispatcher(Dispatchers.IO)
            .diskCacheKey(image.media?.url)
            .build()
        SubcomposeAsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(16.dp)
                .testTag(TestTags.IMAGE),
            model = model,
            contentDescription = "image"
        )
        image.title?.let {
            Text(
                modifier = Modifier
                    .padding(16.dp)
                    .testTag(TestTags.TITLE),
                text = it,
                style = TextStyle(fontSize = 32.sp, textAlign = TextAlign.Center)
            )
        }

        image.description?.let {
            MarkdownText(
                style = TextStyle(
                    textAlign = TextAlign.Center
                ),
                markdown = it,
                modifier = Modifier.padding(16.dp),
            )
        }

        image.author?.let {
            Text(
                modifier = Modifier.padding(16.dp),
                text = it,
                style = TextStyle(fontSize = 20.sp, textAlign = TextAlign.Center)
            )
        }

        image.published?.let {
            it.formattedDate()?.let { date ->
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = stringResource(id = R.string.published_on, date),
                    style = TextStyle(fontSize = 20.sp, textAlign = TextAlign.Center)
                )
            }
        }
    }
}

object TestTags {
    const val IMAGE = "image"
    const val TITLE = "title"
}