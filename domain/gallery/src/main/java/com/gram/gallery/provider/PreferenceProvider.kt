package com.gram.gallery.provider

import com.gram.gallery.repository.PhotoPreferenceRepository

interface PreferenceProvider {
    fun photoPreferenceRepository(): PhotoPreferenceRepository
}
