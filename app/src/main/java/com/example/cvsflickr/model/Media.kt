package com.example.cvsflickr.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Media (
  @Json(name = "m")
  var url : String? = null
)