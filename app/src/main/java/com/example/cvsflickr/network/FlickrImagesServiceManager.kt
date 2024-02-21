package com.example.cvsflickr.network

import com.example.cvsflickr.di.IODispatcher
import com.example.cvsflickr.model.ImagesResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FlickrImagesServiceManager @Inject constructor(
    private val imagesApi: ImagesApi,
    @IODispatcher private val dispatcher: CoroutineDispatcher
) {
    suspend fun getImages(query: String): Flow<ApiResult<ImagesResponse>> {
        val queryParams = mutableMapOf<String, String>()
        queryParams["tags"] = query
        queryParams["nojsoncallback"] = "1"
        queryParams["format"] = "json"
        val result = imagesApi.getImages(queryParams)
        val responseBody = result.body()
        return flow {
            if (result.isSuccessful && responseBody != null) {
                emit(ApiResult.Success(response = responseBody))
            } else {
                emit(ApiResult.Error(error = ""))
            }
        }.flowOn(dispatcher)
    }
}
