package com.gram.app

import android.content.Context
import com.gram.app.ui.launcher.LauncherBuilder
import com.gram.core.Builder
import com.gram.core.component.ComponentBuilder
import com.gram.core.factory.BuilderFactory
import com.gram.gallery.provider.PreferenceProvider
import com.gram.gallery.provider.RepositoryProvider
import dagger.Provides
import dagger.android.AndroidInjector
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

class GramBuilder(
    private val parent: Parent,
) : ComponentBuilder<GramBuilder.Parent, GramBuilder.Component>() {

    override fun build(context: Context): Component {
        return DaggerGramBuilder_Component.builder().parent(parent).build()
    }

    interface Parent : PreferenceProvider, RepositoryProvider

    @dagger.Module(includes = [ Module.Provider::class ])
    internal abstract class Module {
        @dagger.Module
        object Provider {
            @Provides
            @IntoMap
            @ClassKey(LauncherBuilder::class)
            fun provideLauncherBuilder(
                component: Component
            ): Builder = LauncherBuilder(component)
        }
    }

    @Scope
    @dagger.Component(
        dependencies = [ Parent::class ],
        modules = [
            Module::class,
            BuilderFactory.Module::class,
        ]
    )
    interface Component :
        Parent,
        AndroidInjector<GramApplication>,
        BuilderFactory.Provider,
        LauncherBuilder.Parent

    @javax.inject.Scope
    @Retention(AnnotationRetention.RUNTIME)
    annotation class Scope
}
