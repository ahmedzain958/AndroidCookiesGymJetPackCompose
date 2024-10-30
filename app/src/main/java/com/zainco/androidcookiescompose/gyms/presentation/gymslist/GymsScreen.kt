package com.zainco.androidcookiescompose.gyms.presentation.gymslist

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
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zainco.androidcookiescompose.gyms.domain.Gym
import com.zainco.androidcookiescompose.gyms.presentation.SemanticDescription
import com.zainco.androidcookiescompose.ui.theme.Purple80

@Composable
fun GymsScreen(
    state: GymScreenState,
    onItemClick: (Int) -> Unit,
    onFavouriteIconClick: (Int, Boolean) -> Unit,
) {
    Box(
        contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()
    ) {
        LazyColumn() {
            items(state.gymsList) { gym ->
                GymItem(gym, onFavouriteIconClick = { id: Int, oldValue: Boolean ->
                    onFavouriteIconClick.invoke(id, oldValue)
                }) { id ->
                    onItemClick(id)
                }
            }
        }
        if (state.isLoading) CircularProgressIndicator(Modifier.semantics {
            this.contentDescription = SemanticDescription.GYMS_LIST_LOADING
        })
        state.error?.let {
            Text(it)
        }
    }

}

@Composable
fun GymItem(
    gym: Gym,
    onFavouriteIconClick: (Int, Boolean) -> Unit,
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
                onFavouriteIconClick(gym.id, gym.isFavorite)
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
            text = gym.name, style = MaterialTheme.typography.titleSmall, color = Purple80
        )
        CompositionLocalProvider(
            LocalContentColor provides MaterialTheme.colorScheme.secondaryContainer
        ) {
            Text(
                text = gym.place, style = MaterialTheme.typography.bodySmall, color = DarkGray
            )
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun PreviewDropDown() {
    var isExpanded by remember {
        mutableStateOf(false)
    }
    var gender by remember {
        mutableStateOf("")
    }

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        ExposedDropdownMenuBox(
            expanded = isExpanded,
            onExpandedChange = { isExpanded = !isExpanded }) {
            TextField(
                value = gender,
                onValueChange = {
                    gender = it
                },
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
                },
                colors = ExposedDropdownMenuDefaults.textFieldColors(),
                modifier = Modifier.menuAnchor()
            )
            ExposedDropdownMenu(expanded = isExpanded, onDismissRequest = {}) {
                DropdownMenuItem(text = {
                    Text(text = "")
                }, onClick = {
                    gender = ""
                    isExpanded = false
                })
                DropdownMenuItem(text = {
                    Text(text = "Male")
                }, onClick = {
                    gender = "Male"
                    isExpanded = false
                })
                DropdownMenuItem(text = {
                    Text(text = "Female")
                }, onClick = {
                    gender = "Female"
                    isExpanded = false
                })
                DropdownMenuItem(text = {
                    Text(text = "Other")
                }, onClick = {
                    gender = "Other"
                    isExpanded = false
                })
            }
        }
    }
}


