package com.example.galleryviewerapp.data.model

import android.net.Uri

data class MediaEntity(
    val id: Long,
    val uri: Uri,
    val name: String,
    val type: String,
    val dateAdded: Long
)