package com.gram.app.ui.welcome

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gram.app.GramComponent
import com.gram.app.ui.error.ErrorBuilder
import com.gram.app.ui.splash.SplashBuilder
import com.gram.app.viewmodel.PhotoViewModel
import com.gram.core.Builder
import com.gram.core.ViewModelKey
import com.gram.core.component.ComponentBuilder
import com.gram.core.factory.BuilderFactory
import com.gram.core.factory.ViewModelFactory
import dagger.Binds
import dagger.BindsInstance
import dagger.Provides
import dagger.android.AndroidInjector
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

class WelcomeBuilder(
    private val parent: Parent
) : ComponentBuilder<WelcomeBuilder.Parent, WelcomeBuilder.Component>() {

    override fun build(context: Context): Component {
        return DaggerWelcomeBuilder_Component
            .builder()
            .parent(parent)
            .router(WelcomeRouterDelegate())
            .build()
    }

    @dagger.Module(includes = [ Module.Provider::class ])
    internal abstract class Module {
        @Binds
        @Scope
        internal abstract fun factory(factory: ViewModelFactory): ViewModelProvider.Factory

        @Binds
        @Scope
        @IntoMap
        @ViewModelKey(PhotoViewModel::class)
        internal abstract fun viewModel(viewModel: PhotoViewModel): ViewModel

        @dagger.Module
        object Provider {
            @Provides
            @IntoMap
            @ClassKey(SplashBuilder::class)
            fun provideSplashBuilder(
                component: Component
            ): Builder = SplashBuilder(component)

            @Provides
            @IntoMap
            @ClassKey(ErrorBuilder::class)
            fun provideErrorBuilder(
                component: Component
            ): Builder = ErrorBuilder(component)
        }
    }

    interface Parent : GramComponent

    @Scope
    @dagger.Component(
        dependencies = [ Parent::class ],
        modules = [
            Module::class,
            BuilderFactory.Module::class
        ]
    )
    interface Component :
        Parent,
        AndroidInjector<WelcomeFragment>,
        BuilderFactory.Provider,
        SplashBuilder.Parent,
        ErrorBuilder.Parent {
        @dagger.Component.Builder
        interface Builder {
            fun parent(parent: Parent): Builder

            @BindsInstance
            fun router(router: WelcomeRouter): Builder

            fun build(): Component
        }
    }

    @javax.inject.Scope
    @Retention(AnnotationRetention.RUNTIME)
    annotation class Scope
}
