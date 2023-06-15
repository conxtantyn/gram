package com.gram.app.ui.gallery.content

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.gram.app.MockComponent
import com.gram.app.R
import com.gram.app.util.mockPhoto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4ClassRunner::class)
class TestContentFragmentSmoker : MockComponent(), ContentBuilder.Parent {

    @get:Rule
    internal val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val router = mock(ContentRouter::class.java)

    private val cache = mutableListOf(mockPhoto)

    private val stateFlow = MutableStateFlow(cache)

    private lateinit var scenario: FragmentScenario<TestContentFragment>

    @Before
    fun setup() = runTest {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        `when`(photoPreferenceRepository().hasSession()).thenReturn(true)
        `when`(photoPreferenceRepository().getAll()).thenReturn(stateFlow)
        scenario = launchFragmentInContainer(
            themeResId = R.style.Theme_Gram,
        ) {
            TestContentFragment(router, this@TestContentFragmentSmoker)
        }
    }

    @Test
    fun testPhotoClicked() = runTest {
        scenario.onFragment {
            it.requireComponent().photoListener().onPhotoClicked(cache.first())
        }
        verify(router).showPhoto(cache.first())
    }

    @Test
    fun testFabIconClickedWhenPhotoIsDisplayed() = runTest {
        `when`(router.isPhotoActive()).thenReturn(true)
        onView(withId(R.id.fabButton)).perform(click())

        verify(router).dismissPhoto()
    }

    @Test
    fun testFabIconClickedWhenPhotoIsNotDisplayed() = runTest {
        `when`(router.isPhotoActive()).thenReturn(false)
        onView(withId(R.id.fabButton)).perform(click())

        verify(photoRepository()).getRandomPhoto()
        verify(router, times(0)).dismissPhoto()
    }
}
