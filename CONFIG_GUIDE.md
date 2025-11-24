# 🎮 Hướng Dẫn Sử Dụng GameConfig

## 📍 File Config: `GameConfig.kt`

File này chứa **TẤT CẢ** các thông số trò chơi để bạn dễ dàng điều chỉnh mà không cần sửa nhiều file khác nhau.

---

## ⚙️ CÁC THÔNG SỐ QUAN TRỌNG

### 🎯 Điều Kiện Qua Level

```kotlin
// Số câu đúng cần để unlock level tiếp theo
CORRECT_ANSWERS_TO_UNLOCK_NEXT_LEVEL = 3  // Mặc định: 3 câu đúng

// Hiện tại: Trả lời đúng 3 câu → Dialog hoàn thành → Qua level tiếp
```

**Muốn thay đổi?**
- Dễ hơn: `= 2` (chỉ cần 2 câu đúng)
- Khó hơn: `= 5` (cần 5 câu đúng)
- Test nhanh: `= 1` (1 câu đúng là qua level)

---

### ⭐ Hệ Thống Sao

```kotlin
CORRECT_FOR_ONE_STAR = 2      // 2 câu đúng → 1 sao ⭐
CORRECT_FOR_TWO_STARS = 4     // 4 câu đúng → 2 sao ⭐⭐
CORRECT_FOR_THREE_STARS = 6   // 6 câu đúng → 3 sao ⭐⭐⭐
```

**Ví dụ:**
- Trả lời đúng 3 câu: Qua level với 1 sao ⭐
- Trả lời đúng 5 câu: Qua level với 2 sao ⭐⭐
- Trả lời đúng 7 câu: Qua level với 3 sao ⭐⭐⭐

---

### 📊 Độ Khó Theo Level

#### Counting Game (Đếm số)
```kotlin
LEVEL_1_MAX = 5   // Level 1: Đếm từ 1-5
LEVEL_2_MAX = 7   // Level 2: Đếm từ 1-7
LEVEL_3_MAX = 10  // Level 3: Đếm từ 1-10
```

#### Addition Game (Phép cộng)
```kotlin
LEVEL_1_MAX = 5   // Level 1: Số từ 1-5 (VD: 2+3)
LEVEL_2_MAX = 10  // Level 2: Số từ 1-10 (VD: 7+8)
LEVEL_3_MAX = 15  // Level 3: Số từ 1-15 (VD: 12+13)
```

#### Subtraction Game (Phép trừ)
```kotlin
LEVEL_1_MAX = 5   // Level 1: 5-2, 4-1, etc
LEVEL_2_MAX = 10  // Level 2: 10-3, 8-5, etc
LEVEL_3_MAX = 15  // Level 3: 15-7, 12-8, etc
```

#### Matching Game (Ghép số)
```kotlin
LEVEL_1_MAX = 5   // Level 1: Ghép số 1-5
LEVEL_2_MAX = 8   // Level 2: Ghép số 1-8
LEVEL_3_MAX = 10  // Level 3: Ghép số 1-10
```

---

### ⏱️ Thời Gian Animation

```kotlin
DELAY_BEFORE_NEXT_QUESTION = 1500L  // 1.5 giây sau khi đúng
DELAY_BEFORE_RETRY = 1500L          // 1.5 giây sau khi sai
CELEBRATION_DURATION = 2000L        // 2 giây hiển thị celebration
```

**Đơn vị: milliseconds (1000 = 1 giây)**

Muốn nhanh hơn? Giảm số:
- `= 1000L` → 1 giây
- `= 500L` → 0.5 giây

---

### 🎨 Thông Số UI

```kotlin
NUMBER_OF_OPTIONS = 3              // 3 lựa chọn đáp án (counting, addition, subtraction)
NUMBER_OF_MATCHING_OPTIONS = 4     // 4 lựa chọn cho matching game
MAX_OBJECTS_PER_ROW = 5           // Tối đa 5 object mỗi hàng
```

---

## 🔧 CÁCH THAY ĐỔI

### Ví dụ 1: Muốn dễ hơn cho trẻ nhỏ

