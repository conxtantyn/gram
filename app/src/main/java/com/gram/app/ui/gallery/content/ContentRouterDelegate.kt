package com.gram.app.ui.gallery.content

import androidx.navigation.NavController
import androidx.navigation.createGraph
import androidx.navigation.fragment.fragment
import com.gram.app.R
import com.gram.app.extention.map
import com.gram.app.ui.gallery.photo.PhotoFragment
import com.gram.app.ui.gallery.photo.mapToDomain
import com.gram.app.ui.gallery.photos.PhotosFragment
import com.gram.core.android.navigate
import com.gram.gallery.model.Photo
import javax.inject.Inject

@ContentBuilder.Scope
class ContentRouterDelegate @Inject constructor() : ContentRouter {

    private lateinit var controller: NavController

    private val photoRoute = R.id.photoFragment.toString()

    private val photosRoute = R.id.photosFragment.toString()

    override fun onAttach(vararg controllerArgs: NavController) {
        controllerArgs.map { controller ->
            this.controller = controller
            controller.graph = controller.createGraph(
                startDestination = photosRoute
            ) {
                fragment<PhotosFragment>(photosRoute)
                fragment<PhotoFragment>(photoRoute)
            }
        }
    }

    override fun isPhotoActive(): Boolean {
        return controller.currentDestination?.route == photoRoute
    }

    override fun dismissPhoto() {
        controller.popBackStack()
    }

    override fun showPhoto(photo: Photo) {
        controller.navigate(
            photoRoute,
            photo.mapToDomain()
        )
    }
}
