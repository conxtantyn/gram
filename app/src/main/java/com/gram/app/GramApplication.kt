package com.gram.app

import android.app.Application
import com.gram.core.component.ComponentProvider
import com.gram.database.DaggerDatabaseComponent
import com.gram.database.DatabaseComponent
import com.gram.database.module.DatabaseModule
import com.gram.pixel.DaggerPixelComponent
import com.gram.pixel.PixelComponent
import com.gram.pixel.module.NetworkModule

class GramApplication :
    Application(),
    GramBuilder.Parent,
    ComponentProvider<GramBuilder.Component> {

    private lateinit var databaseComponent: DatabaseComponent

    private lateinit var pixelComponent: PixelComponent

    private lateinit var component: GramBuilder.Component

    override fun requireComponent() = component

    override fun onCreate() {
        super.onCreate()
        databaseComponent = DaggerDatabaseComponent
            .builder()
            .databaseModule(DatabaseModule(this))
            .build()
        pixelComponent = DaggerPixelComponent
            .builder()
            .networkModule(NetworkModule(this))
            .preferenceProvider(databaseComponent)
            .build()
        component = GramBuilder(this).build(this)
    }

    override fun photoPreferenceRepository() = databaseComponent.photoPreferenceRepository()

    override fun photoRepository() = pixelComponent.photoRepository()
}
