package com.gram.app.ui.error

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.gram.app.R
import com.gram.app.databinding.FragmentErrorBinding
import com.gram.app.extention.findLifecycleOwner
import com.gram.app.viewmodel.PhotoViewModel
import com.gram.core.component.ComponentProvider
import com.gram.core.findBuilder
import javax.inject.Inject

open class ErrorFragment :
    Fragment(R.layout.fragment_error),
    ComponentProvider<ErrorBuilder.Component> {

    @Inject
    internal lateinit var factory: ViewModelProvider.Factory

    protected lateinit var viewModel: PhotoViewModel

    private lateinit var binding: FragmentErrorBinding

    private val component: ErrorBuilder.Component by lazy {
        findBuilder(ErrorBuilder::class).build(requireContext())
    }

    override fun requireComponent() = component

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireComponent().inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentErrorBinding.bind(view)
        setupView()
        setViewModel()
    }

    private fun setupView() {
        binding.retry.setOnClickListener {
            viewModel()
        }
    }

    private fun setViewModel() {
        val owner = findLifecycleOwner<PhotoViewModel.LifecycleOwner>()

        viewModel = ViewModelProvider(owner, factory)[PhotoViewModel::class.java]
        viewModel.state.observe(viewLifecycleOwner, this::observeState)
    }

    private fun observeState(state: PhotoViewModel.State) {
        binding.retry.isEnabled = !state.loading
        if (state.error?.message != null) {
            binding.subtitle.text = state.error.message
        }
    }
}
