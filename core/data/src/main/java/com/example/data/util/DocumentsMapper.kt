package com.example.data.util

import com.example.data.entity.DocumentsEntity
import com.example.network.dto.DocumentsDto

object DocumentsMapper {
    fun dtoToEntity(dto: DocumentsDto): DocumentsEntity {
        return DocumentsEntity(
            doc_url = dto.doc_url,
            collection = dto.collection,
            thumbnail_url = dto.thumbnail_url,
            datetime = dto.datetime,
            width = dto.width,
            height = dto.height,
            image_url = dto.image_url,
            display_sitename = dto.display_sitename
        )
    }

    fun entityToDto(entity: DocumentsEntity): DocumentsDto {
        return DocumentsDto(
            doc_url = entity.doc_url,
            collection = entity.collection,
            thumbnail_url = entity.thumbnail_url,
            datetime = entity.datetime,
            width = entity.width,
            height = entity.height,
            image_url = entity.image_url,
            display_sitename = entity.display_sitename
        )
    }
}