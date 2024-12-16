package com.example.network.util

import java.io.IOException

object NetworkResponseCallBack {
    suspend fun <T> process(block: suspend () -> T): ApiResult<T> {
        return try {
            ApiResult.Success(block())
        } catch (e: Exception) {
            when (e) {
                is IOException -> ApiResult.NetworkError(e)
                else -> ApiResult.UnknownError(e)
            }
        }
    }
}