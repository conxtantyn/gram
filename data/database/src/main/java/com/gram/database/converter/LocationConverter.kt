package com.gram.database.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.gram.database.domain.Photos

class LocationConverter {

    private val gson = Gson()

    @TypeConverter
    fun fromUrl(location: Photos.Location): String {
        return gson.toJson(location)
    }

    @TypeConverter
    fun toUrl(location: String): Photos.Location {
        return gson.fromJson(location, Photos.Location::class.java)
    }
}
