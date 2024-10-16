package com.zainco.androidcookiescompose.gyms.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.zainco.androidcookiescompose.gyms.presentation.details.GymDetailsScreen
import com.zainco.androidcookiescompose.gyms.presentation.gymslist.GymsScreen
import com.zainco.androidcookiescompose.gyms.presentation.gymslist.GymsViewModel
import com.zainco.androidcookiescompose.ui.theme.AndroidCookiesComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidCookiesComposeTheme {
                GymsAroundApp()
            }
        }
    }
}

@Composable
private fun GymsAroundApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "gyms") {
        composable(route = "gyms") {
            val vm: GymsViewModel = viewModel()
            GymsScreen(vm.state.value, { id ->
                navController.navigate("gyms/$id")
            }, onFavouriteIconClick = { id: Int, oldValue: Boolean ->
                vm.toggleFavState(id, oldValue)
            })
        }

        composable(
            route = "gyms/{gym_id}", arguments = listOf(
                navArgument("gym_id") {
                    type = NavType.IntType
                }), deepLinks = listOf(
                navDeepLink {
                    uriPattern = "https://www.gymsaround.com/details/{gym_id}"

                }
            )
        ) { it: androidx.navigation.NavBackStackEntry ->
            GymDetailsScreen()
        }
    }
}

/*
@Preview
@Composable
fun myBoxLayout() {
    Box(
        modifier = Modifier
            .size(120.dp)
            .background(Color.Black)
            .padding(10.dp)
            .clip(RoundedCornerShape(size = 10.dp))
            .background(Color.LightGray)
    ){
        Text(text = " Hello ", Modifier.align(Alignment.TopStart))
        Text(text = " Android", Modifier.align(Alignment.Center))
        Text(text = " Cooki", Modifier.align(Alignment.BottomEnd))
    }
}

@Preview(showBackground = true)
@Composable
fun MyText() {
    Text(
        text = " s",
        style = TextStyle(
            color = Color.Red,
            fontSize = 24.sp,
            fontStyle = FontStyle.Italic,
            fontFamily = FontFamily.SansSerif,
            textAlign = TextAlign.Center
        ), maxLines = 2
    )
}

@Preview(showBackground = true)
@Composable
fun MyButton() {
    var buttonIsEnabled by remember { mutableStateOf(true) }

    Button(
        onClick = { buttonIsEnabled = !buttonIsEnabled },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Black,
            contentColor = Color.White,
            disabledContainerColor = Color.LightGray,
        ),
        enabled = buttonIsEnabled
    ) {

        Text(text = if (buttonIsEnabled) "Click Me" else "I'm Disabled")
    }
}

@Preview(name = "TextField",showBackground = true)
@Composable
fun MyTextField() {
    var emailAddress by remember { mutableStateOf("") }

    TextField(value = emailAddress, onValueChange = {
        emailAddress = it
    },
        label = {
            Text(text = "Email Address")
        })
}

@Preview(name = "Image",showBackground = true)
@Composable
fun MyImage() {
    Image(
        painter = painterResource(id = R.drawable.ic_launcher_background),
        contentDescription = "Image"
    )
}*/
