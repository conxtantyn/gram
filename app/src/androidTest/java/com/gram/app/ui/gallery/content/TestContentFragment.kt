package com.gram.app.ui.gallery.content

class TestContentFragment(
    private val mainRouter: ContentRouter,
    private val parent: ContentBuilder.Parent,
) : ContentFragment() {
    override fun requireComponent(): ContentBuilder.Component {
        return DaggerContentBuilder_Component.builder()
            .router(mainRouter)
            .parent(parent)
            .build()
    }
}
