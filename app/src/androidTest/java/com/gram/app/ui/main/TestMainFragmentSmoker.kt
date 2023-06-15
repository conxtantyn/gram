package com.gram.app.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.launchFragmentInContainer
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
import org.mockito.Mockito.atLeast
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4ClassRunner::class)
class TestMainFragmentSmoker : MockComponent(), MainBuilder.Parent {

    @get:Rule
    internal val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val router = mock(MainRouter::class.java)

    private val cache = mutableListOf(mockPhoto)

    private val stateFlow = MutableStateFlow(cache)

    @Before
    fun setup() = runTest {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        `when`(photoPreferenceRepository().hasSession()).thenReturn(true)
    }

    @Test
    fun testWelcomeState() = runTest {
        `when`(photoPreferenceRepository().getAll()).thenReturn(MutableStateFlow(emptyList()))
        `when`(photoRepository().getRandomPhoto()).thenThrow(RuntimeException())
        launchFragmentInContainer(
            themeResId = R.style.Theme_Gram,
        ) {
            TestMainFragment(router, this@TestMainFragmentSmoker)
        }
        verify(router).navigateToWelcome()
    }

    @Test
    fun testWelcomeLoadingState() = runTest {
        `when`(photoPreferenceRepository().getAll()).thenReturn(MutableStateFlow(emptyList()))
        `when`(photoRepository().getRandomPhoto()).thenThrow(RuntimeException())
        val scenario = launchFragmentInContainer(
            themeResId = R.style.Theme_Gram,
        ) {
            TestMainFragment(router, this@TestMainFragmentSmoker)
        }
        scenario.onFragment {
            it.getPhoto()
        }
        verify(router, atLeast(2)).navigateToWelcome()
    }

    @Test
    fun testContentLoadingState() = runTest {
        `when`(photoPreferenceRepository().getAll()).thenReturn(stateFlow)
        `when`(photoRepository().getRandomPhoto()).thenThrow(RuntimeException())
        val scenario = launchFragmentInContainer(
            themeResId = R.style.Theme_Gram,
        ) {
            TestMainFragment(router, this@TestMainFragmentSmoker)
        }
        scenario.onFragment {
            it.getPhoto()
        }
        verify(router, times(0)).navigateToWelcome()
    }

    @Test
    fun testContentState() = runTest {
        `when`(photoPreferenceRepository().getAll()).thenReturn(stateFlow)
        `when`(photoRepository().getRandomPhoto()).thenReturn(cache.last())
        launchFragmentInContainer(
            themeResId = R.style.Theme_Gram,
        ) {
            TestMainFragment(router, this@TestMainFragmentSmoker)
        }
        verify(router).navigateToContent()
    }
}
