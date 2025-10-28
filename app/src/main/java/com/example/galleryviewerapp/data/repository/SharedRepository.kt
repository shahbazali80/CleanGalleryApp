package com.example.galleryviewerapp.data.repository

import com.example.galleryviewerapp.domain.model.MediaFile

object SharedRepository {
    var mMediaFile: MediaFile? = null

    fun clearMediaFile() {
        mMediaFile = null
    }
}