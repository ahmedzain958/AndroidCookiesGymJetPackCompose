package com.zainco.androidcookiescompose.gyms.data

import com.zainco.androidcookiescompose.gyms.data.di.IODispatcher
import com.zainco.androidcookiescompose.gyms.data.local.GymsDao
import com.zainco.androidcookiescompose.gyms.data.local.LocalGym
import com.zainco.androidcookiescompose.gyms.data.local.LocalGymFavouriteState
import com.zainco.androidcookiescompose.gyms.data.remote.GymsApiService
import com.zainco.androidcookiescompose.gyms.domain.Gym
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GymsRepository @Inject constructor(
    private val apiService: GymsApiService,
    private val gymDao: GymsDao,
    @IODispatcher private val dispatcher: CoroutineDispatcher
) {

    suspend fun toggleFavouriteGym(gymId: Int, favouriteState: Boolean) =
        withContext(dispatcher) {
            gymDao.update(LocalGymFavouriteState(gymId, favouriteState))
            return@withContext gymDao.getAll()
        }

    suspend fun getGyms(): List<Gym> =
        withContext(dispatcher) {
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

    suspend fun loadGyms() = withContext(dispatcher) {
        try {
            updateLocalDatabase()
        } catch (e: Exception) {
            if (gymDao.getAll().isEmpty())
                throw Exception("Something went wrong. try connecting to internet")
        }
        gymDao.getAll()
    }

    suspend fun getGymById(id: Int) = withContext(dispatcher) {
        apiService.getGymById(id)
    }

}