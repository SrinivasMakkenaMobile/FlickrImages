package com.example.cvsflickr.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ImagesResponse(
    val title: String? = null,
    val link: String? = null,
    val description: String? = null,
    val modified: String? = null,
    val generator: String? = null,
    val items: List<Image>? = null
)