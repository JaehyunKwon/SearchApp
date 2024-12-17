package com.example.network.repository

import com.example.network.dto.NetworkResponse
import com.example.network.util.ApiResult
import kotlinx.coroutines.flow.Flow

interface ListRepository {
    suspend fun getImage(query: String): Flow<ApiResult<NetworkResponse>>
}