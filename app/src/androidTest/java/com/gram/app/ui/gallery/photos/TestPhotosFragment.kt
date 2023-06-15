package com.gram.app.ui.gallery.photos

class TestPhotosFragment(
    private val parent: PhotosBuilder.Parent
) : PhotosFragment() {
    override fun requireComponent(): PhotosBuilder.Component {
        return DaggerPhotosBuilder_Component
            .builder()
            .parent(parent)
            .build()
    }
}
