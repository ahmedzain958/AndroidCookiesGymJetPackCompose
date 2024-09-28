package com.zainco.androidcookiescompose

val listOfGyms = listOf<Gym>(
    Gym("UpTown Gym", "123 Main St, Anytown USA"),
    Gym("Central Fitness", "456 Elm St, Anytown USA"),
    Gym("Sunset Gym", "789 Oak St, Anytown USA"),
    Gym("City Gym", "321 Pine St, Anytown USA"),
    Gym("Hillside Fitness", "654 Maple St, Anytown USA"),
)

data class Gym(val name: String, val place: String)