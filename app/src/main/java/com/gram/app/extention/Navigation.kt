package com.gram.app.extention

import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment

fun Fragment.findControllerById(id: Int): NavController {
    val host = childFragmentManager.findFragmentById(id)
    return (host as NavHostFragment).navController
}
