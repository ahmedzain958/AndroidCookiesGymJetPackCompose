package com.zainco.androidcookiescompose

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface GymsDAO {
    @Query("SELECT * FROM gyms")
    suspend fun getAll(): List<Gym>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAll(gyms: List<Gym>)
}