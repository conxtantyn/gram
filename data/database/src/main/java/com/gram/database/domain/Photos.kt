package com.gram.database.domain

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["uId"], unique = true)])
data class Photos(
    @PrimaryKey(autoGenerate = true) val id: Long?,
    val uId: String,
    val width: Int,
    val height: Int,
    val color: String,
    var likes: Int,
    val description: String,
    val urls: Urls,
    val location: Location,
) : java.io.Serializable {

    data class Urls(
        var header: String,
        val regular: String,
    )

    data class Location(
        val name: String,
        val city: String,
        val country: String
    )
}
