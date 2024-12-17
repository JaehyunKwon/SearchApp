package com.example.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class DocumentsDto(
    val collection: String,
    val thumbnail_url: String,
    val image_url: String,
    val width: Int,
    val height: Int,
    val display_sitename: String,
    val doc_url: String,
    val datetime: String
)
