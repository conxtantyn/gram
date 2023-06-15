package com.gram.database

import com.gram.database.module.DatabaseModule
import com.gram.database.module.RepositoryModule
import com.gram.gallery.provider.PreferenceProvider
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        DatabaseModule::class,
        RepositoryModule::class
    ]
)
interface DatabaseComponent : PreferenceProvider
