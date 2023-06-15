package com.gram.database.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.gram.database.domain.Photos

class UrlConverter {

    private val gson = Gson()

    @TypeConverter
    fun fromUrl(url: Photos.Urls): String {
        return gson.toJson(url)
    }

    @TypeConverter
    fun toUrl(urls: String): Photos.Urls {
        return gson.fromJson(urls, Photos.Urls::class.java)
    }
}
