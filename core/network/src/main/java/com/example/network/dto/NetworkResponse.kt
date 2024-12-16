package com.example.network.dto

import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Keep
data class NetworkResponse(
    @SerialName("meta") val meta: Meta,
    @SerialName("documents") val documents: List<Documents>
)

@Serializable
@Keep
data class Meta(
    @SerialName("total_count") val totalCount: Int,
    @SerialName("pageable_count") val pageableCount: Int,
    @SerialName("is_end") val isEnd: Boolean
)

@Serializable
@Keep
data class Documents(
    @SerialName("collection") val collection: String,
    @SerialName("thumbnail_url") val thumbnailUrl: String,
    @SerialName("image_url") val imageUrl: String,
    @SerialName("width") val width: Int,
    @SerialName("height") val height: Int,
    @SerialName("display_sitename") val displaySitename: String,
    @SerialName("doc_url") val docUrl: String,
    @SerialName("datetime") val datetime: String
)