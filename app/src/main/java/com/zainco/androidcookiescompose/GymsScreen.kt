package com.zainco.androidcookiescompose

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zainco.androidcookiescompose.ui.theme.Purple80

@Composable
fun GymsScreen(onItemClick: (Int) -> Unit) {
    val viewModel: GymsViewModel = viewModel()
    val state = viewModel.state.value
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        LazyColumn() {
            items(state.gymsList) { gym ->
                GymItem(gym, onFavouriteIconClick = {
                    viewModel.toggleFavState(gym.id)
                }) { id ->
                    onItemClick(id)
                }
            }
        }
        if (state.isLoading)
            CircularProgressIndicator()
        state.error?.let {
            Text(it)
        }
    }

}

@Composable
fun GymItem(
    gym: Gym,
    onFavouriteIconClick: (Int) -> Unit,
    onItemClick: (Int) -> Unit,
) {
    val icon = if (gym.isFavorite) {
        Icons.Filled.Favorite
    } else {
        Icons.Filled.FavoriteBorder
    }
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        modifier = Modifier
            .padding(8.dp)
            .clickable { onItemClick(gym.id) },

        ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(8.dp)) {
            DefaultIcon(Icons.Filled.Place, Modifier.weight(0.15f), "Location Icon")
            GymDetails(gym, Modifier.weight(0.70f))
            DefaultIcon(icon, Modifier.weight(0.15f), "Favorite Gym Icon") {
                onFavouriteIconClick(gym.id)
            }
        }
    }
}

@Composable
fun DefaultIcon(
    icon: ImageVector,
    modifier: Modifier,
    contentDescription: String,
    onClick: () -> Unit = {},
) {
    Image(
        imageVector = icon,
        contentDescription = contentDescription,
        modifier = modifier
            .padding(8.dp)
            .clickable {
                onClick()
            },
        colorFilter = ColorFilter.tint(Color.DarkGray),
    )
}

@Composable
fun GymDetails(
    gym: Gym, modifier: Modifier,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
) {
    Column(modifier, horizontalAlignment = horizontalAlignment) {
        Text(
            text = "UpTown Gym",
            style = MaterialTheme.typography.titleSmall,
            color = Purple80
        )
        CompositionLocalProvider(
            LocalContentColor provides MaterialTheme.colorScheme.secondaryContainer
        ) {
            Text(
                text = gym.place,
                style = MaterialTheme.typography.bodySmall,
                color = DarkGray
            )
        }

    }
}

