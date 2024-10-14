package com.zainco.androidcookiescompose

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GymsViewModel() : ViewModel() {
    var state by mutableStateOf(GymScreenState(emptyList(), true))

    private var apiService: GymsApiService

    private val gymDao = GymsDatabase.getDaoInstance(GymsApplication.getAppContext())

    private var coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
        state = state.copy(error = throwable.message, isLoading = false)
    }

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://projectname-5ee14.firebaseio.com/").build()
        apiService = retrofit.create(GymsApiService::class.java)
        getGyms()
    }

    private fun getGyms() {
        viewModelScope.launch(coroutineExceptionHandler) {
            val receivedGyms = getGymsFromRemote()
            state = state.copy(gymsList = receivedGyms, isLoading = false)
        }
    }

    private suspend fun getGymsFromRemote() = withContext(Dispatchers.IO) {
        try {
            updateLocalDatabase()
        } catch (e: Exception) {
            if (gymDao.getAll().isEmpty())
                throw Exception("Something went wrong. try connecting to internet")
        }
        gymDao.getAll()
    }

    private suspend fun updateLocalDatabase() {
        val gyms = apiService.getGyms()
        val favouriteGymsList = gymDao.getFavouriteGyms()
        gymDao.addAll(gyms)
        gymDao.updateAll(favouriteGymsList.map { GymFavouriteState(it.id, true) })
    }

    fun toggleFavState(gymId: Int) {
        val gyms = state.gymsList.toMutableList()
        val itemIndex = gyms.indexOfFirst { it.id == gymId }
        viewModelScope.launch {
            val updatedGymsList = toggleFavouriteGym(gymId, !gyms[itemIndex].isFavorite)
            state = state.copy(gymsList = updatedGymsList)
        }
    }

    private suspend fun toggleFavouriteGym(gymId: Int, currentFavouriteState: Boolean) =
        withContext(Dispatchers.IO) {
            gymDao.update(GymFavouriteState(gymId, currentFavouriteState))
            return@withContext gymDao.getAll()
        }


}