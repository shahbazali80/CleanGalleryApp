package com.example.galleryviewerapp.data.repository

import com.example.galleryviewerapp.domain.model.MediaFile
import com.example.galleryviewerapp.presemtation.utils.showLog

object SharedRepository {
    var mMediaFile: MediaFile? = null

    var imageList: List<MediaFile>? = null
    var videoList: List<MediaFile>? = null

    var selectedImageIndex: Int = 0
    var selectedVideoIndex: Int = 0

    fun saveImageFiles(list: List<MediaFile>) {
        imageList = list
        showLog(message = imageList!!.size)
    }

    fun saveVideoFiles(list: List<MediaFile>) {
        videoList = list
        showLog(message = videoList!!.size)
    }

    fun saveSelectedImageIndex(index: Int) {
        selectedImageIndex = index
    }

    fun saveSelectedVideoIndex(index: Int) {
        selectedVideoIndex = index
    }

    fun clear() {
        mMediaFile = null
        selectedImageIndex = 0
        selectedVideoIndex = 0
    }
}