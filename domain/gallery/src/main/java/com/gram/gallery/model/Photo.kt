package com.gram.gallery.model

data class Photo(
    var id: String,
    var width: Int,
    var height: Int,
    var color: String,
    var likes: Int,
    var description: String,
    var urls: Urls,
    var location: Location,
) : java.io.Serializable
