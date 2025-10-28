package com.example.galleryviewerapp.data.repository

import com.example.galleryviewerapp.data.datasource.MediaDataSource
import com.example.galleryviewerapp.data.mapper.toDomain
import com.example.galleryviewerapp.domain.model.MediaFile
import com.example.galleryviewerapp.domain.repository.MediaRepository
import com.example.galleryviewerapp.presemtation.utils.Constants.IMAGE_FILE
import com.example.galleryviewerapp.presemtation.utils.Constants.VIDEO_FILE
import javax.inject.Inject

class MediaRepositoryImp @Inject constructor(
    private val dataSource: MediaDataSource
) : MediaRepository {
    override suspend fun getAllImages(): List<MediaFile> {
        return dataSource.fetchMedia(IMAGE_FILE).map {
            it.toDomain()
        }
    }

    override suspend fun getAllVideos(): List<MediaFile> {
        return dataSource.fetchMedia(VIDEO_FILE).map {
            it.toDomain()
        }
    }
}