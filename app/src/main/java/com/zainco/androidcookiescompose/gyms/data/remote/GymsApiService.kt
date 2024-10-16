package com.zainco.androidcookiescompose.gyms.data.remote

import retrofit2.http.GET
import retrofit2.http.Query


interface GymsApiService {
    @GET("gyms.json")
    suspend fun getGyms(): List<RemoteGym>

    //https://projectname-5ee14.firebaseio.com/gyms.json?orderBy=%22id%22&equalTo=6
    @GET("gyms.json?orderBy=\"id\"")
    suspend fun getGymById(@Query("equalTo") id: Int): Map<String, RemoteGym>
}