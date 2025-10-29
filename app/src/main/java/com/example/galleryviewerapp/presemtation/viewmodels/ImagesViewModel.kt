package com.example.galleryviewerapp.presemtation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.galleryviewerapp.domain.usecase.GetAllImagesUseCase
import com.example.galleryviewerapp.presemtation.states.GalleryViewState
import com.example.galleryviewerapp.presemtation.utils.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImagesViewModel @Inject constructor(
    private val getAllImagesUseCase: GetAllImagesUseCase,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {
    private val _images = MutableStateFlow<GalleryViewState>(GalleryViewState.Loading)
    val images: StateFlow<GalleryViewState> = _images

    private val _selectedIndex = MutableStateFlow(0)
    val selectedIndex: StateFlow<Int> = _selectedIndex

    fun loadImages() {
        viewModelScope.launch(dispatcherProvider.io) {
            _images.emit(GalleryViewState.Loading)
            try {
                val list = getAllImagesUseCase()
                _images.emit(GalleryViewState.Success(list))
            } catch (e: Exception) {
                _images.emit(GalleryViewState.Error(e.message ?: "Error fetching images"))
            }
        }
    }

    fun saveSelectedImageIndex(index: Int) {
        _selectedIndex.value = index
    }
}

// fellows SRP because it fetch only images files

//next topic
//Coroutines