package com.gram.database.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.gram.database.Database
import com.gram.database.repository.PreferenceRepositoryDelegate
import com.gram.gallery.model.Location
import com.gram.gallery.model.Photo
import com.gram.gallery.model.Urls
import com.gram.gallery.repository.PhotoPreferenceRepository
import io.bloco.faker.Faker
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class PreferenceRepositoryDelegateInstrumentedTest {

    @get:Rule
    internal val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val faker = Faker()

    private lateinit var database: Database

    private lateinit var preferenceRepository: PhotoPreferenceRepository

    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())

        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            Database::class.java
        ).allowMainThreadQueries().build()

        preferenceRepository = PreferenceRepositoryDelegate(database.photoDao())
    }

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.gram.database.test", appContext.packageName)
    }

    @Test
    fun testCacheReadWrite() = runTest {
        val size = database.photoDao().getAll().size
        preferenceRepository.save(
            Photo(
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
        )
        assertEquals(size + 1, database.photoDao().getAll().size)
    }
}
