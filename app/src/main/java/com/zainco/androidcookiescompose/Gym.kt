package com.zainco.androidcookiescompose

val listOfGyms = listOf<Gym>(
    Gym(1,"UpTown Gym", "123 Main St, Anytown USA"),
    Gym(2,"Central Fitness", "456 Elm St, Anytown USA"),
    Gym(3,"Sunset Gym", "789 Oak St, Anytown USA"),
    Gym(4,"City Gym", "321 Pine St, Anytown USA"),
    Gym(5,"Hillside Fitness", "654 Maple St, Anytown USA"),
)

data class Gym(val id: Int, val name: String, val place: String, var isFavorite: Boolean = false)