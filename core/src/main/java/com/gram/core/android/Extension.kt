package com.gram.core.android

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.navigation.NavArgument
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController

@Suppress("UNCHECKED_CAST")
fun <T : Parcelable> Fragment.requireArgs(clazz: Class<T>): T {
    val hasArgs = arguments?.containsKey(clazz.name)
    if (hasArgs == false) {
        arguments = Bundle().apply {
            putParcelable(
                clazz.name,
                findNavController()
                    .graph
                    .arguments[clazz.name]?.defaultValue as T
            )
        }
    }
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        arguments?.getParcelable(clazz.name, clazz)
    } else {
        arguments?.getParcelable(clazz.name)
    }!!
}

fun <T : Parcelable> NavController.navigate(
    route: String,
    args: T,
    options: NavOptions? = null,
    extras: FragmentNavigator.Extras? = null,
) {
    graph.addArgument(
        args.javaClass.name,
        NavArgument.Builder()
            .setType(NavigationArgs(args.javaClass.kotlin))
            .setDefaultValue(args)
            .build(),
    )
    navigate(route, options, extras)
}
