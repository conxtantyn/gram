package com.gram.app.ui.gallery.photos

import com.gram.gallery.model.Photo

interface PhotosListener {
    fun onPhotoClicked(photo: Photo)
}
