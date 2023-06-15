package com.gram.pixel

import com.gram.gallery.provider.PreferenceProvider
import com.gram.gallery.provider.RepositoryProvider
import com.gram.pixel.module.NetworkModule
import com.gram.pixel.module.RepositoryModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        NetworkModule::class,
        RepositoryModule::class
    ],
    dependencies = [ PreferenceProvider::class ]
)
interface PixelComponent : RepositoryProvider, PreferenceProvider
