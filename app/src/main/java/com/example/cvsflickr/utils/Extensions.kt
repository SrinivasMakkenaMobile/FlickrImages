package com.example.cvsflickr.utils

import com.example.cvsflickr.model.Image
import com.squareup.moshi.Moshi
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.Locale

fun Image.imageToString(): String {
    return try {
        val moshi = Moshi.Builder().build()
        val json = moshi.adapter(Image::class.java).lenient()
        return json.toJson(this)
    } catch (e: Exception) {
        ""
    }
}

fun String.stringToImageObject(): Image? {
    return try {
        val moshi = Moshi.Builder().build()
        val json = moshi.adapter(Image::class.java).lenient()
        json.fromJson(this)
    } catch (e: Exception) {
        null
    }
}

fun String.formattedDate(): String? {
    val input = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    val output = SimpleDateFormat("MMM dd, yyyy")
    return try {
        output.format((input.parse(this)))
    } catch (e: ParseException) {
        null
    }
}