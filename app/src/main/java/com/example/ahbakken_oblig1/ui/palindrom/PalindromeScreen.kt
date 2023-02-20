package com.example.ahbakken_oblig1.ui.palindrom

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun PalindromeScreen(modifier: Modifier, onNavigateToNext: () -> Unit) { //is called in main
    Column (
        modifier = modifier.padding(top = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top

    ){
        var palindromeTest by remember { mutableStateOf("") }
        OutlinedTextField(
            value = palindromeTest,
            onValueChange = { palindromeTest = it },
            label = { Text("Insert a palindrome") },
        )
        var result by remember { mutableStateOf(false) }
        val focusManager = LocalFocusManager.current
        var inputText by remember { mutableStateOf("") }
        Button(onClick = {
            result = palindromeChecker(palindromeTest)
            focusManager.clearFocus()
            inputText = palindromeTest
            palindromeTest = ""
        }) {
            Text(text = "Check for palindrome")
        }
        Box (
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(
                modifier = Modifier.focusable(),
                text = "$inputText \nis a palindrome: \n$result",
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center

            )
        }
    }

    Column (
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
            ) {
        Button(
            modifier = Modifier
                .fillMaxWidth(1f),
            onClick = { onNavigateToNext() },
            shape = RoundedCornerShape(0.dp),
        ) {
            Text(
                text = "To the next screen  ",
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Icon(Icons.Rounded.ArrowForward, contentDescription = "Localized description")
        }
    }
}
fun palindromeChecker(inputString: String): Boolean { // Anna is true with .lowercase()
    return inputString.lowercase() == inputString.lowercase().reversed()
}