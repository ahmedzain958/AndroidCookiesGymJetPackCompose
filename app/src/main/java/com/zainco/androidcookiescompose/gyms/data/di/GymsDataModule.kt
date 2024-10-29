package com.zainco.androidcookiescompose.gyms.data.di

import android.content.Context
import androidx.room.Room
import com.zainco.androidcookiescompose.gyms.data.local.GymsDatabase
import com.zainco.androidcookiescompose.gyms.data.remote.GymsApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GymsDataModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://projectname-5ee14.firebaseio.com/").build()
    }

    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext context: Context): GymsDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            GymsDatabase::class.java,
            "gyms_database"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideDao(gymsDatabase: GymsDatabase) = gymsDatabase.dao

    @Provides
    fun provideApiService(retrofit: Retrofit): GymsApiService =
        retrofit.create(GymsApiService::class.java)
}