package com.example.network.repository

import com.example.network.api.ApiService
import com.example.network.dto.Documents
import com.example.network.dto.NetworkResponse
import com.example.network.util.ApiResult
import com.example.network.util.NetworkResponseCallBack
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : ImageRepository {
    override suspend fun getImage(query: String): Flow<ApiResult<NetworkResponse>> = flow {
        val result = NetworkResponseCallBack.process {
            apiService.getImage("KakaoAK 749cd329d09242d9ca2870a97eacccd9", query)
        }

        when (result) {
            is ApiResult.Success -> {
                emit(result.data)
            }
            is ApiResult.NetworkError -> emit(ApiResult.NetworkError(result.throwable))
            is ApiResult.UnknownError -> emit(ApiResult.UnknownError(result.throwable))
            is ApiResult.Loading -> {}
        }
    }
}