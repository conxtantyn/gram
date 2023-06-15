package com.gram.database.repository

import com.gram.database.dao.PhotoDao
import com.gram.database.domain.Photos
import com.gram.gallery.model.Location
import com.gram.gallery.model.Photo
import com.gram.gallery.model.Urls
import com.gram.gallery.repository.PhotoPreferenceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class PreferenceRepositoryDelegate @Inject constructor(
    private val photoDao: PhotoDao,
) : PhotoPreferenceRepository {

    private val state = MutableStateFlow<List<Photo>>(emptyList())

    override suspend fun hasSession(): Boolean {
        val result = photoDao.getAll().mapToDomain()
        state.tryEmit(result)
        return result.isNotEmpty()
    }

    override fun getAll(): StateFlow<List<Photo>> = state

    override suspend fun save(entity: Photo): Long {
        val photo = entity.mapToDomain()
        val id = photoDao.save(photo)

        state.tryEmit(photoDao.getAll().mapToDomain())

        return id
    }

    private fun Photo.mapToDomain(): Photos {
        return Photos(
            id = null,
            uId = id,
            width = width,
            height = height,
            color = color,
            likes = likes,
            description = description,
            urls = mapToUrlDomain(),
            location = mapToLocationDomain()
        )
    }

    private fun Photo.mapToUrlDomain(): Photos.Urls {
        return Photos.Urls(
            header = urls.full,
            regular = urls.regular,
        )
    }

    private fun Photo.mapToLocationDomain(): Photos.Location {
        return Photos.Location(
            name = location.name,
            city = location.city,
            country = location.country,
        )
    }

    private fun Photos.Urls.mapToDomain(): Urls {
        return Urls(
            full = header,
            regular = regular,
        )
    }

    private fun Photos.Location.mapToDomain(): Location {
        return Location(
            name = name,
            city = city,
            country = country,
        )
    }

    private fun List<Photos>.mapToDomain(): List<Photo> {
        return map {
            Photo(
                id = it.uId,
                width = it.width,
                height = it.height,
                color = it.color,
                likes = it.likes,
                description = it.description,
                urls = it.urls.mapToDomain(),
                location = it.location.mapToDomain(),
            )
        }
    }
}
