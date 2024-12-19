package com.example.network.repository

import android.util.Log
import com.example.network.api.ApiService
import com.example.network.dto.NetworkResponse
import com.example.network.util.ApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ListRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : ListRepository {
    override suspend fun getImage(query: String): Flow<ApiResult<NetworkResponse>> = flow {
        try {
            val response = apiService.getImage("KakaoAK 749cd329d09242d9ca2870a97eacccd9", query)
            if (response.isSuccessful) {
                // 성공적으로 데이터를 반환
                response.body()?.let { data ->
                    emit(ApiResult.Success(data))
                } ?: emit(ApiResult.Error("Empty Response Body"))
            } else {
                // 서버 오류 처리
                emit(ApiResult.Error("Error Code: ${response.code()}"))
            }
        } catch (e: Exception) {
            Log.e("TAG", "getImage: $e")
            emit(ApiResult.NetworkError(e)) // 네트워크 오류 처리
        }
    }
}