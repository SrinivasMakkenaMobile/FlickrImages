package com.example.cvsflickr.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cvsflickr.di.IODispatcher
import com.example.cvsflickr.model.Image
import com.example.cvsflickr.network.ApiResult
import com.example.cvsflickr.network.FlickrImagesServiceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImagesViewModel @Inject constructor(
    private val serviceRepository: FlickrImagesServiceManager,
    @IODispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Empty)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()

    fun onQueryChange(query: String = "") {
        _query.value = query
        if (query.isNotEmpty()) {
            fetchImages(query)
        }
    }

    private fun fetchImages(query: String) {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            val request = async(dispatcher) {
                serviceRepository.getImages(query)
            }
            request.await().collect { apiResult ->
                when (apiResult) {
                    is ApiResult.Success -> {
                        val images = apiResult.response.items
                        _uiState.value = UiState.ShowImages(images.orEmpty())
                    }

                    is ApiResult.Error -> {
                        _uiState.value = UiState.Error("Something went wrong! Please try again.")
                    }
                }
            }
        }
    }

    sealed class UiState {
        object Loading : UiState()

        object Empty : UiState()

        data class ShowImages(val images: List<Image>) : UiState()

        data class Error(val errorMessage: String) : UiState()
    }
}