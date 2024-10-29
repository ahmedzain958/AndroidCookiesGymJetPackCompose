package com.zainco.androidcookiescompose.gyms.data

import com.zainco.androidcookiescompose.gyms.data.local.GymsDao
import com.zainco.androidcookiescompose.gyms.data.local.LocalGym
import com.zainco.androidcookiescompose.gyms.data.local.LocalGymFavouriteState
import com.zainco.androidcookiescompose.gyms.data.remote.GymsApiService
import com.zainco.androidcookiescompose.gyms.domain.Gym
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GymsRepository @Inject constructor(
    private val apiService: GymsApiService,
    private val gymDao: GymsDao,
) {

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

    suspend fun getGymById(id: Int) = withContext(Dispatchers.IO) {
        apiService.getGymById(id)
    }

}