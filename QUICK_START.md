# 🎮 Hướng Dẫn Nhanh - Math For Kids

## 📱 Luồng Navigation

```
Login → Register (tùy chọn) → Menu → Level Selection → Game
                                 └─→ Dashboard
```

## 🎯 Chi Tiết Các Game

### 🔢 Đếm Số
- Đếm emoji màu sắc
- 3 lựa chọn đáp án
- Phản hồi: "🎉 Bé giỏi quá!" hoặc "💪 Thử lại nhé!"

### ➕ Phép Cộng  
- Hiển thị 2 nhóm vật
- Biểu thức: `3 + 2 = ?`
- Animation khi đúng

### ➖ Phép Trừ
- Vật biến mất dần
- Hiển thị số ban đầu và số lấy đi
- Luôn cho kết quả không âm

### 🎯 Ghép Số
- 4 nút số lớn
- Đếm kim cương/ngọc
- Ghép số với lượng vật

## 🎨 Màu Sắc Theo Game

- **Đếm Số**: Xanh lá #4CAF50
- **Phép Cộng**: Xanh dương #2196F3
- **Phép Trừ**: Vàng #FFC107
- **Ghép Số**: Hồng #E91E63

## 📝 Code Mẫu

### Navigate đến Game
```kotlin
navController.navigate(
    Screen.Game.createRoute("COUNTING", 1)
)
```

### Thêm Level Mới
```kotlin
levels.add(
    GameLevel(
        id = 11,
        gameType = GameType.ADDITION,
        isUnlocked = false,
        position = LevelPosition(x = 0.5f, y = 1300f)
    )
)
```

### Tùy Chỉnh Độ Khó
Sửa trong `config/GameConfig.kt`:
```kotlin
const val CORRECT_ANSWERS_TO_UNLOCK_NEXT_LEVEL = 3
const val LEVEL_1_MAX = 5
const val LEVEL_2_MAX = 10
```

## 🐛 Đã Sửa

### Fix Infinite Loop Options
```kotlin
var attempts = 0
while (opts.size < 3 && attempts < 50) {
    val num = Random.nextInt(1, 11)
    if (num != correct) opts.add(num)
    attempts++
}
// Backup nếu không đủ
if (opts.size < 3 && correct > 1) opts.add(correct - 1)
```

## 🎮 Demo Accounts

- `demo` / `123`
- `test` / `123`  
- `admin` / `admin`

## 💡 Tips

- Test trên tablet cho trẻ dễ bấm
- Cần 3 câu đúng để qua level
- Tối đa 3 sao mỗi level
- Level tiếp tự động mở sau khi hoàn thành
```kotlin
// In any game file
fun generateQuestion(level: Int): GameQuestion {
    val maxNum = when (level) {
        1 -> 5      // Very easy
        2 -> 10     // Easy
        3 -> 15     // Medium
        else -> 20  // Hard
    }
    // ...
}
```

## 🔧 File Reference

| Screen | File Location |
|--------|---------------|
| Navigation Routes | `navigation/Screen.kt` |
| Game Models | `model/GameModels.kt` |
| Level Selection | `ui/levelselection/LevelSelectionScreen.kt` |
| Counting Game | `ui/game/CountingGame.kt` |
| Addition Game | `ui/game/AdditionGame.kt` |
| Subtraction Game | `ui/game/SubtractionGame.kt` |
| Matching Game | `ui/game/MatchingGame.kt` |
| Shared Components | `ui/game/GameComponents.kt` |
| Game Router | `ui/game/GameScreen.kt` |
| Main Activity | `MainActivity.kt` |

## ✅ Testing Steps

1. **Login Flow**
   - Create account → Login → See menu
   
2. **Level Selection**
   - Click "Bé học Toán" → See Duolingo path
   - First level should be unlocked (pulsating)
   
3. **Each Game Type**
   - Tap counting level → See objects to count
   - Tap addition level → See visual groups
   - Tap subtraction level → See disappearing animation
   - Tap matching level → See number grid
   
4. **Feedback**
   - Answer correctly → Green + "Bé giỏi quá!" + stars
   - Answer wrong → Red + "Thử lại nhé!" + muscle emoji
   
5. **Navigation**
   - Back button works on every screen
   - Dashboard shows stats
   - Logout returns to login

---

**Happy Teaching! 🎓📚**
