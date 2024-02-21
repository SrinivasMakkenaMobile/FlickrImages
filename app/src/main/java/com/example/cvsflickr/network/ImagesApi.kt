package com.example.cvsflickr.network

import com.example.cvsflickr.model.ImagesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ImagesApi {

    @GET("/services/feeds/photos_public.gne")
    suspend fun getImages(
        @QueryMap queryParams: Map<String, String>
    ): Response<ImagesResponse>

}