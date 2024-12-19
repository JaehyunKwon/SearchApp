package com.example.network.util



sealed class ApiResult<out T> {
    data class Success<out T>(val data: T) : ApiResult<T>()
    data class Error(val message: String) : ApiResult<Nothing>()
    data class NetworkError(val throwable: Throwable) : ApiResult<Nothing>()
}