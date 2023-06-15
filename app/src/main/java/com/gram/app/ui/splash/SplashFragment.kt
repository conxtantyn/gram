package com.gram.app.ui.splash

import android.content.Context
import androidx.fragment.app.Fragment
import com.gram.app.R
import com.gram.core.component.ComponentProvider
import com.gram.core.findBuilder

class SplashFragment :
    Fragment(R.layout.fragment_splash),
    ComponentProvider<SplashBuilder.Component> {

    private val component: SplashBuilder.Component by lazy {
        findBuilder(SplashBuilder::class).build(requireContext())
    }

    override fun requireComponent() = component

    override fun onAttach(context: Context) {
        super.onAttach(context)
        component.inject(this)
    }
}
