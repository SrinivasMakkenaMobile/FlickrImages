package com.example.cvsflickr.di

import com.example.cvsflickr.network.ImagesApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(ViewModelComponent::class)
class ImagesModule {

    @Provides
    fun providesImagesApi(retrofit: Retrofit): ImagesApi = 
        retrofit.create(ImagesApi::class.java)
    

    @Provides
    fun providesRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(FLICKR_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    companion object {
        const val FLICKR_BASE_URL = "https://api.flickr.com/"
    }
}

