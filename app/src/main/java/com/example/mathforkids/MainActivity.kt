package com.example.mathforkids

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mathforkids.ui.theme.MathForKidsTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.animation.*
import androidx.compose.animation.core.*



// ----------------------------- Data Model -----------------------------

data class GameResult(
    val isCorrect: Boolean
)

// ----------------------------- Main Activity -----------------------------

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MathForKidsTheme {
                AppNavigator()
            }
        }
    }
}

// ----------------------------- App Navigator -----------------------------

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavigator() {
    var currentScreen by remember { mutableStateOf("login") }
    var registeredUsers = remember { mutableStateListOf<Pair<String, String>>() }
    var currentUser by remember { mutableStateOf("") }
    var results = remember { mutableStateListOf<GameResult>() }

    AnimatedContent(
        targetState = currentScreen,
        transitionSpec = {
            fadeIn(animationSpec = tween(600)) with fadeOut(animationSpec = tween(600))
        }
    ) { screen ->
        when (screen) {
            "login" -> LoginScreen(
                onLogin = { username, password ->
                    val userExists = registeredUsers.any { it.first == username && it.second == password }
                    if (userExists) {
                        currentUser = username
                        currentScreen = "menu"
                    }
                },
                onNavigateToRegister = { currentScreen = "register" }
            )

            "register" -> RegisterScreen(
                onRegister = { username, password ->
                    if (registeredUsers.any { it.first == username }) return@RegisterScreen
                    registeredUsers.add(username to password)
                    currentScreen = "login"
                },
                onBack = { currentScreen = "login" }
            )

            "menu" -> MainMenuScreen(
                username = currentUser,
                onNavigateToMath = { currentScreen = "math" },
                onNavigateToDashboard = { currentScreen = "dashboard" },
                onLogout = { currentScreen = "login" }
            )

            "math" -> MathGameScreen(
                onBack = { currentScreen = "menu" },
                onResult = { result -> results.add(result) }
            )

            "dashboard" -> DashboardScreen(
                results = results,
                onBack = { currentScreen = "menu" }
            )
        }
    }
}


// ----------------------------- Login Screen -----------------------------

@Composable
fun LoginScreen(onLogin: (String, String) -> Unit, onNavigateToRegister: () -> Unit) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }

    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Image(
            painter = painterResource(id = R.drawable.bg_login), // <-- tên resource đổi thành lowercase
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column(modifier = Modifier.offset(y = (-40).dp),horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Đăng nhập", fontSize = 32.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(30.dp))
            OutlinedTextField(value = username, onValueChange = { username = it }, label = { Text("Tên người dùng") })
            OutlinedTextField(value = password, onValueChange = { password = it }, label = { Text("Mật khẩu") })
            Spacer(Modifier.height(20.dp))
            Button(onClick = {
                if (username.isNotEmpty() && password.isNotEmpty()) {
                    onLogin(username, password)
                } else error = "Vui lòng nhập đủ thông tin!"
            }) { Text("Đăng nhập") }
            Spacer(Modifier.height(10.dp))
            TextButton(onClick = onNavigateToRegister) { Text("Chưa có tài khoản? Đăng ký ngay") }
            if (error.isNotEmpty()) Text(error, color = Color.Red)
        }
    }
}

// ----------------------------- Register Screen -----------------------------


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(onRegister: (String, String) -> Unit, onBack: () -> Unit) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center)
    {
        Image(
            painter = painterResource(id = R.drawable.bg_dangky), // <-- tên resource đổi thành lowercase
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier.offset(y = (-40).dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Đăng ký",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(Modifier.height(30.dp))
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Tên người dùng", color = Color.White) },
                textStyle = LocalTextStyle.current.copy(color = Color.White),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.White,
                    cursorColor = Color.White,
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.White
                )
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Mật khẩu", color = Color.White) },
                textStyle = LocalTextStyle.current.copy(color = Color.White),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.White,
                    cursorColor = Color.White,
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.White
                )
            )

            Spacer(Modifier.height(20.dp))
            Button(onClick = {
                if (username.isNotEmpty() && password.isNotEmpty()) onRegister(username, password)
            }) { Text("Tạo tài khoản") }
            Spacer(Modifier.height(10.dp))
            TextButton(onClick = onBack) { Text("⬅ Quay lại đăng nhập") }
        }
    }
}

// ----------------------------- Main Menu -----------------------------

@Composable
fun MainMenuScreen(
    username: String,
    onNavigateToMath: () -> Unit,
    onNavigateToDashboard: () -> Unit,
    onLogout: () -> Unit
) {
    Box(
        Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Color(0xFFE1BEE7), Color(0xFFFFF8E1)))),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Xin chào, $username 👋", fontSize = 28.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(40.dp))
            Button(onClick = onNavigateToMath) { Text("🎯 Bé học Toán") }
            Spacer(Modifier.height(20.dp))
            Button(onClick = onNavigateToDashboard) { Text("📊 Thống kê học tập") }
            Spacer(Modifier.height(20.dp))
            Button(onClick = onLogout, colors = ButtonDefaults.buttonColors(containerColor = Color.Red)) {
                Text("Đăng xuất", color = Color.White)
            }
        }
    }
}

