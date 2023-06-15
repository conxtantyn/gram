package com.gram.app.ui.welcome

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.gram.app.R
import com.gram.app.extention.findControllerById
import com.gram.app.extention.findLifecycleOwner
import com.gram.app.viewmodel.PhotoViewModel
import com.gram.core.component.ComponentProvider
import com.gram.core.findBuilder
import javax.inject.Inject

open class WelcomeFragment :
    Fragment(R.layout.fragment_welcome),
    ComponentProvider<WelcomeBuilder.Component> {

    @Inject
    internal lateinit var router: WelcomeRouter

    @Inject
    internal lateinit var factory: ViewModelProvider.Factory

    protected lateinit var viewModel: PhotoViewModel

    private val component: WelcomeBuilder.Component by lazy {
        findBuilder(WelcomeBuilder::class).build(requireContext())
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
        router.onAttach(findControllerById(R.id.welcomeFragment))
    }

    private fun setupViewModel() {
        val owner = findLifecycleOwner<PhotoViewModel.LifecycleOwner>()

        viewModel = ViewModelProvider(owner, factory)[PhotoViewModel::class.java]
        viewModel.state.observe(viewLifecycleOwner, this::observeState)
    }

    private fun observeState(state: PhotoViewModel.State) {
        if (state.loading) {
            router.navigateToSplash()
        } else if (state.error != null) {
            router.navigateToError()
        }
    }
}
