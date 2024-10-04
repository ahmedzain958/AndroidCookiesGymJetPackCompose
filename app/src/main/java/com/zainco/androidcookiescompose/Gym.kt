package com.zainco.androidcookiescompose

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(tableName = "gyms")
data class Gym(
    @ColumnInfo(name = "gym_id")
    val id: Int,
    @ColumnInfo(name = "gym_name")
    @SerializedName("gym_name")
    val name: String,
    @ColumnInfo(name = "gym_location")
    @SerializedName("gym_location")
    val place: String,
    @SerializedName("is_open")
    val isOpen: Boolean,
    var isFavorite: Boolean = false,
)