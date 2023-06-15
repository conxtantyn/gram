package com.gram.app

import com.gram.gallery.repository.PhotoPreferenceRepository
import com.gram.gallery.repository.PhotoRepository
import org.mockito.Mockito

abstract class MockComponent : GramComponent {

    private val preferenceRepository: PhotoPreferenceRepository by lazy {
        Mockito.mock(PhotoPreferenceRepository::class.java)
    }

    private val photoRepository: PhotoRepository by lazy {
        Mockito.mock(PhotoRepository::class.java)
    }

    override fun photoPreferenceRepository() = preferenceRepository

    override fun photoRepository() = photoRepository
}
