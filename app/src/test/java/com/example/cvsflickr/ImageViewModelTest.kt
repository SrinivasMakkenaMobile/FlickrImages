package com.example.cvsflickr

import com.example.cvsflickr.model.Image
import com.example.cvsflickr.model.ImagesResponse
import com.example.cvsflickr.network.ApiResult
import com.example.cvsflickr.network.FlickrImagesServiceManager
import com.example.cvsflickr.viewmodel.ImagesViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class ImageViewModelTest {

    private lateinit var imagesViewModel: ImagesViewModel

    private val serviceRepository: FlickrImagesServiceManager = mockk()

    @Before
    fun setUP() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @Test
    fun `verify getImages API getting invoked`() = runTest {
        imagesViewModel =
            ImagesViewModel(serviceRepository, UnconfinedTestDispatcher(testScheduler))
        val query = "test"

        coEvery { serviceRepository.getImages(any()) } returns flow {
            emit(
                ApiResult.Success(
                    ImagesResponse(
                        items = listOf(Image(title = "Test"))
                    )
                )
            )
        }.flowOn(UnconfinedTestDispatcher())

        imagesViewModel.onQueryChange(query)

        coVerify {
            serviceRepository.getImages(any())
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}