package com.zainco.androidcookiescompose

import com.zainco.androidcookiescompose.gyms.data.GymsRepository
import com.zainco.androidcookiescompose.gyms.domain.usecases.GetInitialGymsUseCase
import com.zainco.androidcookiescompose.gyms.domain.usecases.GetSortedGymsUseCase
import com.zainco.androidcookiescompose.gyms.domain.usecases.ToggleFavouriteStateUseCase
import com.zainco.androidcookiescompose.gyms.presentation.gymslist.GymsViewModel
import org.junit.Test

class GymsViewModelTest {
    @Test
    fun loadingState_isSetCorrectly(){
        val viewModel = getViewModel()
    }

    private fun getViewModel(): GymsViewModel {
       /* val gymsRepository = GymsRepository()
        val getStoredGymsUseCase = GetSortedGymsUseCase()
        val toggleFavouriteStateUseCase = ToggleFavouriteStateUseCase()
        val getInitialGymsUseCase = GetInitialGymsUseCase()
        return GymsViewModel()*/
    }
}
