package com.example.network.util



sealed class ApiResult<out T> {
    data object Loading : ApiResult<Nothing>()
    data class Success<out T>(val data: T) : ApiResult<T>()
    data class NetworkError(val throwable: Throwable) : ApiResult<Nothing>()
    data class UnknownError(val throwable: Throwable) : ApiResult<Nothing>()

}