package com.example.cvsflickr

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class FlickrApplication : Application() {
    val application: FlickrApplication
        get() = instance


    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: FlickrApplication
            private set

        val applicationContext: Context
            get() = instance.applicationContext
    }
}