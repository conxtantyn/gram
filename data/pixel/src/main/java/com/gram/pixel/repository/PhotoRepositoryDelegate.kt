package com.gram.pixel.repository

import com.gram.gallery.model.Location
import com.gram.gallery.model.Photo
import com.gram.gallery.model.Urls
import com.gram.gallery.repository.PhotoPreferenceRepository
import com.gram.gallery.repository.PhotoRepository
import com.gram.pixel.model.Token
import com.gram.pixel.service.PhotoApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class PhotoRepositoryDelegate @Inject constructor(
    private val token: Token,
    private val photoApiService: PhotoApiService,
    private val photoPreferenceRepository: PhotoPreferenceRepository
) : PhotoRepository {
    override suspend fun getRandomPhoto(): Photo {
        val response = photoApiService.getPhoto(token.id)
        val result = response.mapToDomain()

        photoPreferenceRepository.save(result)

        return result
    }

    private fun PhotoApiService.Photo.mapToDomain(): Photo {
        return Photo(
            id = id,
            width = width,
            height = height,
            color = color,
            likes = likes,
            description = description ?: UNAVAILABLE,
            urls = urls.mapToDomain(),
            location = location.mapToDomain(),
        )
    }

    private fun PhotoApiService.Url.mapToDomain(): Urls {
        return Urls(
            full = full,
            regular = regular,
        )
    }

    private fun PhotoApiService.Location.mapToDomain(): Location {
        return Location(
            name = name ?: UNKNOWN,
            city = city ?: UNKNOWN,
            country = country ?: UNKNOWN,
        )
    }

    private companion object {
        const val UNKNOWN = "Unknown"
        const val UNAVAILABLE = "Unavailable"
    }
}
