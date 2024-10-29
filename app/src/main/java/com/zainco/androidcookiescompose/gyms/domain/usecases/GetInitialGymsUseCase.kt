package com.zainco.androidcookiescompose.gyms.domain.usecases

import com.zainco.androidcookiescompose.gyms.data.GymsRepository
import com.zainco.androidcookiescompose.gyms.domain.Gym
import javax.inject.Inject

class GetInitialGymsUseCase @Inject constructor(
    private val gymsRepository: GymsRepository,
    private val getSortedGymsUseCase: GetSortedGymsUseCase,
) {


    suspend operator fun invoke(): List<Gym> {
        gymsRepository.loadGyms()
        return getSortedGymsUseCase()
    }
}