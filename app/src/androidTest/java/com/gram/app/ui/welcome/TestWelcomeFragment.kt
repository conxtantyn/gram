package com.gram.app.ui.welcome

class TestWelcomeFragment(
    private val mainRouter: WelcomeRouter,
    private val parent: WelcomeBuilder.Parent,
) : WelcomeFragment() {
    override fun requireComponent(): WelcomeBuilder.Component {
        return DaggerWelcomeBuilder_Component
            .builder()
            .router(mainRouter)
            .parent(parent)
            .build()
    }

    internal fun getPhoto() = viewModel()
}
