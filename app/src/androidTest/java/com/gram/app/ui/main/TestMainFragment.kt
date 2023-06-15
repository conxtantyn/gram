package com.gram.app.ui.main

class TestMainFragment(
    private val mainRouter: MainRouter,
    private val parent: MainBuilder.Parent,
) : MainFragment() {
    override fun requireComponent(): MainBuilder.Component {
        return DaggerMainBuilder_Component
            .builder()
            .router(mainRouter)
            .parent(parent)
            .build()
    }

    internal fun getPhoto() = viewModel()
}
