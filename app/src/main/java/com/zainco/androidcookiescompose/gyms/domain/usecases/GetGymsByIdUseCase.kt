package com.zainco.androidcookiescompose.gyms.domain.usecases

import com.zainco.androidcookiescompose.gyms.data.GymsRepository
import com.zainco.androidcookiescompose.gyms.data.remote.RemoteGym
import javax.inject.Inject

class GetGymsByIdUseCase @Inject constructor(private val gymsRepository: GymsRepository) {
    suspend operator fun invoke(id: Int): Map<String, RemoteGym> {
        return gymsRepository.getGymById(id)
    }
}