package com.example.galleryviewerapp.presemtation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.galleryviewerapp.data.repository.SharedRepository
import com.example.galleryviewerapp.domain.usecase.GetAllVideosUseCase
import com.example.galleryviewerapp.presemtation.states.GalleryViewState
import com.example.galleryviewerapp.presemtation.utils.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideosViewModel @Inject constructor(
    private val getAllVideosUseCase: GetAllVideosUseCase,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {
    private val _videos = MutableStateFlow<GalleryViewState>(GalleryViewState.Loading)
    val videos: StateFlow<GalleryViewState> = _videos

    fun loadVideos() {
        viewModelScope.launch(dispatcherProvider.io) {
            _videos.emit(GalleryViewState.Loading)
            try {
                val list = getAllVideosUseCase()
                SharedRepository.saveVideoFiles(list)
                _videos.emit(GalleryViewState.Success(list))
            } catch (e: Exception) {
                _videos.emit(GalleryViewState.Error(e.message ?: "Error fetching videos"))
            }
        }
    }
}

// fellows SRP because it fetch only video files