package com.zainco.androidcookiescompose

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GymsViewModel(private val stateHandle: SavedStateHandle) : ViewModel() {
    var state by  mutableStateOf(emptyList<Gym>())

    private var apiService: GymsApiService
    private var coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
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
            val gyms = getGymsFromRemote()
            withContext(Dispatchers.Main) {
                state = gyms.restoreGymsAndTheSelected()
            }
        }
    }

    private suspend fun getGymsFromRemote() = withContext(Dispatchers.IO){apiService.getGyms()}

    fun toggleFavState(gymId: Int) {
        val gyms = state.toMutableList()
        val itemIndex = gyms.indexOfFirst { it.id == gymId }
        gyms[itemIndex] = gyms[itemIndex].copy(isFavorite = !gyms[itemIndex].isFavorite)
        storeSelectedGym(gyms[itemIndex])
        state = gyms
    }

    fun List<Gym>.restoreGymsAndTheSelected(): List<Gym> {
        stateHandle.get<List<Int>>(FAV_IDS)?.let { savedIds ->
            savedIds.forEach { gymId ->
                this.find { it.id == gymId }?.isFavorite = true
            }
        }
        return this
    }

    private fun storeSelectedGym(gym: Gym) {
        val savedHandleList = stateHandle.get<List<Int>>(FAV_IDS).orEmpty().toMutableList()
        if (gym.isFavorite)
            savedHandleList.add(gym.id)
        else
            savedHandleList.remove(gym.id)
        stateHandle[FAV_IDS] = savedHandleList
    }

    override fun onCleared() {
        super.onCleared()
    }

    companion object {
        const val FAV_IDS = "favouriteGymsIds"
    }
}