package com.gram.core.android

import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import com.google.gson.Gson
import kotlin.reflect.KClass

class NavigationArgs<T : Parcelable>(
    private val clazz: KClass<T>,
) : NavType<T>(false) {

    private val gson = Gson()

    override fun get(bundle: Bundle, key: String): T? {
        return bundle.getParcelable(key)
    }

    override fun parseValue(value: String): T {
        return gson.fromJson(value, clazz.java)
    }

    override fun put(bundle: Bundle, key: String, value: T) {
        bundle.putParcelable(key, value)
    }
}
