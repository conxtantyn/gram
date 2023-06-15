package com.gram.database.module

import com.gram.database.repository.PreferenceRepositoryDelegate
import com.gram.gallery.repository.PhotoPreferenceRepository
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {
    @Binds
    internal abstract fun photoPreferenceRepository(
        repository: PreferenceRepositoryDelegate
    ): PhotoPreferenceRepository
}
