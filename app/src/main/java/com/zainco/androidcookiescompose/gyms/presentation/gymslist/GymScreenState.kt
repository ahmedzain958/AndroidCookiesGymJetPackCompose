package com.zainco.androidcookiescompose.gyms.presentation.gymslist

import com.zainco.androidcookiescompose.gyms.domain.Gym

data class GymScreenState(
    val gymsList: List<Gym>,
    val isLoading: Boolean,
    val error: String? = null
)
