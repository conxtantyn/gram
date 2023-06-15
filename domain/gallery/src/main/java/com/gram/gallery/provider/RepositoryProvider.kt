package com.gram.gallery.provider

import com.gram.gallery.repository.PhotoRepository

interface RepositoryProvider {
    fun photoRepository(): PhotoRepository
}
