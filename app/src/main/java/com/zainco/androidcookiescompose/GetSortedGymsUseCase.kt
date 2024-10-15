package com.zainco.androidcookiescompose

class GetSortedGymsUseCase {
    private val gymsRepository = GymsRepository()

    suspend operator fun invoke(): List<Gym> {
        return gymsRepository.getGyms().sortedBy {
            it.name
        }
    }
}