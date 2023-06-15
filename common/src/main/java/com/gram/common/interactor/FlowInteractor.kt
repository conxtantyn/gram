package com.gram.common.interactor

import kotlinx.coroutines.flow.Flow

interface FlowInteractor<T> : Interactor<T> {
    operator fun invoke(): Flow<T>
}
