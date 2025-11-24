package com.example.mathforkids.ui.game

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.mathforkids.config.GameConfig
import com.example.mathforkids.model.GameType
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Unified game screen that routes to the appropriate game type
 * Tracks progress and unlocks next level automatically
 */
@Composable
fun GameScreen(
    gameType: String,
    level: Int,
    onComplete: () -> Unit,
    onBack: () -> Unit
) {
    // Track results for this game session
    var correctCount by remember { mutableStateOf(0) }
    var incorrectCount by remember { mutableStateOf(0) }
    var showCompletionDialog by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    
    val type = when (gameType.uppercase()) {
        "COUNTING" -> GameType.COUNTING
        "ADDITION" -> GameType.ADDITION
        "SUBTRACTION" -> GameType.SUBTRACTION
        "MATCHING" -> GameType.MATCHING
        else -> GameType.COUNTING
    }
    
    // Check if level is completed
    LaunchedEffect(correctCount) {
        if (GameConfig.canUnlockNextLevel(correctCount) && !showCompletionDialog) {
            showCompletionDialog = true
        }
    }
    
    Box(modifier = Modifier.fillMaxSize()) {
        // Route to appropriate game based on type
        when (type) {
            GameType.COUNTING -> CountingGameScreen(
                level = level,
                onCorrect = { correctCount++ },
                onIncorrect = { incorrectCount++ },
                onBack = onBack
            )
            GameType.ADDITION -> AdditionGameScreen(
                level = level,
                onCorrect = { correctCount++ },
                onIncorrect = { incorrectCount++ },
                onBack = onBack
            )
            GameType.SUBTRACTION -> SubtractionGameScreen(
                level = level,
                onCorrect = { correctCount++ },
                onIncorrect = { incorrectCount++ },
                onBack = onBack
            )
            GameType.MATCHING -> MatchingGameScreen(
                level = level,
                onCorrect = { correctCount++ },
                onIncorrect = { incorrectCount++ },
                onBack = onBack
            )
        }
        
        // Completion Dialog
        if (showCompletionDialog) {
            LevelCompletionDialog(
                correctAnswers = correctCount,
                incorrectAnswers = incorrectCount,
                stars = GameConfig.calculateStars(correctCount),
                onContinue = {
                    showCompletionDialog = false
                    scope.launch {
                        delay(300)
                        onComplete()
                    }
                },
                onPlayAgain = {
                    showCompletionDialog = false
                    correctCount = 0
                    incorrectCount = 0
                },
                onBack = {
                    showCompletionDialog = false
                    onBack()
                }
            )
        }
    }
}

/**
 * Dialog hiển thị khi hoàn thành level
 */
@Composable
fun LevelCompletionDialog(
    correctAnswers: Int,
    incorrectAnswers: Int,
    stars: Int,
    onContinue: () -> Unit,
    onPlayAgain: () -> Unit,
    onBack: () -> Unit
) {
    Dialog(onDismissRequest = onBack) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(24.dp))
                .background(
                    androidx.compose.ui.graphics.Brush.verticalGradient(
                        listOf(Color(0xFFFFD54F), Color(0xFFFFB300))
                    )
                )
                .padding(32.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    "🎊 HOÀN THÀNH! 🎊",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
                )

                Spacer(Modifier.height(16.dp))

                // Stars
                Text(
                    "⭐".repeat(stars),
                    fontSize = 48.sp
                )

                Spacer(Modifier.height(24.dp))

                // Stats
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White.copy(alpha = 0.9f)
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "Kết quả:",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(Modifier.height(8.dp))
                        Text("✅ Đúng: $correctAnswers", fontSize = 16.sp)
                        Text("❌ Sai: $incorrectAnswers", fontSize = 16.sp)
                    }
                }

                Spacer(Modifier.height(24.dp))

                // Buttons
                Button(
                    onClick = onContinue,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4CAF50)
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        "➡️ Level tiếp theo",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(Modifier.height(12.dp))

                OutlinedButton(
                    onClick = onPlayAgain,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text("🔄 Chơi lại", fontSize = 18.sp)
                }
                
                Spacer(Modifier.height(8.dp))
                
                TextButton(
                    onClick = onBack,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("⬅ Quay lại", fontSize = 16.sp, color = Color.White)
                }
            }
        }
    }
}



