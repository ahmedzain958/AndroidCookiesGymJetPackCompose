package com.zainco.androidcookiescompose

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GymsDetailsViewModel: ViewModel() {
    val state = mutableStateOf<Gym?>(null)
    private var apiService: GymsApiService
    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://projectname-5ee14.firebaseio.com/").build()
        apiService = retrofit.create(GymsApiService::class.java)
        getGym(6)
    }

    private fun getGym(id: Int) {
        viewModelScope.launch {
            val gym = getGymFromRemoteDB(id)
        }
    }

    private suspend fun getGymFromRemoteDB(id: Int) = withContext(Dispatchers.IO) {
        apiService.getGymById(id)
    }

}