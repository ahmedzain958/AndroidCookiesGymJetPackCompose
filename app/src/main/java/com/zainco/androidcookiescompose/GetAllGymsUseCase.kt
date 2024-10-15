package com.zainco.androidcookiescompose

class GetAllGymsUseCase {
    private val gymsRepository = GymsRepository()

    suspend operator fun invoke(): List<Gym> {
        return gymsRepository.getGymsFromRemote().sortedBy {
            it.name
        }
    }
}