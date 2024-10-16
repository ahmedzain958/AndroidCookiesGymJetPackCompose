package com.zainco.androidcookiescompose.gyms.domain

data class Gym(
    val id: Int,
    val name: String,
    val place: String,
    val isOpen: Boolean,
    val isFavorite: Boolean = false
)
