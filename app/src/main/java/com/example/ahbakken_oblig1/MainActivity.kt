package com.example.ahbakken_oblig1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ahbakken_oblig1.ui.palindrom.PalindromeScreen
import com.example.ahbakken_oblig1.ui.quiz.QuizScreen
import com.example.ahbakken_oblig1.ui.theme.Ahbakken_oblig1Theme
import com.example.ahbakken_oblig1.ui.unitconverter.UnitConverterScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Ahbakken_oblig1Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ScreenNavigator()
                }
            }
        }
    }
}

@Composable
fun ScreenNavigator() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "palindrome") {
        composable("palindrome") { PalindromeScreen(
            modifier = Modifier.fillMaxSize(),
            onNavigateToNext = {navController.navigate("converter")}
        ) }
        composable("converter") { UnitConverterScreen(
                modifier = Modifier.fillMaxSize(),
                onNavigateToNext = {navController.navigate("quiz")}
            ) }
        composable("quiz") { QuizScreen(
            modifier = Modifier.fillMaxSize()
        ) }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Ahbakken_oblig1Theme {
        ScreenNavigator()
    }
}