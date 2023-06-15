package com.gram.app.ui.launcher

import android.content.Context
import com.gram.app.GramComponent
import com.gram.app.ui.main.MainBuilder
import com.gram.core.Builder
import com.gram.core.component.ComponentBuilder
import com.gram.core.factory.BuilderFactory
import dagger.Provides
import dagger.android.AndroidInjector
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

class LauncherBuilder(
    private val parent: Parent,
) : ComponentBuilder<LauncherBuilder.Parent, LauncherBuilder.Component>() {

    override fun build(context: Context): Component {
        return DaggerLauncherBuilder_Component.builder().parent(parent).build()
    }

    @dagger.Module(includes = [ Module.Provider::class ])
    internal abstract class Module {
        @dagger.Module
        object Provider {
            @Provides
            @IntoMap
            @ClassKey(MainBuilder::class)
            fun provideMainBuilder(
                component: Component
            ): Builder = MainBuilder(component)
        }
    }

    interface Parent : GramComponent

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
        AndroidInjector<LauncherActivity>,
        BuilderFactory.Provider,
        MainBuilder.Parent

    @javax.inject.Scope
    @Retention(AnnotationRetention.RUNTIME)
    annotation class Scope
}
