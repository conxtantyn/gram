package com.gram.app.ui.gallery.content

import com.gram.app.ui.gallery.photos.PhotosListener
import com.gram.core.Interactor
import com.gram.gallery.model.Photo
import javax.inject.Inject

@ContentBuilder.Scope
class ContentInteractor @Inject constructor(
    private val contentRouter: ContentRouter
) : Interactor, PhotosListener {
    override fun onPhotoClicked(photo: Photo) {
        contentRouter.showPhoto(photo)
    }
}
