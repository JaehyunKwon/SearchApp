package com.example.network.api

import com.example.network.dto.Documents
import com.example.network.dto.NetworkResponse
import com.example.network.util.ApiResult
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiService {
    @GET("v2/search/image")
    suspend fun getImage(
        @Header("Authorization") authorization: String,
        @Query("query") query: String
    ): ApiResult<NetworkResponse>
}