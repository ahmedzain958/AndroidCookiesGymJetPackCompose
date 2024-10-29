package com.zainco.androidcookiescompose.gyms.domain.usecases

import com.zainco.androidcookiescompose.gyms.data.GymsRepository
import com.zainco.androidcookiescompose.gyms.domain.Gym
import javax.inject.Inject

class GetSortedGymsUseCase @Inject constructor(private val gymsRepository : GymsRepository) {


    suspend operator fun invoke(): List<Gym> {
        return gymsRepository.getGyms().sortedBy {
            it.name
        }
    }
}