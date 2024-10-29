package com.zainco.androidcookiescompose.gyms.presentation.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zainco.androidcookiescompose.gyms.presentation.gymslist.DefaultIcon
import com.zainco.androidcookiescompose.gyms.presentation.gymslist.GymDetails

@Preview
@Composable
fun GymDetailsScreen() {
    val viewModel: GymsDetailsViewModel = hiltViewModel()
    val item = viewModel.state.value

    item?.let {
        Column (horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize().padding(16.dp)){
            DefaultIcon(
                icon = Icons.Filled.Place, modifier =
                Modifier.padding(32.dp),
                contentDescription = "Location Icon"
            )
            GymDetails(
                gym = it,
                modifier = Modifier.padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            )
            Text(
                text = if (it.isOpen) "Gym is Open" else "Gym is Closed",
                color = if (it.isOpen) Color.Green else Color.Red
            )
        }
    }
}