package com.gram.app.ui.welcome

import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.createGraph
import androidx.navigation.fragment.fragment
import com.gram.app.R
import com.gram.app.extention.map
import com.gram.app.ui.error.ErrorFragment
import com.gram.app.ui.splash.SplashFragment
import javax.inject.Inject

@WelcomeBuilder.Scope
class WelcomeRouterDelegate @Inject constructor() : WelcomeRouter {

    private lateinit var controller: NavController

    private val splashRoute = R.id.splashFragment.toString()

    private val errorRoute = R.id.errorFragment.toString()

    override fun onAttach(vararg controllerArgs: NavController) {
        controllerArgs.map { controller ->
            this.controller = controller
            this.controller.graph = controller.createGraph(
                startDestination = splashRoute
            ) {
                fragment<SplashFragment>(splashRoute)
                fragment<ErrorFragment>(errorRoute)
            }
        }
    }

    override fun navigateToSplash() {
        controller.navigate(
            splashRoute,
            NavOptions
                .Builder()
                .setPopUpTo(errorRoute, true)
                .build()
        )
    }

    override fun navigateToError() {
        controller.navigate(
            errorRoute,
            NavOptions
                .Builder()
                .setPopUpTo(splashRoute, true)
                .build()
        )
    }
}
