package com.gram.gallery.repository

import kotlinx.coroutines.flow.StateFlow

interface PreferenceRepository<T : java.io.Serializable> {
    fun getAll(): StateFlow<List<T>>

    suspend fun save(entity: T): Long
}
