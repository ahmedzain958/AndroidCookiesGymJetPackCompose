package com.zainco.androidcookiescompose.gyms.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "gyms")
data class LocalGymFavouriteState(
    @ColumnInfo(name = "gym_id")
    val id: Int,
    @ColumnInfo(name = "is_favorite")
    val isFavorite: Boolean = false
)
