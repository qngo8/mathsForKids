package com.example.mathforkids

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.Image
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mathforkids.ui.theme.MathForKidsTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding




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

@Composable
fun AppNavigator() {
    var currentScreen by remember { mutableStateOf("login") }
    var registeredUsers = remember { mutableStateListOf<Pair<String, String>>() }
    var currentUser by remember { mutableStateOf("") }

    when (currentScreen) {
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
                registeredUsers.add(username to password)
                currentScreen = "login"
            },
            onBack = { currentScreen = "login" }
        )

        "menu" -> MainMenuScreen(
            username = currentUser,
            onNavigateToMath = { currentScreen = "math" },
            onLogout = { currentScreen = "login" }
        )

        "math" -> MathGameScreen(onBack = { currentScreen = "menu" })
    }
}

@Composable
fun LoginScreen(onLogin: (String, String) -> Unit, onNavigateToRegister: () -> Unit) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.bg_login), // <-- tên resource đổi thành lowercase
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier.align(Alignment.Center)
                .offset(y = (-40).dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Đăng nhập", fontSize = 32.sp, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(30.dp))

            OutlinedTextField(value = username, onValueChange = { username = it }, label = { Text("Tên người dùng") })
            OutlinedTextField(value = password, onValueChange = { password = it }, label = { Text("Mật khẩu") })

            Spacer(modifier = Modifier.height(20.dp))

            Button(onClick = {
                if (username.isNotEmpty() && password.isNotEmpty()) {
                    onLogin(username, password)
                } else {
                    error = "Vui lòng nhập đủ thông tin!"
                }
            }) {
                Text("Đăng nhập")
            }

            Spacer(modifier = Modifier.height(10.dp))

            TextButton(onClick = onNavigateToRegister) {
                Text("Chưa có tài khoản? Đăng ký ngay")
            }

            if (error.isNotEmpty()) {
                Spacer(modifier = Modifier.height(10.dp))
                Text(error, color = Color.Red)
            }
        }
    }

}

@Composable
fun RegisterScreen(onRegister: (String, String) -> Unit, onBack: () -> Unit) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFFFFF3E0), Color(0xFFFFCCBC))
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("📝 Đăng ký", fontSize = 32.sp, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(30.dp))

            OutlinedTextField(value = username, onValueChange = { username = it }, label = { Text("Tên người dùng") })
            OutlinedTextField(value = password, onValueChange = { password = it }, label = { Text("Mật khẩu") })

            Spacer(modifier = Modifier.height(20.dp))

            Button(onClick = {
                if (username.isNotEmpty() && password.isNotEmpty()) onRegister(username, password)
            }) {
                Text("Tạo tài khoản")
            }

            Spacer(modifier = Modifier.height(10.dp))

            TextButton(onClick = onBack) {
                Text("⬅ Quay lại đăng nhập")
            }
        }
    }
}

@Composable
fun MainMenuScreen(username: String, onNavigateToMath: () -> Unit, onLogout: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFFE1BEE7), Color(0xFFFFF8E1))
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Xin chào, $username 👋", fontSize = 28.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(40.dp))
            Button(onClick = onNavigateToMath) { Text("🎯 Bé học Toán") }
            Spacer(modifier = Modifier.height(20.dp))
            Button(onClick = onLogout, colors = ButtonDefaults.buttonColors(containerColor = Color.Red)) {
                Text("Đăng xuất", color = Color.White)
            }
        }
    }
}

@Composable
fun MathGameScreen(onBack: () -> Unit) {
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
        Text("🎯 Math For Kids 🎯", fontSize = 32.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFF7E57C2))

        Spacer(modifier = Modifier.height(40.dp))

        Text("$num1 + $num2 = ?", fontSize = 40.sp, fontWeight = FontWeight.Bold, color = Color(0xFF43A047))

        Spacer(modifier = Modifier.height(40.dp))

        options.forEach { option ->
            Button(
                onClick = {
                    if (option == answer) {
                        feedback = "🎉 Bé giỏi quá!"
                        scope.launch {
                            delay(1000)
                            nextQuestion()
                        }
                    } else {
                        feedback = "❌ Bé thử lại nhé!"
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .padding(vertical = 10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF81D4FA))
            ) {
                Text(option.toString(), fontSize = 26.sp, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(25.dp))

        if (feedback.isNotEmpty()) {
            Text(
                text = feedback,
                fontSize = 24.sp,
                color = if (feedback.contains("giỏi")) Color(0xFF43A047) else Color(0xFFE53935)
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        Button(onClick = onBack, colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)) {
            Text("⬅ Quay lại menu", color = Color.White)
        }
    }
}

fun generateOptions(correctAnswer: Int): List<Int> {
    val options = mutableSetOf(correctAnswer)
    while (options.size < 3) options.add(Random.nextInt(1, 18))
    return options.shuffled()
}

