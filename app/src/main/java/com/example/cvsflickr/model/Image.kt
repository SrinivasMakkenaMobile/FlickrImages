package com.example.cvsflickr.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Image(
    val title: String? = null,
    val link: String? = null,
    val media: Media? = null,
    val dateTaken: String? = null,
    val description: String? = null,
    val published: String? = null,
    val author: String? = null,
    val authorId: String? = null,
    val tags: String? = null
)