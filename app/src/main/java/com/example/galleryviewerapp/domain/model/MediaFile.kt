package com.example.galleryviewerapp.domain.model

import android.net.Uri

data class MediaFile (
    val id: Long,
    val uri: Uri,
    val name: String,
    val type: MediaType,
    val dateAdded: Long
)

enum class MediaType { IMAGE, VIDEO }   // holds a fixed set of constant values. Each value is like an object, and cannot create new instances of them.