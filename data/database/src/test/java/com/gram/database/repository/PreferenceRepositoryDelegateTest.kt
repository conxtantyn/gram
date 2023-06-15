package com.gram.database.repository

import com.gram.database.dao.PhotoDao
import com.gram.database.domain.Photos
import com.gram.gallery.model.Location
import com.gram.gallery.model.Photo
import com.gram.gallery.model.Urls
import com.gram.gallery.repository.PhotoPreferenceRepository
import io.bloco.faker.Faker
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.slot
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotSame
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PreferenceRepositoryDelegateTest {

    @MockK
    internal lateinit var photoDao: PhotoDao

    private lateinit var repository: PhotoPreferenceRepository

    private val faker = Faker()

    private val cache = mutableListOf<Photos>()

    private val mockPhoto = Photo(
        id = "${System.currentTimeMillis()}",
        width = faker.number.positive(),
        height = faker.number.positive(),
        color = faker.color.hexColor(),
        likes = faker.number.positive(),
        description = faker.lorem.character(),
        urls = Urls(
            full = faker.placeholdit.image(),
            regular = faker.placeholdit.image()
        ),
        location = Location(
            name = faker.address.streetName(),
            city = faker.address.city(),
            country = faker.address.country(),
        )
    )

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)

        mock()

        repository = PreferenceRepositoryDelegate(photoDao)
    }

    private fun mock() {
        val photoSlut = slot<Photos>()
        coEvery { photoDao.save(capture(photoSlut)) } answers {
            cache.add(photoSlut.captured)
            cache.size.toLong()
        }
        coEvery { photoDao.getAll() } returns cache
    }

    @Test
    fun `test save photo success`() = runTest {
        val size = repository.save(mockPhoto)
        val result = repository.getAll().firstOrNull()

        assertEquals(result, listOf(mockPhoto))
        assertNotSame(size, photoDao.getAll().size)
    }

    @Test
    fun `test save photo failure`() = runTest {
        val size = photoDao.getAll().size
        coEvery { photoDao.save(any()) } throws RuntimeException()

        try {
            repository.save(mockPhoto)
        } catch (_: Throwable) { }

        assertEquals(size, photoDao.getAll().size)
    }
}
