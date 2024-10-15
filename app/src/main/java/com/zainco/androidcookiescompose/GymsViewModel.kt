package com.zainco.androidcookiescompose

import androidx.compose.runtime.Recomposer
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
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
    private var _state by mutableStateOf(GymScreenState(emptyList(), true))
    val state: State<GymScreenState>
        get() = derivedStateOf { _state }

    private val repo = GymsRepository()

    private var coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
        _state = _state.copy(error = throwable.message, isLoading = false)
    }

    init {
        getGyms()
    }

    private fun getGyms() {
        viewModelScope.launch(coroutineExceptionHandler) {
            val receivedGyms = repo.getGymsFromRemote()
            _state = _state.copy(gymsList = receivedGyms, isLoading = false)
        }
    }


    fun toggleFavState(gymId: Int) {
        val gyms = _state.gymsList.toMutableList()
        val itemIndex = gyms.indexOfFirst { it.id == gymId }
        viewModelScope.launch {
            val updatedGymsList = repo.toggleFavouriteGym(gymId, !gyms[itemIndex].isFavorite)
            _state = _state.copy(gymsList = updatedGymsList)
        }
    }
}