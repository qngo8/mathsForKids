package com.example.mathforkids

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mathforkids.ui.theme.MathForKidsTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MathForKidsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFFFFF8E1)
                ) {
                    MathGameScreen()
                }
            }
        }
    }
}

@Composable
fun MathGameScreen() {
    var num1 by remember { mutableStateOf(Random.nextInt(1, 10)) }
    var num2 by remember { mutableStateOf(Random.nextInt(1, 10)) }
    var answer by remember { mutableStateOf(num1 + num2) }
    var options by remember { mutableStateOf(generateOptions(answer)) }
    var feedback by remember { mutableStateOf("") }

    fun nextQuestion() {
        num1 = Random.nextInt(1, 10)
        num2 = Random.nextInt(1, 10)
        answer = num1 + num2
        options = generateOptions(answer)
        feedback = ""
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Math For Kids 🎯",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF6A1B9A)
        )

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "$num1 + $num2 = ?",
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF388E3C)
        )

        Spacer(modifier = Modifier.height(30.dp))

        options.forEach { option ->
            Button(
                onClick = {
                    feedback = if (option == answer) "🎉 Correct!" else "❌ Try again!"
                    if (option == answer) nextQuestion()
                },
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .padding(vertical = 8.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF81D4FA))
            ) {
                Text(option.toString(), fontSize = 24.sp, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
        Text(text = feedback, fontSize = 24.sp)
    }
}

fun generateOptions(correctAnswer: Int): List<Int> {
    val options = mutableSetOf(correctAnswer)
    while (options.size < 3) {
        val randomOption = Random.nextInt(1, 18)
        options.add(randomOption)
    }
    return options.shuffled()
}
