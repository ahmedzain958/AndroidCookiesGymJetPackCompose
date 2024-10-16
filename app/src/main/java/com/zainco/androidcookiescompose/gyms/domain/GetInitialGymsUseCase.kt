package com.zainco.androidcookiescompose.gyms.domain

import com.zainco.androidcookiescompose.gyms.data.GymsRepository

class GetInitialGymsUseCase {
    private val gymsRepository = GymsRepository()
    private val getSortedGymsUseCase = GetSortedGymsUseCase()

    suspend operator fun invoke(): List<Gym> {
        gymsRepository.loadGyms()
        return getSortedGymsUseCase()
    }
}