```kotlin
// Trong GameConfig.kt
const val CORRECT_ANSWERS_TO_UNLOCK_NEXT_LEVEL = 2  // Chỉ cần 2 câu đúng

object CountingDifficulty {
    const val LEVEL_1_MAX = 3   // Đếm tối đa 3
    const val LEVEL_2_MAX = 5   // Đếm tối đa 5
    const val LEVEL_3_MAX = 7   // Đếm tối đa 7
}

object AdditionDifficulty {
    const val LEVEL_1_MAX = 3   // Cộng số nhỏ 1-3
    const val LEVEL_2_MAX = 5   // Cộng số nhỏ 1-5
    const val LEVEL_3_MAX = 8   // Cộng số nhỏ 1-8
}
```

### Ví dụ 2: Muốn khó hơn, thử thách hơn

```kotlin
const val CORRECT_ANSWERS_TO_UNLOCK_NEXT_LEVEL = 5  // Cần 5 câu đúng

object AdditionDifficulty {
    const val LEVEL_1_MAX = 10  // Level 1 đã khó
    const val LEVEL_2_MAX = 20  // Level 2 rất khó
    const val LEVEL_3_MAX = 30  // Level 3 cực khó!
}
```

### Ví dụ 3: Test nhanh, qua level liền

```kotlin
const val CORRECT_ANSWERS_TO_UNLOCK_NEXT_LEVEL = 1  // 1 câu đúng là qua!
```

---

## 💡 TÍNH NĂNG MỚI

### ✅ Dialog Hoàn Thành Level

Khi đạt đủ số câu đúng, sẽ hiển thị:
- 🎊 Thông báo hoàn thành
- ⭐⭐⭐ Số sao đạt được
- 📊 Thống kê (Đúng/Sai)
- **➡️ Level tiếp theo** - Tự động quay về chọn level
- **🔄 Chơi lại** - Chơi lại level hiện tại

### ✅ Unlock Level Tự Động

- Level tiếp theo sẽ tự unlock sau khi hoàn thành (chức năng sẽ được thêm vào LevelSelectionScreen)
- Không cần code gì thêm, chỉ cần trả lời đúng đủ số câu!

---

## 🎯 QUI TRÌNH CHƠI MỚI

```
Bắt đầu Level 1
    ↓
Trả lời câu hỏi
    ↓
Đúng 1 → Tiếp tục
Đúng 2 → Tiếp tục  
Đúng 3 → 🎊 HOÀN THÀNH!
    ↓
Dialog xuất hiện:
  - Hiển thị sao: ⭐ (vì chỉ đúng 3)
  - Chọn "Level tiếp theo" → Về màn hình chọn level
  - Level 2 đã unlock!
```

---

## 📝 GHI CHÚ

### Config hiện tại (mặc định):
- ✅ **3 câu đúng** để qua level
- ✅ **1-3 sao** tùy theo số câu đúng
- ✅ **3 lựa chọn** đáp án
- ✅ **1.5 giây** delay giữa các câu
- ✅ **Level 1 dễ, Level 3 khó** (tự động tăng)

### Để test nhanh:
```kotlin
// Đổi thành 1 câu đúng là qua
const val CORRECT_ANSWERS_TO_UNLOCK_NEXT_LEVEL = 1
```

### Để làm game khó:
```kotlin
// Cần 10 câu đúng mới qua level!
const val CORRECT_ANSWERS_TO_UNLOCK_NEXT_LEVEL = 10

// Và tăng độ khó
object AdditionDifficulty {
    const val LEVEL_1_MAX = 20
    const val LEVEL_2_MAX = 50
    const val LEVEL_3_MAX = 100
}
```

---

## 🚀 BẮT ĐẦU NGAY

1. Mở file `config/GameConfig.kt`
2. Thay đổi số `CORRECT_ANSWERS_TO_UNLOCK_NEXT_LEVEL`
3. Build lại app
4. Chơi thử!

**Hiện tại:** Chỉ cần **3 câu đúng** là qua level và mở level tiếp theo! 🎉