// ----------------------------- Math Game -----------------------------

@Composable
fun MathGameScreen(onBack: () -> Unit, onResult: (GameResult) -> Unit) {
    var num1 by remember { mutableStateOf(Random.nextInt(1, 10)) }
    var num2 by remember { mutableStateOf(Random.nextInt(1, 10)) }
    var answer by remember { mutableStateOf(num1 + num2) }
    var options by remember { mutableStateOf(generateOptions(answer)) }
    var feedback by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

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
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFFFFF3E0), Color(0xFFFFE0B2), Color(0xFFFFCCBC))
                )
            )
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("🎯 Math For Kids 🎯", fontSize = 32.sp, fontWeight = FontWeight.ExtraBold)
        Spacer(Modifier.height(40.dp))
        Text("$num1 + $num2 = ?", fontSize = 40.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(40.dp))
        options.forEach { option ->
            Button(
                onClick = {
                    val correct = option == answer
                    onResult(GameResult(correct))
                    feedback = if (correct) "🎉 Bé giỏi quá!" else "❌ Bé thử lại nhé!"
                    if (correct) {
                        scope.launch {
                            delay(1000)
                            nextQuestion()
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .padding(vertical = 10.dp)
            ) {
                Text(option.toString(), fontSize = 26.sp, fontWeight = FontWeight.Bold)
            }
        }
        Spacer(Modifier.height(25.dp))
        if (feedback.isNotEmpty()) Text(feedback, fontSize = 24.sp)
        Spacer(Modifier.height(40.dp))
        Button(onClick = onBack, colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)) {
            Text("⬅ Quay lại menu", color = Color.White)
        }
    }
}

// ----------------------------- Dashboard Screen -----------------------------

@Composable
fun DashboardScreen(results: List<GameResult>, onBack: () -> Unit) {
    val totalQuestions = results.size
    val correctAnswers = results.count { it.isCorrect }
    val incorrectAnswers = totalQuestions - correctAnswers
    val accuracy = if (totalQuestions > 0) (correctAnswers * 100 / totalQuestions) else 0

    Box(
        Modifier
            .fillMaxSize()
            .background(Color(0xFFF1F8E9))
            .padding(20.dp)
            .offset(y = (-100).dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("📊 Kết quả học tập", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = Color(0xFF388E3C))
            Spacer(Modifier.height(20.dp))

            if (totalQuestions == 0) {
                Text("Chưa có dữ liệu. Hãy chơi vài ván nhé!", color = Color.Gray)
            } else {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    StatCard("Tổng câu", totalQuestions, Color(0xFF1976D2))
                    StatCard("Đúng", correctAnswers, Color(0xFF43A047))
                    StatCard("Chính xác", "$accuracy%", Color(0xFFFBC02D))
                }

                Spacer(Modifier.height(40.dp))

                // Biểu đồ đơn giản
                Canvas(modifier = Modifier.fillMaxWidth().height(200.dp)) {
                    val total = correctAnswers + incorrectAnswers
                    if (total > 0) {
                        val barWidth = size.width / 4
                        val maxHeight = size.height * 0.8f
                        val correctHeight = (correctAnswers.toFloat() / total) * maxHeight
                        val incorrectHeight = (incorrectAnswers.toFloat() / total) * maxHeight

                        drawRect(
                            color = Color(0xFF43A047),
                            topLeft = androidx.compose.ui.geometry.Offset(size.width / 4 - barWidth / 2, size.height - correctHeight),
                            size = androidx.compose.ui.geometry.Size(barWidth, correctHeight)
                        )
                        drawRect(
                            color = Color(0xFFE53935),
                            topLeft = androidx.compose.ui.geometry.Offset(size.width * 3 / 4 - barWidth / 2, size.height - incorrectHeight),
                            size = androidx.compose.ui.geometry.Size(barWidth, incorrectHeight)
                        )
                    }
                }
            }

            Spacer(Modifier.height(30.dp))
            Button(onClick = onBack) { Text("⬅ Quay lại menu") }
        }
    }
}

@Composable
fun StatCard(label: String, value: Any, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(label, color = Color.DarkGray)
        Text(value.toString(), fontSize = 30.sp, fontWeight = FontWeight.Bold, color = color)
    }
}

// ----------------------------- Helper -----------------------------

fun generateOptions(correctAnswer: Int): List<Int> {
    val options = mutableSetOf(correctAnswer)
    while (options.size < 3) options.add(Random.nextInt(1, 18))
    return options.shuffled()
}
