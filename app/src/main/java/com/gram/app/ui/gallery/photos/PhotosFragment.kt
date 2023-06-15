package com.gram.app.ui.gallery.photos

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gram.app.R
import com.gram.app.databinding.FragmentPhotosBinding
import com.gram.app.extention.findLifecycleOwner
import com.gram.app.viewmodel.PhotoViewModel
import com.gram.core.component.ComponentProvider
import com.gram.core.findBuilder
import com.gram.gallery.model.Photo
import javax.inject.Inject

open class PhotosFragment :
    Fragment(R.layout.fragment_photos),
    ComponentProvider<PhotosBuilder.Component>,
    PhotosAdapter.ListingAdapterListener {

    @Inject
    internal lateinit var adapter: PhotosAdapter

    @Inject
    internal lateinit var photosListener: PhotosListener

    @Inject
    internal lateinit var factory: ViewModelProvider.Factory

    protected lateinit var viewModel: PhotoViewModel

    private lateinit var binding: FragmentPhotosBinding

    private val component: PhotosBuilder.Component by lazy {
        findBuilder(PhotosBuilder::class).build(requireContext())
    }

    override fun requireComponent() = component

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireComponent().inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPhotosBinding.bind(view)

        setupView()
        setViewModel()
    }

    private fun setupView() {
        val density: Float = resources.displayMetrics.density
        val valueInDp: Int = (HEADER_OFFSET / density).toInt()

        adapter.listingAdapterListener = this
        binding.root.adapter = adapter
        binding.root.addItemDecoration(HeaderDecorator(valueInDp))
        binding.root.layoutManager = GridLayoutManager(
            requireContext(),
            GRID_COUNT
        ).apply {
            reverseLayout = true
        }
    }

    private fun setViewModel() {
        val owner = findLifecycleOwner<PhotoViewModel.LifecycleOwner>()

        viewModel = ViewModelProvider(owner, factory)[PhotoViewModel::class.java]
        viewModel.state.observe(viewLifecycleOwner, this::observeState)
    }

    private fun observeState(state: PhotoViewModel.State) {
        if (!state.loading && !state.initialize) {
            val offset = state.photos.size - 1
            adapter.submit(state.photos)
            updateView(offset)
        }
    }

    private fun updateView(offset: Int) {
        if (arguments == null) {
            binding.root.scrollToPosition(offset)
        } else if (hasOffset()) {
            binding.root.post {
                binding.root.smoothScrollToPosition(offset)
            }
        }
        updateOffset(offset)
    }

    override fun onPause() {
        super.onPause()
        updateOffset(-1)
    }

    private fun updateOffset(offset: Int) {
        arguments = (arguments ?: Bundle()).apply {
            putInt(OFFSET_ARGS, offset)
        }
    }

    private fun hasOffset(): Boolean {
        return arguments?.containsKey(OFFSET_ARGS) == true &&
            arguments?.getInt(OFFSET_ARGS) != OUTBOUND
    }

    override fun onItemClicked(photo: Photo) {
        photosListener.onPhotoClicked(photo)
    }

    private class HeaderDecorator(
        private val offset: Int,
    ) : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            if (parent.getChildAdapterPosition(view) <= 2 &&
                (parent.adapter?.itemCount ?: 0) > 4
            ) {
                outRect.bottom = offset
            } else {
                super.getItemOffsets(outRect, view, parent, state)
            }
        }
    }

    private companion object {
        const val GRID_COUNT = 2
        const val HEADER_OFFSET = 160
        const val OUTBOUND = -1

        const val OFFSET_ARGS = "OFFSET_ARGS"
    }
}
