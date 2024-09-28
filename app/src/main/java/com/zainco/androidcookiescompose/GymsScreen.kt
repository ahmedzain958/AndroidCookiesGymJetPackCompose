package com.zainco.androidcookiescompose

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zainco.androidcookiescompose.ui.theme.AndroidCookiesComposeTheme
import com.zainco.androidcookiescompose.ui.theme.Purple80
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color.Companion.DarkGray

@Composable
fun GymsScreen() {
    val viewModel: GymsViewModel = viewModel()
    LazyColumn() {
        items(viewModel.getGyms()) { gym ->
            GymItem(gym)
        }
    }
}

@Composable
fun GymItem(gym: Gym) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        modifier = Modifier
            .padding(8.dp),

        ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(8.dp)) {
            GymIcon(Icons.Filled.Place, Modifier.weight(0.15f))
            GymDetails(gym, Modifier.weight(0.70f))
            FavoriteIcon(Modifier.weight(0.15f))
        }
    }
}

@Composable
fun FavoriteIcon(modifier: Modifier) {
    var isFavoriteState by remember {
        mutableStateOf(false)
    }
    val icon = if (isFavoriteState) {
        Icons.Filled.Favorite
    } else {
        Icons.Filled.FavoriteBorder
    }
    Image(
        imageVector = icon,
        contentDescription = "Favorite Gym Icon",
        modifier = modifier
            .padding(8.dp)
            .clickable {
                isFavoriteState = !isFavoriteState

            },
        colorFilter = ColorFilter.tint(Color.Red),
    )
}

@Composable
fun GymDetails(gym: Gym, modifier: Modifier) {
    Column(modifier) {
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

@Composable
fun GymIcon(vector: ImageVector, modifier: Modifier) {
    Image(
        imageVector = vector,
        contentDescription = "Gym icon",
        modifier = modifier,
        colorFilter = ColorFilter.tint(Color.DarkGray)
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewGymsScreen() {
    AndroidCookiesComposeTheme {
        val viewModel: GymsViewModel = viewModel()
        LazyColumn() {
            items(viewModel.getGyms()) { gym ->
                GymItem(gym)
            }
        }
    }
}
