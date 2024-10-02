package com.zainco.androidcookiescompose

import retrofit2.Call
import retrofit2.http.GET


interface GymsApiService{
    @GET("gyms.json")
    suspend fun getGyms(): List<Gym>
}