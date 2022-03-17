package com.compose.numberguessinggame

import android.os.Bundle
import android.util.Size
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.compose.numberguessinggame.ui.theme.NumberGuessingGameTheme
import kotlin.random.Random.Default.nextInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NumberGuessingGameTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    var random: Int = nextInt(1, 1000)
                    GuessingGame(random)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Composable
fun GuessingGame(random: Int) {
    var randNumber = remember { mutableStateOf(random) }
    var output = remember { mutableStateOf("") }
    var count = remember { mutableStateOf(0) }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Guess the number (1-1000)",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        )

        var text by remember { mutableStateOf("") }
        TextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Type the number (1-1000)") }
        )

        Text(
            text = ""
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = {
                    text = ""
                    var random = nextInt(1, 1000)
                    randNumber.value = random
                    output.value = ""
                    count.value = 0
                },
                modifier = Modifier.size(width = 100.dp, height = 40.dp),
                content = {
                    Text(text = "Reset", fontSize = 20.sp)
                }
            )

            Button(
                onClick = {
                    var input = 0
                    var isText = 0

                    try {
                        input = text.toInt()
                    } catch (text: NumberFormatException) {
                        output.value = "Please enter only number"
                        isText = 1
                    }

                    val checkAnswer = if (input > randNumber.value) {
                        output.value = "$input is too high"
                        count.value = count.value + 1
                    } else if (input < randNumber.value) {
                        output.value = "$input is too low"
                        count.value = count.value + 1
                    } else {
                        output.value = "Congratulations !"
                    }

                    if (isText == 0) {
                        checkAnswer
                    }

                },
                modifier = Modifier.size(width = 100.dp, height = 40.dp),
                content = {
                    Text(text = "Check", fontSize = 20.sp)
                }
            )
        }
        Text(
            text = "${output.value}",
            fontSize = 18.sp
        )
        Text(
            text = "Guessing Time: ${count.value}", fontSize = 18.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NumberGuessingGameTheme {
        GuessingGame(0)
    }
}