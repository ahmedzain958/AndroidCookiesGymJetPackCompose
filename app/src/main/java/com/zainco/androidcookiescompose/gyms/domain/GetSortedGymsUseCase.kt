package com.zainco.androidcookiescompose.gyms.domain

import com.zainco.androidcookiescompose.gyms.data.GymsRepository
import javax.inject.Inject

class GetSortedGymsUseCase @Inject constructor(private val gymsRepository : GymsRepository) {


    suspend operator fun invoke(): List<Gym> {
        return gymsRepository.getGyms().sortedBy {
            it.name
        }
    }
}