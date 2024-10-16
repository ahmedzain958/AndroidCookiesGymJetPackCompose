package com.zainco.androidcookiescompose.gyms.data

import com.zainco.androidcookiescompose.GymsApplication
import com.zainco.androidcookiescompose.gyms.data.local.GymsDatabase
import com.zainco.androidcookiescompose.gyms.data.local.LocalGym
import com.zainco.androidcookiescompose.gyms.data.local.LocalGymFavouriteState
import com.zainco.androidcookiescompose.gyms.data.remote.GymsApiService
import com.zainco.androidcookiescompose.gyms.domain.Gym
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GymsRepository {
    private var apiService = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("https://projectname-5ee14.firebaseio.com/").build()
        .create(GymsApiService::class.java)

    private val gymDao = GymsDatabase.getDaoInstance(GymsApplication.getAppContext())

    suspend fun toggleFavouriteGym(gymId: Int, favouriteState: Boolean) =
        withContext(Dispatchers.IO) {
            gymDao.update(LocalGymFavouriteState(gymId, favouriteState))
            return@withContext gymDao.getAll()
        }

    suspend fun getGyms(): List<Gym> =
        withContext(Dispatchers.IO) {
            return@withContext gymDao.getAll().map {
                Gym(it.id, it.name, it.place, it.isOpen, it.isFavorite)
            }
        }

    private suspend fun updateLocalDatabase() {
        val gyms = apiService.getGyms()
        val favouriteGymsList = gymDao.getFavouriteGyms()
        gymDao.addAll(gyms.map {
            LocalGym(it.id, it.name, it.place, it.isOpen)
        })
        gymDao.updateAll(favouriteGymsList.map { LocalGymFavouriteState(it.id, true) })
    }

    suspend fun loadGyms() = withContext(Dispatchers.IO) {
        try {
            updateLocalDatabase()
        } catch (e: Exception) {
            if (gymDao.getAll().isEmpty())
                throw Exception("Something went wrong. try connecting to internet")
        }
        gymDao.getAll()
    }

}