package com.gram.app.ui.welcome

import com.gram.core.Router

interface WelcomeRouter : Router {
    fun navigateToSplash()
    fun navigateToError()
}
