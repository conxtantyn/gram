package com.gram.pixel.repository

import com.gram.gallery.model.Photo
import com.gram.gallery.repository.PhotoPreferenceRepository
import com.gram.gallery.repository.PhotoRepository
import com.gram.pixel.model.Token
import com.gram.pixel.service.PhotoApiService
import io.bloco.faker.Faker
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.slot
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotSame
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PhotoRepositoryDelegateTest {

    @MockK
    internal lateinit var photoApiService: PhotoApiService

    @MockK
    internal lateinit var photoPreferenceRepository: PhotoPreferenceRepository

    private lateinit var repository: PhotoRepository

    private val faker = Faker()

    private var cache = mutableListOf<Photo>()

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)

        val photoSlut = slot<Photo>()
        coEvery { photoApiService.getPhoto(any()) } returns PhotoApiService.Photo(
            id = "text-id",
            width = faker.number.positive(),
            height = faker.number.positive(),
            color = faker.color.hexColor(),
            likes = faker.number.positive(),
            description = faker.lorem.character(),
            urls = PhotoApiService.Url(
                full = faker.placeholdit.image(),
                regular = faker.placeholdit.image()
            ),
            location = PhotoApiService.Location(
                name = faker.address.streetName(),
                city = faker.address.city(),
                country = faker.address.country(),
            )
        )
        coEvery { photoPreferenceRepository.save(capture(photoSlut)) } answers {
            cache.add(photoSlut.captured)
            cache.size.toLong()
        }

        repository = PhotoRepositoryDelegate(
            Token(faker.date.toString()),
            photoApiService,
            photoPreferenceRepository,
        )
    }

    @Test
    fun `test random photo generation success`() = runTest {
        val size = cache.size
        val response = repository.getRandomPhoto()

        assertNotSame(null, response)
        assertNotSame(cache.size, size)

        assertEquals(response, cache.last())
    }

    @Test
    fun `test random photo generation failure`() = runTest {
        coEvery { photoApiService.getPhoto(any()) } throws RuntimeException()

        val size = cache.size
        val response = try {
            repository.getRandomPhoto()
        } catch (error: Throwable) {
            null
        }

        assertEquals(null, response)
        assertEquals(cache.size, size)
    }
}
