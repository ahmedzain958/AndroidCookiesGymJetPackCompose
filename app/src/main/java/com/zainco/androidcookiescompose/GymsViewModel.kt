package com.zainco.androidcookiescompose

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class GymsViewModel(private val stateHandle: SavedStateHandle) : ViewModel() {
    var state by
        mutableStateOf(restoreGymsAndTheSelected())

    private fun getGyms() = listOfGyms

    fun toggleFavState(gymId: Int){
        val gyms = state.toMutableList()
        val itemIndex = gyms.indexOfFirst { it.id == gymId }
        gyms[itemIndex] = gyms[itemIndex].copy(isFavorite = !gyms[itemIndex].isFavorite)
        storeSelectedGym(gyms[itemIndex])
        state = gyms
    }
    fun restoreGymsAndTheSelected(): List<Gym>{
        val gyms = getGyms()
        stateHandle.get<List<Int>>(FAV_IDS)?.let { savedIds ->
            savedIds.forEach { gymId ->
                gyms.find { it.id == gymId }?.isFavorite = true
            }
        }
        return gyms
    }
    private fun storeSelectedGym(gym: Gym){
        val savedHandleList = stateHandle.get<List<Int>>(FAV_IDS).orEmpty().toMutableList()
        if(gym.isFavorite)
            savedHandleList.add(gym.id)
        else
            savedHandleList.remove(gym.id)
        stateHandle[FAV_IDS] = savedHandleList
    }
    companion object{
        const val FAV_IDS= "favouriteGymsIds"
    }
}