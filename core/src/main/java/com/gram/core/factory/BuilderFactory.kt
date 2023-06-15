package com.gram.core.factory

import dagger.Binds

interface BuilderFactory {
    fun <T : Any> builder(clazz: Class<T>): T

    interface Provider {
        fun factory(): BuilderFactory
    }

    @dagger.Module
    abstract class Module {
        @Binds
        internal abstract fun builderFactory(
            factoryDelegate: BuilderFactoryDelegate
        ): BuilderFactory
    }
}
