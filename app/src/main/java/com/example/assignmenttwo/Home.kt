package com.example.assignmenttwo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun MainFeature(
    onMusicPlay: () -> Unit,
    onMusicSeek: (Int) -> Unit,
    onMusicStop: () -> Unit,
    getMusicDuration: () -> Int
) {
    // State variables for CheckBox, RadioButton, RatingBar, Slider, and Switch
    var selectedCheckBoxes by remember { mutableStateOf(mutableListOf<String>()) }
    var selectedRadioOption by remember { mutableStateOf("Option 1") }
    var rating by remember { mutableStateOf(3f) }
    var sliderValue by remember { mutableStateOf(0f) }
    var isSwitched by remember { mutableStateOf(false) }

    LaunchedEffect(isSwitched) {
        if (isSwitched) {
            onMusicPlay()
        } else {
            onMusicStop()
        }
    }

    LaunchedEffect(sliderValue) {
        val duration = getMusicDuration()
        val seekPosition = (sliderValue * duration).toInt()
        onMusicSeek(seekPosition)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // CheckBox
        Text(text = "Select Multiple Options", fontWeight = FontWeight.Bold)
        CheckBoxGroup(
            selectedOptions = selectedCheckBoxes,
            onSelectionChanged = { selectedCheckBoxes = it.toMutableList() }
        )
        Text(text = "Selected CheckBoxes: ${selectedCheckBoxes.joinToString()}", modifier = Modifier.background(
            Color.Green))

        // RadioButton Group
        Text(text = "Choose an Option", fontWeight = FontWeight.Bold)
        RadioButtonGroup(selectedOption = selectedRadioOption, onOptionSelected = { selectedRadioOption = it })
        Text(text = "Selected Radio Option: $selectedRadioOption", modifier = Modifier.background(
            Color.Magenta))

        // RatingBar
        Text(text = "Rating: ${rating.toInt()} stars")
        RatingBar(rating = rating, onRatingChanged = { rating = it })

        // Switch to play/stop music
        Row(verticalAlignment = Alignment.CenterVertically) {
            Switch(
                checked = isSwitched,
                onCheckedChange = { isSwitched = it }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = if (isSwitched) "Music Playing" else "Music OFF")
        }
        Slider(
            value = sliderValue,
            onValueChange = { sliderValue = it },
            valueRange = 0f..1f,
            steps = 1000
        )


    }
}

@Composable
fun CheckBoxGroup(
    selectedOptions: List<String>,
    onSelectionChanged: (List<String>) -> Unit
) {
    val options = listOf("Song 1", "Song 2", "Song 3")
    Column {
        options.forEach { option ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = selectedOptions.contains(option),
                    onCheckedChange = {
                        val newSelections = selectedOptions.toMutableList()
                        if (it) newSelections.add(option) else newSelections.remove(option)
                        onSelectionChanged(newSelections)
                    }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = option)
            }
        }
    }
}

@Composable
fun RadioButtonGroup(selectedOption: String, onOptionSelected: (String) -> Unit) {
    val options = listOf("Gaana 1", "Gaana 2", "Gaana 3")
    Column {
        options.forEach { option ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = (selectedOption == option),
                    onClick = { onOptionSelected(option) }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = option)
            }
        }
    }
}

@Composable
fun RatingBar(rating: Float, onRatingChanged: (Float) -> Unit) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Create a total of 5 stars
        for (i in 1..5) {
            val filled = i <= rating // Full star condition
            IconButton(onClick = { onRatingChanged(i.toFloat()) }) {
                Icon(
                    imageVector = if (filled) androidx.compose.material.icons.Icons.Filled.Star else androidx.compose.material.icons.Icons.Outlined.Star,
                    contentDescription = "Rating Star"
                )
            }
        }
    }
}

