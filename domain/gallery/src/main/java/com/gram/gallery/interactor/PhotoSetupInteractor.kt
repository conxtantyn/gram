package com.gram.gallery.interactor

import com.gram.common.interactor.SuspendInteractor
import com.gram.gallery.repository.PhotoPreferenceRepository
import javax.inject.Inject

class PhotoSetupInteractor @Inject constructor(
    private val randomPhotoInteractor: RandomPhotoInteractor,
    private val photoPreferenceRepository: PhotoPreferenceRepository
) : SuspendInteractor<Unit> {
    override suspend fun invoke() {
        if (!photoPreferenceRepository.hasSession()) {
            randomPhotoInteractor()
        }
    }
}
