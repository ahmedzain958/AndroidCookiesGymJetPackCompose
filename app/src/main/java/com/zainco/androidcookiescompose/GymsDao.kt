package com.zainco.androidcookiescompose

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface GymsDao {
    @Query("SELECT * FROM gyms")
    fun getAll(): List<Gym>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAll(gyms: List<Gym>)
}