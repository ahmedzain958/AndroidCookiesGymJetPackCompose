package com.zainco.androidcookiescompose.gyms.domain

import com.zainco.androidcookiescompose.gyms.data.GymsRepository

class GetSortedGymsUseCase {
    private val gymsRepository = GymsRepository()

    suspend operator fun invoke(): List<Gym> {
        return gymsRepository.getGyms().sortedBy {
            it.name
        }
    }
}