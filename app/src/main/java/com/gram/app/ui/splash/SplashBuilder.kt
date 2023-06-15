package com.gram.app.ui.splash

import android.content.Context
import com.gram.core.component.ComponentBuilder
import dagger.android.AndroidInjector

class SplashBuilder(
    private val parent: Parent
) : ComponentBuilder<SplashBuilder.Parent, SplashBuilder.Component>() {

    override fun build(context: Context): Component {
        return DaggerSplashBuilder_Component.builder()
            .parent(parent)
            .build()
    }

    @dagger.Module
    internal abstract class Module

    interface Parent

    @dagger.Component(
        dependencies = [ Parent::class ],
        modules = [ Module::class ]
    )
    interface Component : Parent, AndroidInjector<SplashFragment>
}
