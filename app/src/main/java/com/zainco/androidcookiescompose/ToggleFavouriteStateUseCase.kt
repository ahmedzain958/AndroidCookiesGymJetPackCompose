package com.zainco.androidcookiescompose

class ToggleFavouriteStateUseCase {
    private val gymsRepository = GymsRepository()

    suspend operator fun invoke(id: Int, oldState: Boolean): List<Gym> {
        val newState = oldState.not()
        return gymsRepository.toggleFavouriteGym(id, newState)
    }
}