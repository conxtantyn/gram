package com.gram.app.ui.gallery.photos

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.gram.app.MockComponent
import com.gram.app.R
import com.gram.app.util.mockPhoto
import com.gram.app.util.recyclerViewItemSizeMatcher
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
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4ClassRunner::class)
class TestPhotosFragmentSmoker : MockComponent(), PhotosBuilder.Parent {

    @get:Rule
    internal val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val listener: PhotosListener = mock(PhotosListener::class.java)

    private lateinit var scenario: FragmentScenario<TestPhotosFragment>

    private val stateFlow = MutableStateFlow(listOf(mockPhoto))

    @Before
    fun setup() = runTest {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        `when`(photoPreferenceRepository().hasSession()).thenReturn(true)
        `when`(photoPreferenceRepository().getAll()).thenReturn(stateFlow)
        scenario = launchFragmentInContainer(
            themeResId = R.style.Theme_Gram,
        ) {
            TestPhotosFragment(this@TestPhotosFragmentSmoker)
        }
    }

    @Test
    fun testListDisplayed() = runTest {
        onView(withId(R.id.photosFragment))
            .check(matches(recyclerViewItemSizeMatcher(1)))
    }

    @Test
    fun testListItemClicked() = runTest {
        onView(withId(R.id.photosFragment)).perform(
            RecyclerViewActions
                .actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click())
        )
        verify(listener).onPhotoClicked(any())
    }

    override fun photoListener() = listener
}
