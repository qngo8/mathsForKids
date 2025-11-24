# ⚙️ Hướng Dẫn GameConfig

## 📍 File: `config/GameConfig.kt`

Chứa **TẤT CẢ** thông số game, dễ dàng điều chỉnh.

## 🎯 Thông Số Chính

### Điều Kiện Qua Level
```kotlin
CORRECT_ANSWERS_TO_UNLOCK_NEXT_LEVEL = 3  // 3 câu đúng → qua level
```
**Thay đổi**:
- Dễ hơn: `= 2`
- Khó hơn: `= 5`
- Test nhanh: `= 1`

### Hệ Thống Sao
```kotlin
CORRECT_FOR_ONE_STAR = 2      // 2 đúng → 1⭐
CORRECT_FOR_TWO_STARS = 4     // 4 đúng → 2⭐
CORRECT_FOR_THREE_STARS = 6   // 6 đúng → 3⭐
```

### Độ Khó Theo Level

#### Đếm Số
```kotlin
LEVEL_1_MAX = 5   // Đếm 1-5
LEVEL_2_MAX = 7   // Đếm 1-7
LEVEL_3_MAX = 10  // Đếm 1-10
```

#### Phép Cộng
```kotlin
LEVEL_1_MAX = 5   // Số 1-5 (2+3)
LEVEL_2_MAX = 10  // Số 1-10 (7+8)
LEVEL_3_MAX = 15  // Số 1-15 (12+13)
```

#### Phép Trừ
```kotlin
LEVEL_1_MAX = 5   // 5-2, 4-1
LEVEL_2_MAX = 10  // 10-3, 8-5
LEVEL_3_MAX = 15  // 15-7, 12-8
```

#### Ghép Số
```kotlin
LEVEL_1_MAX = 5   // Ghép 1-5
LEVEL_2_MAX = 8   // Ghép 1-8
LEVEL_3_MAX = 10  // Ghép 1-10
```

### Thời Gian (milliseconds)
```kotlin
DELAY_BEFORE_NEXT_QUESTION = 1500L  // 1.5s sau khi đúng
DELAY_BEFORE_RETRY = 1500L          // 1.5s sau khi sai
CELEBRATION_DURATION = 2000L        // 2s celebration
```

### Thông Số UI
```kotlin
NUMBER_OF_OPTIONS = 3              // 3 lựa chọn
NUMBER_OF_MATCHING_OPTIONS = 4     // 4 lựa chọn matching
MAX_OBJECTS_PER_ROW = 5           // Tối đa 5 vật/hàng
```

## 🔧 Ví Dụ Thay Đổi

### Dễ hơn cho trẻ nhỏ
```kotlin
const val CORRECT_ANSWERS_TO_UNLOCK_NEXT_LEVEL = 2

object CountingDifficulty {
    const val LEVEL_1_MAX = 3
    const val LEVEL_2_MAX = 5
    const val LEVEL_3_MAX = 7
}
```

### Khó hơn, thử thách
```kotlin
const val CORRECT_ANSWERS_TO_UNLOCK_NEXT_LEVEL = 5

object AdditionDifficulty {
    const val LEVEL_1_MAX = 10
    const val LEVEL_2_MAX = 20
    const val LEVEL_3_MAX = 30
}
```

### Test nhanh
```kotlin
const val CORRECT_ANSWERS_TO_UNLOCK_NEXT_LEVEL = 1  // 1 câu qua luôn!
```

## 💡 Tính Năng

### Dialog Hoàn Thành
Khi đủ câu đúng:
- 🎊 Thông báo hoàn thành
- ⭐⭐⭐ Số sao
- 📊 Thống kê (Đúng/Sai)
- **➡️ Level tiếp** - Về chọn level
- **🔄 Chơi lại** - Replay

### Unlock Tự Động
Level tiếp tự động unlock sau khi hoàn thành.

## 🎯 Quy Trình Chơi

```
Level 1
  ↓
Trả lời câu hỏi
  ↓
Đúng 3 câu → 🎊 HOÀN THÀNH!
  ↓
Dialog: ⭐ (3 câu đúng)
  ↓
"Level tiếp theo" → Level 2 unlock!
```

## 📝 Ghi Chú

### Config mặc định:
- ✅ 3 câu đúng qua level
- ✅ 1-3 sao tùy số câu
- ✅ 3 lựa chọn
- ✅ 1.5s delay
- ✅ Level 1 dễ → Level 3 khó

### Helper Functions
```kotlin
// Tính số sao
fun getStarsForCorrectAnswers(correct: Int): Int

// Check unlock
fun shouldUnlockNextLevel(correct: Int): Boolean

// Lấy max số cho level
fun getCountingMaxForLevel(level: Int): Int
fun getAdditionMaxForLevel(level: Int): Int
fun getSubtractionMaxForLevel(level: Int): Int
fun getMatchingMaxForLevel(level: Int): Int
```
