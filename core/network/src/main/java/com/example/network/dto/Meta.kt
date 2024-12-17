package com.example.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class Meta(
    val totalCount: Int,
    val pageableCount: Int,
    val isEnd: Boolean
)
