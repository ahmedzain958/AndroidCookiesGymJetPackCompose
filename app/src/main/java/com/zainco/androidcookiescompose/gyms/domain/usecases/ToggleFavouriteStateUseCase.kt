package com.zainco.androidcookiescompose.gyms.domain.usecases

import com.zainco.androidcookiescompose.gyms.data.GymsRepository
import com.zainco.androidcookiescompose.gyms.domain.Gym
import javax.inject.Inject

class ToggleFavouriteStateUseCase @Inject constructor(
    private val gymsRepository: GymsRepository,
    private val getSortedGymsUseCase: GetSortedGymsUseCase,
) {

    suspend operator fun invoke(id: Int, oldState: Boolean): List<Gym> {
        val newState = oldState.not()
        gymsRepository.toggleFavouriteGym(id, newState)
        return getSortedGymsUseCase()
    }
}