package com.gram.app.ui.gallery.photo

import android.content.Context
import com.gram.core.component.ComponentBuilderWithArgs
import dagger.BindsInstance
import dagger.android.AndroidInjector

class PhotoBuilder(
    private val parent: Parent
) : ComponentBuilderWithArgs<PhotoArgs, PhotoBuilder.Parent, PhotoBuilder.Component>() {

    override fun build(context: Context, args: PhotoArgs): Component {
        return DaggerPhotoBuilder_Component.builder()
            .parent(parent)
            .args(args)
            .build()
    }

    @dagger.Module
    internal abstract class Module

    interface Parent

    @Scope
    @dagger.Component(
        dependencies = [ Parent::class ],
        modules = [ Module::class ]
    )
    interface Component : Parent, AndroidInjector<PhotoFragment> {
        @dagger.Component.Builder
        interface Builder {
            fun parent(parent: Parent): Builder

            @BindsInstance
            fun args(args: PhotoArgs): Builder

            fun build(): Component
        }
    }

    @javax.inject.Scope
    @Retention(AnnotationRetention.RUNTIME)
    annotation class Scope
}
