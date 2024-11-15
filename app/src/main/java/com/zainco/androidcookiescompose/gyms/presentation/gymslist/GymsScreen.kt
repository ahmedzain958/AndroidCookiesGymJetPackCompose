package com.zainco.androidcookiescompose.gyms.presentation.gymslist

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zainco.androidcookiescompose.R
import com.zainco.androidcookiescompose.gyms.domain.Gym
import com.zainco.androidcookiescompose.gyms.presentation.SemanticDescription
import com.zainco.androidcookiescompose.ui.theme.Purple80

@Composable
fun GymsScreen(
    state: GymScreenState,
    onItemClick: (Int) -> Unit,
    onFavouriteIconClick: (Int, Boolean) -> Unit,
) {
    var query by remember { mutableStateOf("") } // Holds the search query
    var isFavourite by remember { mutableStateOf(CHOICE.ALL) } // Holds the search query
    val filteredGyms = remember(state.gymsList, query, isFavourite) {
        state.gymsList.filter { gym ->
            // Apply the search query filter if `query` is not blank
            (query.isBlank() || gym.name.startsWith(query, ignoreCase = true)) &&
                    // Apply the favourite filter
                    when (isFavourite) {
                        CHOICE.ALL -> true
                        CHOICE.IS_FAVOURITE -> gym.isFavorite
                        CHOICE.NOT_FAVOURITE -> !gym.isFavorite
                    }
        }
    }

    Box(
        contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()
    ) {
        if (state.isLoading) CircularProgressIndicator(Modifier.semantics {
            this.contentDescription = SemanticDescription.GYMS_LIST_LOADING
        })
        state.error?.let {
            Text(it)
        }
        Column {
            DropDown { choice: CHOICE ->
                isFavourite = choice // Update the query on text change
            }
            Spacer(modifier = Modifier.size(16.dp))
            SearchBar { updatedQuery ->
                query = updatedQuery // Update the query on text change
            }
            Spacer(modifier = Modifier.size(16.dp))
            LazyColumn() {
                items(filteredGyms) { gym ->
                    GymItem(gym, onFavouriteIconClick = { id: Int, oldValue: Boolean ->
                        onFavouriteIconClick.invoke(id, oldValue)
                    }) { id ->
                        onItemClick(id)
                    }
                }
            }
        }
    }

}


@Composable
fun SearchBar(onTextChanged: (String) -> Unit) {
    var query by remember {
        mutableStateOf("")
    }
    TextField(
        value = query,
        onValueChange = {
            query = it
            onTextChanged.invoke(it)
        },
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(32.dp),
        leadingIcon = {
            Image(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        },
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedContainerColor = Color.LightGray.copy(alpha = 0.3f),
            unfocusedContainerColor = Color.LightGray.copy(alpha = 0.3f),
        ),
        placeholder = {
            Text(
                text = "Search for GYMS",
                style = MaterialTheme.typography.bodySmall
            )
        }
    )

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
@Preview(showBackground = true)
@Composable
fun ProfileHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.align(Alignment.CenterStart)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_profile),
                contentDescription = null,
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = "Hello,", style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "John Doe",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
        Image(
            painter = painterResource(id = R.drawable.ic_notification),
            contentDescription = null,
            modifier = Modifier
                .size(48.dp)
                .align(Alignment.CenterEnd)
                .clip(CircleShape)
                .background(Color.LightGray.copy(alpha = 0.3f))
                .padding(8.dp),
            contentScale = ContentScale.Inside
        )
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

enum class CHOICE(val value: String) {
    IS_FAVOURITE("IsFavourite"),
    NOT_FAVOURITE("NOT_FAVOURITE"),
    ALL("")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDown(onDropDownItemSelected: (CHOICE) -> Unit) {
    var isExpanded by remember {
        mutableStateOf(false)
    }
    var choice by remember {
        mutableStateOf(CHOICE.ALL.value)
    }
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        ExposedDropdownMenuBox(
            expanded = isExpanded,
            onExpandedChange = { isExpanded = !isExpanded }) {
            TextField(
                value = choice,
                onValueChange = {
                    choice = it
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
                    Text(text = CHOICE.ALL.value)
                }, onClick = {
                    choice = CHOICE.ALL.value
                    isExpanded = false
                    onDropDownItemSelected(CHOICE.ALL)
                })
                DropdownMenuItem(text = {
                    Text(text = "IsFavourite")
                }, onClick = {
                    choice = CHOICE.IS_FAVOURITE.value
                    isExpanded = false
                    onDropDownItemSelected(CHOICE.IS_FAVOURITE)
                })
                DropdownMenuItem(text = {
                    Text(text = "NotIsFavourite")
                }, onClick = {
                    choice = CHOICE.NOT_FAVOURITE.value
                    isExpanded = false
                    onDropDownItemSelected(CHOICE.NOT_FAVOURITE)
                })
            }
        }
    }
}

