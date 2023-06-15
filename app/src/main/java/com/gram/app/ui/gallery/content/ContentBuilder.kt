package com.gram.app.ui.gallery.content

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gram.app.GramComponent
import com.gram.app.ui.gallery.photo.PhotoBuilder
import com.gram.app.ui.gallery.photos.PhotosBuilder
import com.gram.app.ui.gallery.photos.PhotosListener
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

class ContentBuilder(
    private val parent: Parent
) : ComponentBuilder<ContentBuilder.Parent, ContentBuilder.Component>() {

    override fun build(context: Context): Component {
        return DaggerContentBuilder_Component.builder()
            .parent(parent)
            .router(ContentRouterDelegate())
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

        @Binds
        @Scope
        internal abstract fun photoListener(router: ContentInteractor): PhotosListener

        @dagger.Module
        internal object Provider {
            @Provides
            @IntoMap
            @ClassKey(PhotoBuilder::class)
            fun providePhotoBuilder(
                component: Component
            ): Builder = PhotoBuilder(component)

            @Provides
            @IntoMap
            @ClassKey(PhotosBuilder::class)
            fun providePhotosBuilder(
                component: Component
            ): Builder = PhotosBuilder(component)
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
        AndroidInjector<ContentFragment>,
        BuilderFactory.Provider,
        PhotoBuilder.Parent,
        PhotosBuilder.Parent {
        @dagger.Component.Builder
        interface Builder {
            fun parent(parent: Parent): Builder

            @BindsInstance
            fun router(router: ContentRouter): Builder

            fun build(): Component
        }
        fun listener(): PhotosListener
    }

    @javax.inject.Scope
    @Retention(AnnotationRetention.RUNTIME)
    annotation class Scope
}
