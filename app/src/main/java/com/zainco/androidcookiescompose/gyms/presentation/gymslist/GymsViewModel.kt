package com.zainco.androidcookiescompose.gyms.presentation.gymslist

import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zainco.androidcookiescompose.gyms.data.di.MainDispatcher
import com.zainco.androidcookiescompose.gyms.domain.usecases.GetInitialGymsUseCase
import com.zainco.androidcookiescompose.gyms.domain.usecases.ToggleFavouriteStateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GymsViewModel @Inject constructor(
    private val getInitialGymsUseCase: GetInitialGymsUseCase,
    private val toggleFavouriteStateUseCase: ToggleFavouriteStateUseCase,
    @MainDispatcher private val dispatcher: CoroutineDispatcher,
) : ViewModel() {
    private var _state by mutableStateOf(GymScreenState(emptyList(), true))
    val state: State<GymScreenState>
        get() = derivedStateOf { _state }


    private var coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
        _state = _state.copy(error = throwable.message, isLoading = false)
    }

    init {
        getGyms()
    }

    private fun getGyms() {
        viewModelScope.launch(coroutineExceptionHandler + dispatcher) {
            val receivedGyms = getInitialGymsUseCase()
            _state = _state.copy(gymsList = receivedGyms, isLoading = false)
        }
    }


    fun toggleFavState(gymId: Int, oldValue: Boolean) {
        viewModelScope.launch(coroutineExceptionHandler + dispatcher) {
            val updatedGymsList = toggleFavouriteStateUseCase(gymId, oldValue)
            _state = _state.copy(gymsList = updatedGymsList)
        }
    }
}