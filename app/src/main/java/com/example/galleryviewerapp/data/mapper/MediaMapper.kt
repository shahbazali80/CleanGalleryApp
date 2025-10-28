package com.example.galleryviewerapp.data.mapper

import com.example.galleryviewerapp.data.model.MediaEntity
import com.example.galleryviewerapp.domain.model.MediaFile
import com.example.galleryviewerapp.domain.model.MediaType
import com.example.galleryviewerapp.presemtation.utils.Constants.IMAGE_FILE

fun MediaEntity.toDomain(): MediaFile {
    return MediaFile(
        id = id,
        uri = uri,
        name = name,
        type = if (type == IMAGE_FILE) MediaType.IMAGE else MediaType.VIDEO,
        dateAdded = dateAdded
    )
}