package com.gram.app.ui.launcher

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.createGraph
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.fragment
import com.gram.app.R
import com.gram.app.databinding.ActivityLauncherBinding
import com.gram.app.ui.main.MainFragment
import com.gram.core.component.ComponentProvider
import com.gram.core.findBuilder

class LauncherActivity : AppCompatActivity(), ComponentProvider<LauncherBuilder.Component> {

    private lateinit var binding: ActivityLauncherBinding

    private val mainRoute = R.id.mainFragment.toString()

    private val component: LauncherBuilder.Component by lazy {
        findBuilder(LauncherBuilder::class).build(this)
    }

    override fun requireComponent() = component

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityLauncherBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val controller = binding.launcherActivity.getFragment<NavHostFragment>().navController
        controller.graph = controller.createGraph(
            startDestination = mainRoute
        ) {
            fragment<MainFragment>(mainRoute)
        }
    }
}
