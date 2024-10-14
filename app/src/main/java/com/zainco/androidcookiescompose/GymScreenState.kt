package com.zainco.androidcookiescompose

data class GymScreenState(
    val gymsList: List<Gym>,
    val isLoading: Boolean,
    val error: String? = null
)
