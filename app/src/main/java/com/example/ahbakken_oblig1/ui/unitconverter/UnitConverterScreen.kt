package com.example.ahbakken_oblig1.ui.unitconverter

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@Composable
fun UnitConverterScreen(modifier: Modifier, onNavigateToNext: () -> Unit) { //is called in main
    ConverterInput()
    Column(
        modifier = modifier.padding(bottom = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Text(text = "UnitConverterScreen")
        Button(onClick = onNavigateToNext) {
            Text(text = "To the next screen")
        }
    }
}

@Composable
fun ConverterInput() {
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(34.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        var convertInput by remember { mutableStateOf("") }
        var result by remember { mutableStateOf(0.00) }
        val focusManager = LocalFocusManager.current
        var inputText by remember { mutableStateOf("") }

        OutlinedTextField(
            value = convertInput,
            onValueChange = { convertInput = it },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            label = { Text("Volume") }
        )


        val options = listOf( "Fluid ounce", "Cup", "Gallon", "Hogshead" )
        var expanded by remember { mutableStateOf(false) }
        var imperialUnit by remember { mutableStateOf("") }

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            TextField(
                modifier = Modifier.menuAnchor(),
                value = imperialUnit,
                onValueChange = { imperialUnit = it },
                label = { Text("Choose imperial unit") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                colors = ExposedDropdownMenuDefaults.textFieldColors(),
            )
            val filteringOptions = options.filter { it.contains(imperialUnit, ignoreCase = true) }
            if (filteringOptions.isNotEmpty()) {
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = !expanded },
                ) {
                    filteringOptions.forEach { selectionOption ->
                        DropdownMenuItem(
                            text = { Text(selectionOption) },
                            onClick = {
                                imperialUnit = selectionOption
                                expanded = false
                                focusManager.clearFocus()
                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                        )
                    }
                }
            }
        } //dropdown menu end

        val snackbarHostState = remember { SnackbarHostState() }
        val scope = rememberCoroutineScope()
        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            floatingActionButton = {
                Button(
                    onClick = {
                        if (imperialUnit in options && !convertInput.isNullOrEmpty() && convertInput.matches(Regex("\\d+")) )
                        {
                            result = unitConverter(convertInput.toInt(), imperialUnit)
                            inputText = convertInput
                            convertInput = ""
                        }
                        // show snack-bar as a suspend function
                        else {
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    "Insert Volume and Imperial Unit"
                                )
                            }
                        }
                        focusManager.clearFocus()
                        imperialUnit = ""
                    },
                    modifier = Modifier.padding(bottom = 350.dp),
                ) { Text("Convert") }
            },
            content = { innerPadding ->
                Text(
                    modifier = Modifier.padding(innerPadding).fillMaxSize().wrapContentSize(),
                    text = "You have $result liters",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
        )

    } //Column end
}//function end


fun unitConverter(numberInput: Int, from: String): Double {

    val result = when (from) {
        "Fluid ounce" ->  numberInput*0.02957 // fluid ounce
        "Cup" ->  numberInput*0.23659 // cup
        "Gallon" ->  numberInput*3.78541 // gallon
        "Hogshead" ->  numberInput*238.481 //hogshead
        else -> -1.00
    }
    return String.format("%.2f", result).toDouble()
}