package com.gram.core

import androidx.navigation.NavController

interface Router {
    fun onAttach(vararg controllerArgs: NavController)
}
