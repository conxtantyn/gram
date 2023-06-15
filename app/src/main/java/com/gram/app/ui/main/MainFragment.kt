package com.gram.app.ui.main

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.gram.app.R
import com.gram.app.extention.findControllerById
import com.gram.app.viewmodel.PhotoViewModel
import com.gram.core.component.ComponentProvider
import com.gram.core.findBuilder
import javax.inject.Inject

open class MainFragment :
    Fragment(R.layout.fragment_main),
    ComponentProvider<MainBuilder.Component>,
    PhotoViewModel.LifecycleOwner {

    @Inject
    internal lateinit var router: MainRouter

    @Inject
    internal lateinit var factory: ViewModelProvider.Factory

    protected lateinit var viewModel: PhotoViewModel

    private val component: MainBuilder.Component by lazy {
        findBuilder(MainBuilder::class).build(requireContext())
    }

    override fun requireComponent() = component

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireComponent().inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupViewModel()
    }

    private fun setupViews() {
        router.onAttach(findControllerById(R.id.mainFragment))
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, factory)[PhotoViewModel::class.java]
        viewModel.state.observe(viewLifecycleOwner, this::observeState)
    }

    private fun observeState(state: PhotoViewModel.State) {
        if (state.initialize) {
            router.navigateToWelcome()
        } else {
            router.navigateToContent()
        }
        state.error?.message?.let {
            Toast.makeText(
                requireContext(),
                it,
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
