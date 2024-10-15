package com.zainco.androidcookiescompose

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
            gymDao.update(GymFavouriteState(gymId, favouriteState))
            return@withContext gymDao.getAll()
        }

    private suspend fun updateLocalDatabase() {
        val gyms = apiService.getGyms()
        val favouriteGymsList = gymDao.getFavouriteGyms()
        gymDao.addAll(gyms)
        gymDao.updateAll(favouriteGymsList.map { GymFavouriteState(it.id, true) })
    }
    suspend fun getGymsFromRemote() = withContext(Dispatchers.IO) {
        try {
            updateLocalDatabase()
        } catch (e: Exception) {
            if (gymDao.getAll().isEmpty())
                throw Exception("Something went wrong. try connecting to internet")
        }
        gymDao.getAll()
    }

}