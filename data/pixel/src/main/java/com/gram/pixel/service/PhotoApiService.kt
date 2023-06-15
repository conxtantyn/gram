package com.gram.pixel.service

import retrofit2.http.GET
import retrofit2.http.Query

interface PhotoApiService {
    @GET("photos/random/")
    suspend fun getPhoto(
        @Query("client_id") key: String,
    ): Photo

    data class Photo(
        var id: String,
        var width: Int,
        var height: Int,
        var color: String,
        var likes: Int,
        var description: String?,
        var urls: Url,
        var location: Location,
    )

    data class Url(
        var full: String,
        var regular: String,
    )

    data class Location(
        val name: String?,
        val city: String?,
        val country: String?
    )
}
