package com.zainco.androidcookiescompose

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ScoreSection(score: Int, countCallBack: () -> Unit) {
    Column {
        Text(text = "$score")
        Button(onClick = { countCallBack() }) {
            Text(text = "Add Points")
        }
    }
}

/*@Preview(name = "Preview 1", showSystemUi = true,
    showBackground = true, device = Devices.NEXUS_5)
@Composable
fun PreviewScoreSection() {
    ScoreSection(score = 450, countCallBack = {})
}

@Preview(name = "Preview 2", showBackground = true)
@Composable
fun PreviewScoreSection2() {
    var score  by remember { mutableStateOf(0) }
    ScoreSection(score = score, countCallBack = {
        score+=2
    })
}*/

@Preview(name = "Preview 2", showBackground = true)
@Composable
fun PreviewScoreSection2() {
    var score  by remember { mutableStateOf(0) }
    ScoreSection(score = score, countCallBack = {
        score+=2
    })
}