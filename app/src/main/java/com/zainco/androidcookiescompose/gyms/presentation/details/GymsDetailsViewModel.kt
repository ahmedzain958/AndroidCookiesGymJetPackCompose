package com.zainco.androidcookiescompose.gyms.presentation.details

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zainco.androidcookiescompose.gyms.data.remote.GymsApiService
import com.zainco.androidcookiescompose.gyms.domain.Gym
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GymsDetailsViewModel(val savedStateHandle: SavedStateHandle) : ViewModel() {
    val state = mutableStateOf<Gym?>(null)
    private var apiService: GymsApiService

    init {
        val retrofit: Retrofit =
            Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://projectname-5ee14.firebaseio.com/").build()
        apiService = retrofit.create(GymsApiService::class.java)
        val gymId = savedStateHandle.get<Int>("gym_id") ?: 0
        getGym(gymId)
    }

    private fun getGym(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val gym: Gym = getGymFromRemoteDB(id)
            withContext(Dispatchers.Main) {
                state.value = gym
            }
        }
    }

    private suspend fun getGymFromRemoteDB(id: Int) = withContext(Dispatchers.IO) {
        apiService.getGymById(id).values.first().let {
            Gym(
                it.id, it.name, it.place, it.isOpen
            )
        }
    }

}