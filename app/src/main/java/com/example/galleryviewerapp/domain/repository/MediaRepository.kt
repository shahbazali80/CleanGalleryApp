package com.example.galleryviewerapp.domain.repository

import com.example.galleryviewerapp.domain.model.MediaFile

interface MediaRepository {
    suspend fun getAllImages(): List<MediaFile>
    suspend fun getAllVideos(): List<MediaFile>
}

// fellows SRP because it fetch only media files
// fellows OCP because it allow to add more functions but not to modify
// 100% abstraction