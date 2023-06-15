package com.gram.app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gram.gallery.interactor.PhotoSetupInteractor
import com.gram.gallery.interactor.PhotosInteractor
import com.gram.gallery.interactor.RandomPhotoInteractor
import com.gram.gallery.model.Photo
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class PhotoViewModel @Inject constructor(
    private val photosInteractor: PhotosInteractor,
    private val photoSetupInteractor: PhotoSetupInteractor,
    private val randomPhotoInteractor: RandomPhotoInteractor
) : ViewModel() {

    private val mutableState = MutableLiveData<State>()

    val state: LiveData<State> = mutableState

    init {
        mutableState.postValue(getInitialState())
        viewModelScope.launch {
            setup()
            photosInteractor.invoke().collectLatest {
                mutableState.value?.let { state ->
                    mutableState.postValue(state.withItems(it))
                }
            }
        }
    }

    private fun setup() {
        viewModelScope.launch {
            try {
                photoSetupInteractor.invoke()
            } catch (error: Throwable) {
                mutableState.value?.let {
                    mutableState.postValue(it.withError(error))
                }
            }
        }
    }

    operator fun invoke() {
        mutableState.value?.let {
            mutableState.postValue(it.withLoader(true))
        }
        viewModelScope.launch {
            try {
                randomPhotoInteractor.invoke()
            } catch (error: Throwable) {
                mutableState.value?.let {
                    mutableState.postValue(it.withError(error))
                }
            }
        }
    }

    private fun getInitialState() = State(
        initialize = true,
        loading = false,
        photos = emptyList(),
        error = null
    )

    interface LifecycleOwner

    data class State(
        val initialize: Boolean,
        val loading: Boolean,
        val photos: List<Photo>,
        val error: Throwable? = null,
    ) {
        fun withLoader(loading: Boolean): State {
            return State(
                initialize = initialize,
                loading = loading,
                photos = photos,
                error = error
            )
        }

        fun withError(error: Throwable): State {
            return State(
                initialize = initialize,
                loading = false,
                photos = photos,
                error = error
            )
        }

        fun withItems(items: List<Photo>): State {
            return State(
                initialize = if (items.isEmpty()) {
                    initialize
                } else {
                    false
                },
                loading = false,
                photos = items,
                error = error
            )
        }
    }
}
