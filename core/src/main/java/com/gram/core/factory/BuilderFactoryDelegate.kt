package com.gram.core.factory

import com.gram.core.Builder
import javax.inject.Inject

class BuilderFactoryDelegate @Inject constructor(
    private val factory: Map<Class<*>, @JvmSuppressWildcards javax.inject.Provider<Builder>>
) : BuilderFactory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : Any> builder(clazz: Class<T>): T {
        return (factory[clazz]?.get() as? T?) ?: throw RuntimeException(
            "Builder ${clazz.name} not found!"
        )
    }
}
