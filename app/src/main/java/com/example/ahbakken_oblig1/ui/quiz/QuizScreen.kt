package com.example.ahbakken_oblig1.ui.quiz

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import com.example.ahbakken_oblig1.R


@Composable
fun QuizScreen(modifier: Modifier) { //is called in main
    var points by remember { mutableStateOf(0) }
    var counter by remember { mutableStateOf(0) }
    var enabled by remember { mutableStateOf(true) }

    val firstQuestion = Question(stringResource(R.string.q1), false)
    val secondQuestion = Question(stringResource(R.string.q2), true)
    val lastQuestion = Question(stringResource(R.string.q3), false)

    var question by remember { mutableStateOf( firstQuestion ) }
    Column(
        modifier = modifier.padding(start = 8.dp, end = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = question.question,
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
        )
        
        Row {
            Button ( //TRUE BUTTON
                modifier = Modifier.padding(20.dp),
                enabled = enabled,
                colors = ButtonDefaults.buttonColors(Color("#00AC05".toColorInt())),
                onClick = {
                    if (question.truthOrLie) { //if true
                        points++
                    }
                    if (question == lastQuestion) {
                        enabled = !enabled
                    } else {
                        question = when (counter) {
                            0 -> secondQuestion
                            else -> {lastQuestion}
                        }
                        counter++
                    }
                }
            ) {
                Text(text = "True")
            }


            Button(//FALSE BUTTON
                modifier = Modifier.padding(20.dp),
                enabled = enabled,
                onClick = {
                    if (!question.truthOrLie) { //if false
                        points++
                    }
                    if (question == lastQuestion) {
                        enabled = !enabled
                    } else {

                        question = when (counter) {
                            0 -> secondQuestion
                            else -> {lastQuestion}
                        }
                    }
                    counter++
                },
                colors = ButtonDefaults.buttonColors(Color("#AC0500".toColorInt())),

            ) {
                Text(text = "False")
            }
        }
        Text(
            text = "You have $points points.",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

    }
    Column (
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Button(
            modifier = Modifier
                .fillMaxWidth(1f),
            onClick = {
                points = 0
                counter = 0
                enabled = true
                question = firstQuestion },
            shape = RoundedCornerShape(0.dp),
        ) {
            Text(
                text = "Restart quiz  ",
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Icon(Icons.Rounded.Refresh, contentDescription = "Localized description")
        }
    }
}


data class Question(val question: String, val truthOrLie: Boolean)