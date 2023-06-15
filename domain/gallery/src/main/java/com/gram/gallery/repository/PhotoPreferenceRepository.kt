package com.gram.gallery.repository

import com.gram.gallery.model.Photo

interface PhotoPreferenceRepository : PreferenceRepository<Photo> {
    suspend fun hasSession(): Boolean = true
}
