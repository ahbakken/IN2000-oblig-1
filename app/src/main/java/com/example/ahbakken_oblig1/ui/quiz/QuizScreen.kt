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
import com.example.ahbakken_oblig1.R


@Composable
fun QuizScreen(modifier: Modifier) { //is called in main
    var points by remember { mutableStateOf(0) }
    var enabledButton by remember { mutableStateOf(true) }
    val question1 = Question(stringResource(R.string.q1), false)
    val question2 = Question(stringResource(R.string.q2), true)
    val question3 = Question(stringResource(R.string.q3), false)
    val questionList = listOf<Question>(question1, question2, question3)
    val questionState= QuizUiState(questionList)
    var question by remember { mutableStateOf(questionState.getQuestion()) }
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = question,
            textAlign = TextAlign.Center
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
                enabled = enabledButton,
                onClick = {
                    if (questionState.getAnswer()) { //if true
                        points++
                    }
                    questionState.increaseQuestionNumber()
                    if (questionState.questions.size == questionState.getQuestionNumber()) {
                        enabledButton = false
                    } else {
                        question = questionState.getQuestion()
                    }

                },
                colors = trueButtonColor
            ) {
                Text(text = "True")
            }

            Button(//FALSE BUTTON
                modifier = Modifier.padding(20.dp),
                onClick = {
                    if (!questionState.getAnswer()) { //if false
                        points++
                    }
                    questionState.increaseQuestionNumber()
                    if (questionState.questions.size == questionState.getQuestionNumber()) {
                        enabledButton = false
                    } else {
                        question = questionState.getQuestion()
                    }

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
                points = 0;
                questionState.resetQuiz();
                enabledButton = true
                question = questionState.getQuestion()
            }
        ) {
            Text(text = "Restart quiz")
        }
    }
}

data class QuizUiState( val questions: List<Question> ) {
    private var questionNumber: Int = 0
    fun increaseQuestionNumber() {
        if (questionNumber <= questions.size) {
            questionNumber++
        }

    }
    fun getQuestionNumber(): Int {
        return questionNumber
    }
    fun getQuestion(): String {
        return questions[questionNumber].question
    }
    fun getAnswer(): Boolean {
        return questions[questionNumber].truthOrLie
    }
    fun resetQuiz() {
        questionNumber = 0
    }
}

data class Question(val question: String, val truthOrLie: Boolean)