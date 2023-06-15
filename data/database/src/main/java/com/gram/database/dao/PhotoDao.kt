package com.gram.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gram.database.domain.Photos

@Dao
interface PhotoDao {
    @Query("SELECT * FROM Photos entity")
    suspend fun getAll(): List<Photos>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(entity: Photos): Long
}
