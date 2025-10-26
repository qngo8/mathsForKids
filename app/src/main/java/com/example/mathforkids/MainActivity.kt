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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.graphics.Brush
import kotlinx.coroutines.delay
import kotlinx.coroutines.*




class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MathForKidsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Transparent
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        Color(0xFFFFF3E0), // cam nhạt
                                        Color(0xFFFFE0B2), // vàng pastel
                                        Color(0xFFFFCCBC)  // hồng nhạt
                                    )
                                )
                            )
                    ) {
                        MathGameScreen()
                    }
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
            text = "🎯 Math For Kids 🎯",
            fontSize = 32.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color(0xFF7E57C2),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .clip(RoundedCornerShape(50.dp))
                .background(Color(0xFFE1BEE7))
                .padding(horizontal = 20.dp, vertical = 10.dp)
        )

        Spacer(modifier = Modifier.height(40.dp))
//  Phép toán
        Text(
            text = "$num1 + $num2 = ?",
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF43A047)
        )

        Spacer(modifier = Modifier.height(40.dp))
        // 🔘 Các nút đáp án
        options.forEach { option ->
            Button(
                onClick = {
                    if (option == answer) {
                        feedback = "🎉 Bé giỏi quá!"
                        // ✨ Thêm delay 1 giây rồi mới đổi câu
                        CoroutineScope(Dispatchers.Main).launch {
                            delay(1000)
                            nextQuestion()
                        }
                    } else {
                        feedback = "❌Bé thử lại nhé!"
                    }
                },

                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .padding(vertical = 10.dp)
                    .clip(RoundedCornerShape(50.dp)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF81D4FA), // xanh baby
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(50.dp),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
            ) {
                Text(
                    text = option.toString(),
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 5.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(25.dp))

 //  Phản hồi
        if (feedback.isNotEmpty()) {
            Text(
                text = feedback,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = if (feedback.contains("Chính xác")) Color(0xFF43A047) else Color(0xFFE53935),
                modifier = Modifier
                    .clip(RoundedCornerShape(30.dp))
                    .background(
                        if (feedback.contains("Chính xác")) Color(0xFFC8E6C9)
                        else Color(0xFFFFCDD2)
                    )
                    .padding(horizontal = 20.dp, vertical = 10.dp)
            )
        }
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
//Xem giao diện
@Preview(showBackground = true)
@Composable
fun MathGamePreview() {
    MathForKidsTheme {
        MathGameScreen()
    }
}

