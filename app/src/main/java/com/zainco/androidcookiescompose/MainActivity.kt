package com.zainco.androidcookiescompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zainco.androidcookiescompose.ui.theme.AndroidCookiesComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidCookiesComposeTheme{
                GymsScreen()
            }
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
