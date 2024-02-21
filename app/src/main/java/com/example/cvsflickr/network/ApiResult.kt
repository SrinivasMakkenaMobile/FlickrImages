package com.example.cvsflickr.network


sealed class ApiResult<T: Any> {
    data class Success<T: Any>(
        val response: T
    ) : ApiResult<T>()

    data class Error<T: Any>(val error: String) : ApiResult<T>()
}