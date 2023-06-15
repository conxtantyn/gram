package com.gram.gallery.repository

import com.gram.gallery.model.Photo

interface PhotoRepository {
    suspend fun getRandomPhoto(): Photo
}
