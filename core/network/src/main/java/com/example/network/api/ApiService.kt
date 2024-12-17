package com.example.network.api

import com.example.network.dto.NetworkResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiService {
    @GET("v2/search/image")
    suspend fun getImage(
        @Header("Authorization") authorization: String,
        @Query("query") query: String
    ): Response<NetworkResponse>
}