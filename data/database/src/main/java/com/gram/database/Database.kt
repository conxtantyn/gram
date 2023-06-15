package com.gram.database

import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.gram.database.Database.Companion.VERSION
import com.gram.database.converter.LocationConverter
import com.gram.database.converter.UrlConverter
import com.gram.database.dao.PhotoDao
import com.gram.database.domain.Photos

@TypeConverters(
    value = [
        UrlConverter::class,
        LocationConverter::class,
    ]
)
@androidx.room.Database(
    entities = [ Photos::class ],
    version = VERSION
)
abstract class Database : RoomDatabase() {

    abstract fun photoDao(): PhotoDao

    companion object {
        const val VERSION = 1

        val NAME: String = Database::class.java.name
    }
}
