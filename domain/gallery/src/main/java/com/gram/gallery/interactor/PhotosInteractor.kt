package com.gram.gallery.interactor

import com.gram.common.interactor.FlowInteractor
import com.gram.gallery.model.Photo
import com.gram.gallery.repository.PhotoPreferenceRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class PhotosInteractor @Inject constructor(
    private val photoPreferenceRepository: PhotoPreferenceRepository
) : FlowInteractor<List<Photo>> {
    override fun invoke(): StateFlow<List<Photo>> {
        return photoPreferenceRepository.getAll()
    }
}
