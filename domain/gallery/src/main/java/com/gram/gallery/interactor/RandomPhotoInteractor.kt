package com.gram.gallery.interactor

import com.gram.common.interactor.SuspendInteractor
import com.gram.gallery.model.Photo
import com.gram.gallery.repository.PhotoRepository
import javax.inject.Inject

class RandomPhotoInteractor @Inject constructor(
    private val photoRepository: PhotoRepository
) : SuspendInteractor<Photo> {
    override suspend fun invoke(): Photo {
        return photoRepository.getRandomPhoto()
    }
}
