package com.gram.pixel.module

import com.gram.gallery.repository.PhotoRepository
import com.gram.pixel.repository.PhotoRepositoryDelegate
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {
    @Binds
    internal abstract fun providePhotoRepository(
        repository: PhotoRepositoryDelegate,
    ): PhotoRepository
}
