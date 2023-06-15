package com.gram.app.ui.main

import com.gram.core.Router

interface MainRouter : Router {
    fun navigateToWelcome()
    fun navigateToContent()
}
