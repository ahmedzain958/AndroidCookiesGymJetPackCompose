package com.zainco.androidcookiescompose.gyms.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlin.concurrent.Volatile

@Database(entities = [LocalGym::class], version = 1, exportSchema = false)
abstract class GymsDatabase : RoomDatabase() {
    abstract val dao: GymsDao

    companion object {
        @Volatile
        private var daoInstance: GymsDao? = null

        private fun buildDatabase(context: Context): GymsDatabase = Room.databaseBuilder(
            context.applicationContext,
            GymsDatabase::class.java,
            "gyms_db"
        ).fallbackToDestructiveMigration().build()

        fun getDaoInstance(context: Context): GymsDao {
            synchronized(this){
                if (daoInstance == null) {
                    daoInstance = buildDatabase(context).dao
                }
                return daoInstance as GymsDao
            }

        }
    }

}