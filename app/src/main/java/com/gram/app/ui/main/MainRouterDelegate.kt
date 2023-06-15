package com.gram.app.ui.main

import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.createGraph
import androidx.navigation.fragment.fragment
import com.gram.app.R
import com.gram.app.extention.map
import com.gram.app.ui.gallery.content.ContentFragment
import com.gram.app.ui.welcome.WelcomeFragment
import javax.inject.Inject

@MainBuilder.Scope
class MainRouterDelegate @Inject constructor() : MainRouter {

    private lateinit var controller: NavController

    private val welcomeRoute = R.id.welcomeFragment.toString()

    private val contentRoute = R.id.contentFragment.toString()

    override fun onAttach(vararg controllerArgs: NavController) {
        controllerArgs.map { controller ->
            this.controller = controller
            this.controller.graph = controller.createGraph(
                startDestination = welcomeRoute
            ) {
                fragment<WelcomeFragment>(welcomeRoute)
                fragment<ContentFragment>(contentRoute)
            }
        }
    }

    override fun navigateToWelcome() {
        if (controller.currentDestination?.route != welcomeRoute) {
            controller.navigate(
                welcomeRoute,
                NavOptions.Builder().setPopUpTo(
                    contentRoute, true
                ).build()
            )
        }
    }

    override fun navigateToContent() {
        if (controller.currentDestination?.route != contentRoute) {
            controller.navigate(
                contentRoute,
                NavOptions
                    .Builder()
                    .setPopUpTo(welcomeRoute, true)
                    .build()
            )
        }
    }
}
