package com.zainco.androidcookiescompose.gyms.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "gyms")
data class LocalGym(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "gym_id")
    val id: Int,
    @ColumnInfo(name = "gym_name")
    val name: String,
    @ColumnInfo(name = "gym_location")
    val place: String,
    @ColumnInfo(name = "is_open")
    val isOpen: Boolean,
    @ColumnInfo(name = "is_favorite")
    val isFavorite: Boolean = false
)
