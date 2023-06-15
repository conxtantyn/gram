package com.gram.core.component

interface ComponentProvider<T> {
    fun requireComponent(): T
}
