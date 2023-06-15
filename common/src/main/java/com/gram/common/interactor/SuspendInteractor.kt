package com.gram.common.interactor

interface SuspendInteractor<T> : Interactor<T> {
    suspend operator fun invoke(): T
}
