package com.gram.database.module

import android.content.Context
import androidx.room.Room
import com.gram.database.Database
import dagger.Module
import dagger.Provides

@Module
class DatabaseModule(private val context: Context) {
    @Provides
    fun provideDatabase(): Database {
        return Room.databaseBuilder(context, Database::class.java, Database.NAME).build()
    }

    @Provides
    fun providePhotoDao(database: Database) = database.photoDao()
}
