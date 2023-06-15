package com.gram.app.ui.gallery.content

import com.gram.core.Router
import com.gram.gallery.model.Photo

interface ContentRouter : Router {
    fun isPhotoActive(): Boolean

    fun dismissPhoto()

    fun showPhoto(photo: Photo)
}
