package com.zainco.androidcookiescompose

import retrofit2.http.GET
import retrofit2.http.Query


interface GymsApiService {
    @GET("gyms.json")
    suspend fun getGyms(): List<Gym>

    @GET("gyms.json?OrderBy=\"id\"")
    suspend fun getGymById(@Query("equalTo") id: Int): Gym
}