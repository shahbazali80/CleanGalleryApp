package com.example.galleryviewerapp.domain.usecase

import com.example.galleryviewerapp.domain.repository.MediaRepository
import javax.inject.Inject

class GetAllImagesUseCase @Inject constructor(
    private val repository: MediaRepository
) {
    suspend operator fun invoke() = repository.getAllImages()

}