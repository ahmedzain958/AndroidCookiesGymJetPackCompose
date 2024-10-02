package com.zainco.androidcookiescompose

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun GymDetailsScreen(){
    val viewModel: GymsDetailsViewModel = viewModel()
    val item = viewModel.state.value

    item?.let {

    }
}