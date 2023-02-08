package com.example.ahbakken_oblig1.ui.palindrom

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ahbakken_oblig1.ui.theme.Ahbakken_oblig1Theme

@Composable
fun PalindromeScreen(modifier: Modifier, onNavigateToNext: () -> Unit) { //is called in main
    Column (
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ){
        PalindromeTest()
    }
    Column(
        modifier = modifier.padding(bottom = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom,
    ) {
        Text(text = "PalindromeScreen")
        Button(onClick = onNavigateToNext) {
            Text(text = "To the next screen")
        }

    }
}

@Composable
fun PalindromeTest() {
    Column(
        modifier = Modifier.padding(top = 34.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        var palindromeTest by remember { mutableStateOf("") }
        OutlinedTextField(
            value = palindromeTest,
            onValueChange = { palindromeTest = it },
            label = { Text("Insert a palindrome") }
        )
        var result by remember { mutableStateOf(false) }
        val focusManager = LocalFocusManager.current
        var inputText by remember { mutableStateOf("") }
        Button(onClick = {
            result = palindromeChecker(palindromeTest);
            focusManager.clearFocus();
            inputText = palindromeTest;
            palindromeTest = ""
        }) {
            Text(text = "Check for palindrome")
        }
        Box (
            modifier = Modifier.padding(top = 30.dp)
                ) {
            Text(
                modifier = Modifier.focusable(),
                text = "$inputText \nis a palindrome: \n$result",
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center

            )
        }

    }
}

fun palindromeChecker(inputString: String): Boolean {
    return inputString == inputString.reversed()
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Ahbakken_oblig1Theme {
        PalindromeTest()
    }
}