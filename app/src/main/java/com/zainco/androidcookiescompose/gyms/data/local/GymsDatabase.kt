package com.zainco.androidcookiescompose.gyms.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlin.concurrent.Volatile

@Database(entities = [LocalGym::class], version = 1, exportSchema = false)
abstract class GymsDatabase : RoomDatabase() {
    abstract val dao: GymsDao


}