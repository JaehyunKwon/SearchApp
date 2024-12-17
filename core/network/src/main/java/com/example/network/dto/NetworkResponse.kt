package com.example.network.dto

import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Keep
data class NetworkResponse(
    @SerialName("meta") val meta: Meta,
    @SerialName("documents") val documents: List<DocumentsDto>
)