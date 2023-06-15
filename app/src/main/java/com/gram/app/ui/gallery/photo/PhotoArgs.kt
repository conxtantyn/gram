package com.gram.app.ui.gallery.photo

import android.os.Parcelable
import androidx.navigation.Navigator
import kotlinx.parcelize.Parcelize

@Parcelize
data class PhotoArgs(
    var id: String,
    var width: Int,
    var height: Int,
    var color: String,
    var likes: Int,
    var description: String,
    var urls: Urls,
    var location: Location,
) : Parcelable, Navigator.Extras {

    @Parcelize
    data class Urls(
        var full: String,
        var regular: String,
    ) : Parcelable

    @Parcelize
    data class Location(
        val name: String,
        val city: String,
        val country: String
    ) : Parcelable
}
