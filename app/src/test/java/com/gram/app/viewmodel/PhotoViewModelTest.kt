package com.gram.app.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.gram.gallery.interactor.PhotoSetupInteractor
import com.gram.gallery.interactor.PhotosInteractor
import com.gram.gallery.interactor.RandomPhotoInteractor
import com.gram.gallery.model.Location
import com.gram.gallery.model.Photo
import com.gram.gallery.model.Urls
import io.bloco.faker.Faker
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PhotoViewModelTest {

    @get:Rule
    internal val instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    internal lateinit var photosInteractor: PhotosInteractor

    @MockK
    internal lateinit var randomPhotoInteractor: RandomPhotoInteractor

    @MockK
    internal lateinit var photoSetupInteractor: PhotoSetupInteractor

    @MockK
    internal lateinit var observer: Observer<PhotoViewModel.State>

    private lateinit var viewModel: PhotoViewModel

    private val faker = Faker()

    private val cache = mutableListOf<Photo>()

    private val stateFlow = MutableStateFlow(emptyList<Photo>())

    private val initialState = PhotoViewModel.State(
        initialize = true,
        loading = false,
        photos = emptyList(),
        error = null
    )

    @Before
    internal fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        MockKAnnotations.init(this, relaxed = true)

        mock()

        viewModel = PhotoViewModel(photosInteractor, photoSetupInteractor, randomPhotoInteractor)

        viewModel.state.observeForever(observer)
    }

    private fun mock() {
        coEvery { photosInteractor.invoke() } returns stateFlow
        coEvery { randomPhotoInteractor.invoke() } answers {
            cache.add(
                Photo(
                    id = "${System.currentTimeMillis()}",
                    width = faker.number.positive(),
                    height = faker.number.positive(),
                    color = faker.color.hexColor(),
                    description = faker.lorem.character(),
                    likes = faker.number.positive(),
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
            stateFlow.tryEmit(cache)
            cache.last()
        }
    }

    @Test
    fun `test that state has observer`() = runTest {
        viewModel()

        assertTrue(viewModel.state.hasObservers())
    }

    @Test
    fun `test success state`() = runTest {
        viewModel()

        verify {
            observer.onChanged(
                PhotoViewModel.State(
                    initialize = false,
                    loading = false,
                    photos = cache,
                    error = null
                )
            )
        }
    }

    @Test
    fun `test loading state while initializing`() = runTest {
        coEvery { randomPhotoInteractor.invoke() } throws RuntimeException()

        viewModel()

        verify { observer.onChanged(initialState.withLoader(true)) }

        assertTrue(viewModel.state.value?.initialize == true)
    }

    @Test
    fun `test loading state while initialized`() = runTest {
        viewModel()

        verify { observer.onChanged(initialState.withLoader(true)) }

        assertTrue(viewModel.state.value?.initialize == false)
    }

    @Test
    fun `test error state`() = runTest {
        val error = RuntimeException()

        coEvery { randomPhotoInteractor.invoke() } throws error

        viewModel()

        verify { observer.onChanged(initialState.withError(error)) }
    }
}
