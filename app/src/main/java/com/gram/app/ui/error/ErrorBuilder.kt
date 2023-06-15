package com.gram.app.ui.error

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gram.app.GramComponent
import com.gram.app.viewmodel.PhotoViewModel
import com.gram.core.ViewModelKey
import com.gram.core.component.ComponentBuilder
import com.gram.core.factory.ViewModelFactory
import dagger.Binds
import dagger.android.AndroidInjector
import dagger.multibindings.IntoMap

class ErrorBuilder(
    private val parent: Parent
) : ComponentBuilder<ErrorBuilder.Parent, ErrorBuilder.Component>() {

    override fun build(context: Context): Component {
        return DaggerErrorBuilder_Component
            .builder()
            .parent(parent)
            .build()
    }

    @dagger.Module
    internal abstract class Module {
        @Binds
        @Scope
        internal abstract fun factory(factory: ViewModelFactory): ViewModelProvider.Factory

        @Binds
        @Scope
        @IntoMap
        @ViewModelKey(PhotoViewModel::class)
        internal abstract fun viewModel(viewModel: PhotoViewModel): ViewModel
    }

    interface Parent : GramComponent

    @Scope
    @dagger.Component(
        dependencies = [ Parent::class ],
        modules = [ Module::class ]
    )
    interface Component : Parent, AndroidInjector<ErrorFragment>

    @javax.inject.Scope
    @Retention(AnnotationRetention.RUNTIME)
    annotation class Scope
}
