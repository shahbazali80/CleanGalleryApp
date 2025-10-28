package com.example.galleryviewerapp.di

import android.content.Context
import com.example.galleryviewerapp.data.datasource.MediaDataSource
import com.example.galleryviewerapp.data.repository.MediaRepositoryImp
import com.example.galleryviewerapp.domain.repository.MediaRepository
import com.example.galleryviewerapp.domain.usecase.GetAllImagesUseCase
import com.example.galleryviewerapp.domain.usecase.GetAllVideosUseCase
import com.example.galleryviewerapp.presemtation.utils.DefaultDispatcherProvider
import com.example.galleryviewerapp.presemtation.utils.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideMediaDataSource(@ApplicationContext context: Context) = MediaDataSource(context)

    @Provides
    @Singleton
    fun provideMediaRepository(
        dataSource: MediaDataSource
    ): MediaRepository = MediaRepositoryImp(dataSource)

    @Provides
    fun provideDispatcherProvider(): DispatcherProvider = DefaultDispatcherProvider()

    @Provides
    fun provideGetAllImagesUseCase(repository: MediaRepository) = GetAllImagesUseCase(repository)

    @Provides
    fun provideGetAllVideosUseCase(repository: MediaRepository) = GetAllVideosUseCase(repository)
}