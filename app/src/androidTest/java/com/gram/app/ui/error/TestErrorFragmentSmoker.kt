package com.gram.app.ui.error

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
import com.gram.gallery.model.Photo
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
import org.mockito.Mockito.doAnswer
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.internal.stubbing.answers.AnswersWithDelay
import org.mockito.internal.stubbing.answers.Returns

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4ClassRunner::class)
class TestErrorFragmentSmoker : MockComponent(), ErrorBuilder.Parent {

    @get:Rule
    internal val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val networkDelay = 100L

    private val stateFlow = MutableStateFlow(arrayListOf<Photo>())

    private lateinit var scenario: FragmentScenario<TestErrorFragment>

    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @Test
    fun testRetry() = runTest {
        `when`(photoPreferenceRepository().hasSession()).thenReturn(true)
        `when`(photoPreferenceRepository().getAll()).thenReturn(stateFlow)
        `when`(photoRepository().getRandomPhoto()).thenThrow(RuntimeException())
        scenario = launchFragmentInContainer(
            themeResId = R.style.Theme_Gram,
        ) {
            TestErrorFragment(this@TestErrorFragmentSmoker)
        }
        onView(withId(R.id.retry)).perform(click())
        verify(photoRepository()).getRandomPhoto()
    }

    @Test
    fun testRetryWhileLoading() = runTest {
        val answer = AnswersWithDelay(networkDelay, Returns(mockPhoto))

        `when`(photoPreferenceRepository().hasSession()).thenReturn(true)
        `when`(photoPreferenceRepository().getAll()).thenReturn(stateFlow)
        `when`(photoRepository().getRandomPhoto()).thenThrow(RuntimeException())
        scenario = launchFragmentInContainer(
            themeResId = R.style.Theme_Gram,
        ) {
            TestErrorFragment(this@TestErrorFragmentSmoker)
        }
        doAnswer(answer).`when`(photoRepository()).getRandomPhoto()

        onView(withId(R.id.retry)).perform(click())
        onView(withId(R.id.retry)).perform(click())
        verify(photoRepository(), times(1)).getRandomPhoto()
    }
}
