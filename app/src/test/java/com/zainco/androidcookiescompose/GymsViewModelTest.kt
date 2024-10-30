package com.zainco.androidcookiescompose

import com.zainco.androidcookiescompose.gyms.data.GymsRepository
import com.zainco.androidcookiescompose.gyms.data.local.GymsDao
import com.zainco.androidcookiescompose.gyms.data.local.LocalGym
import com.zainco.androidcookiescompose.gyms.data.local.LocalGymFavouriteState
import com.zainco.androidcookiescompose.gyms.data.remote.GymsApiService
import com.zainco.androidcookiescompose.gyms.data.remote.RemoteGym
import com.zainco.androidcookiescompose.gyms.domain.usecases.GetInitialGymsUseCase
import com.zainco.androidcookiescompose.gyms.domain.usecases.GetSortedGymsUseCase
import com.zainco.androidcookiescompose.gyms.domain.usecases.ToggleFavouriteStateUseCase
import com.zainco.androidcookiescompose.gyms.presentation.gymslist.GymScreenState
import com.zainco.androidcookiescompose.gyms.presentation.gymslist.GymsViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Test

@ExperimentalCoroutinesApi
class GymsViewModelTest {

    private val dispatcher = StandardTestDispatcher()
    private val scope = TestScope(dispatcher)


    @Test
    fun loadingState_isSetCorrectly() = scope.runTest{
        val viewModel = getViewModel()
        val state = viewModel.state.value
        assert(state == GymScreenState(emptyList(), true, null))
    }

    private fun getViewModel(): GymsViewModel {
        val gymsRepository = GymsRepository(TestGymsApiService(), TestGymsDao(), dispatcher)
        val getStoredGymsUseCase = GetSortedGymsUseCase(gymsRepository)
        val toggleFavouriteStateUseCase =
            ToggleFavouriteStateUseCase(gymsRepository, getStoredGymsUseCase)
        val getInitialGymsUseCase = GetInitialGymsUseCase(gymsRepository, getStoredGymsUseCase)
        return GymsViewModel(getInitialGymsUseCase, toggleFavouriteStateUseCase, dispatcher)
    }

    class TestGymsApiService : GymsApiService {
        private fun getDummyGymsList() = arrayListOf(
            RemoteGym(0, "n0", "p0", false),
            RemoteGym(1, "n1", "p1", false),
            RemoteGym(2, "n2", "p2", false),
            RemoteGym(3, "n3", "p3", false),
            RemoteGym(4, "n4", "p4", false),
        )

        override suspend fun getGyms(): List<RemoteGym> {
            return getDummyGymsList()
        }

        override suspend fun getGymById(id: Int): Map<String, RemoteGym> {
            TODO("Not yet implemented")
        }
    }

    class TestGymsDao : GymsDao {
        private val gyms = HashMap<Int, LocalGym>()
        override suspend fun getAll(): List<LocalGym> {
            return gyms.values.toList()
        }

        override suspend fun addAll(gyms: List<LocalGym>) {
            gyms.forEach {
                this.gyms[it.id] = it
            }
        }

        override suspend fun update(localGymFavouriteState: LocalGymFavouriteState) {
            TODO("Not yet implemented")
        }

        override suspend fun getFavouriteGyms(): List<LocalGym> {
            return gyms.values.toList().filter { it: LocalGym ->
                it.isFavorite
            }
        }

        override suspend fun updateAll(localGymFavouriteStates: List<LocalGymFavouriteState>) {
            localGymFavouriteStates.forEach {
                updateGym_AlternativeFnForRoomUpdating(it)
            }
        }

        private fun updateGym_AlternativeFnForRoomUpdating(gymFavouriteState: LocalGymFavouriteState) {
            val gym = gyms[gymFavouriteState.id]
            gym?.let {
                this.gyms[gymFavouriteState.id] = it.copy(isFavorite = gymFavouriteState.isFavorite)
            }

        }
    }

}
