package com.gram.app.ui.error

class TestErrorFragment(
    private val parent: ErrorBuilder.Parent
) : ErrorFragment() {
    override fun requireComponent(): ErrorBuilder.Component {
        return DaggerErrorBuilder_Component
            .builder()
            .parent(parent)
            .build()
    }
}
