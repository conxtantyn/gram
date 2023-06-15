package com.gram.core

import android.app.Activity
import androidx.fragment.app.Fragment
import com.gram.core.component.ComponentProvider
import com.gram.core.factory.BuilderFactory
import kotlin.reflect.KClass

@Suppress("UNCHECKED_CAST")
fun <T : Any> Activity.findBuilder(clazz: KClass<T>): T {
    val dependency = application as ComponentProvider<*>
    return (dependency.requireComponent() as BuilderFactory.Provider).factory()
        .builder(clazz.java)
}

@Suppress("UNCHECKED_CAST")
fun <T : Any> Fragment.findBuilder(clazz: KClass<T>): T {
    var fragment: Fragment? = this
    while (fragment?.parentFragment != null) {
        val dependency = fragment.parentFragment as? ComponentProvider<*>?
        val factory = (dependency?.requireComponent() as? BuilderFactory.Provider?)?.factory()
        val builder = factory?.builder(clazz.java)
        if (builder != null) {
            return builder
        }
        fragment = fragment.parentFragment
    }
    return findBuilderInHost(clazz)
}

@Suppress("UNCHECKED_CAST")
private fun <T : Any> Fragment.findBuilderInHost(clazz: KClass<T>): T {
    val dependency = activity as? ComponentProvider<*>?
    val factory = (dependency?.requireComponent() as? BuilderFactory.Provider?)?.factory()
    val builder = factory?.builder(clazz.java)
    if (builder != null) {
        return builder
    }
    throw RuntimeException("Builder ${clazz.java.name} not found!")
}
