package com.example.galleryviewerapp.presemtation.states

import com.example.galleryviewerapp.domain.model.MediaFile

sealed class GalleryViewState {
    object Loading : GalleryViewState()
    data class Success(val data: List<MediaFile>) : GalleryViewState()
    data class Error(val message: String) : GalleryViewState()
}
