package com.example.ahbakken_oblig1.ui.quiz

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
            val falseButtonColor = ButtonDefaults.buttonColors(
                containerColor = Red,
                contentColor = MaterialTheme.colorScheme.surface
            )
            val trueButtonColor = ButtonDefaults.buttonColors(
                containerColor = Green,
                contentColor = MaterialTheme.colorScheme.surface
            )

            Button ( //TRUE BUTTON
                modifier = Modifier.padding(20.dp),
                enabled = enabled,
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
                },
                colors = trueButtonColor
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
                colors = falseButtonColor

            ) {
                Text(text = "False")
            }
        }
        Text(text = "You have $points points.")

    }
    Column(
        modifier = modifier.padding(bottom = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ){
        Button(
            modifier = Modifier,
            onClick = {
                points = 0
                counter = 0
                enabled = true
                question = firstQuestion
            }
        ) {
            Text(text = "Restart quiz")
        }
    }
}


data class Question(val question: String, val truthOrLie: Boolean)