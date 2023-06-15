package com.gram.app.ui.gallery.content

import android.animation.ObjectAnimator
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.gram.app.R
import com.gram.app.databinding.FragmentContentBinding
import com.gram.app.extention.findLifecycleOwner
import com.gram.app.viewmodel.PhotoViewModel
import com.gram.core.component.ComponentProvider
import com.gram.core.findBuilder
import javax.inject.Inject

open class ContentFragment :
    Fragment(R.layout.fragment_content),
    ComponentProvider<ContentBuilder.Component> {

    @Inject
    internal lateinit var router: ContentRouter

    @Inject
    internal lateinit var factory: ViewModelProvider.Factory

    protected lateinit var viewModel: PhotoViewModel

    private lateinit var binding: FragmentContentBinding

    private val component: ContentBuilder.Component by lazy {
        findBuilder(ContentBuilder::class).build(requireContext())
    }

    override fun requireComponent() = component

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireComponent().inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val owner = findLifecycleOwner<PhotoViewModel.LifecycleOwner>()

        binding = FragmentContentBinding.bind(view)
        viewModel = ViewModelProvider(owner, factory)[PhotoViewModel::class.java]

        setupViews()
    }

    private fun setupViews() {
        val controller = binding.navigationHost
            .getFragment<NavHostFragment>()
            .navController
        router.onAttach(controller)
        binding.fabButton.clipToOutline = true
        attachListeners(controller)
    }

    private fun attachListeners(controller: NavController) {
        controller.addOnDestinationChangedListener { _, _, _ ->
            updateIconView()
        }
        binding.fabButton.setOnClickListener {
            if (!router.isPhotoActive()) {
                viewModel()
            } else {
                router.dismissPhoto()
            }
        }
    }

    private fun updateIconView() {
        val initial = 0f
        val angle = if (router.isPhotoActive()) {
            ROTATION_DEGREE
        } else {
            initial
        }
        ObjectAnimator
            .ofFloat(binding.fabButton, View.ROTATION, initial, angle)
            .setDuration(ANIMATION_DURATION)
            .start()
    }

    private companion object {
        const val ROTATION_DEGREE = 45f
        const val ANIMATION_DURATION = 250L
    }
}
