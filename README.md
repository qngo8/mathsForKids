# mathsForKids
An Android App for kids(5 years old) to learn maths
/be-hoc-toan (Tên Repository)
│
├── backend/              # ⬅️ Quân (Backend) & Nam (Database) làm việc ở đây
│   ├── app/              # Thư mục chứa logic chính của FastAPI
│   │   ├── __init__.py
│   │   ├── main.py         # File FastAPI chính (nơi chạy app)
│   │   ├── routers/        # ⬅️ QUÂN: Nơi định nghĩa các API (ví dụ: /problems, /users)
│   │   │   ├── __init__.py
│   │   │   └── problems.py   # Logic cho các bài toán
│   │   │
│   │   ├── models/         # ⬅️ NAM: Nơi định nghĩa cấu trúc dữ liệu (Pydantic models)
│   │   │   ├── __init__.py
│   │   │   └── schemas.py    # Ví dụ: class MathProblem(BaseModel): ...
│   │   │
│   │   ├── crud/           # ⬅️ NAM: Logic tương tác vớI database (Create, Read, Update, Delete)
│   │   │   ├── __init__.py
│   │   │   └── crud_problems.py # Hàm lấy bài toán từ DB, lưu câu trả lời...
│   │   │
│   │   └── db/             # ⬅️ NAM: Cài đặt và kết nối database (SQLAlchemy, session)
│   │       ├── __init__.py
│   │       └── database.py
│   │
│   ├── requirements.txt    # ⬅️ QUÂN: Danh sách thư viện Python (fastapi, uvicorn...)
│   └── .gitignore          # (Bỏ qua các file __pycache__, .env...)
│
│
├── frontend/             # ⬅️ HUY (Frontend) làm việc chính ở đây
│   │                     # (Toàn bộ dự án Android Studio của Huy sẽ nằm trong này)
│   ├── app/              # Thư mục code Android (Kotlin)
│   │   ├── src/
│   │   │   ├── main/
│   │   │   │   ├── java/com/behoctoan/
│   │   │   │   │   ├── MainActivity.kt
│   │   │   │   │   ├── ui/                 # Chứa các màn hình (View - Compose)
│   │   │   │   │   │   └── math/
│   │   │   │   │   │       └── MathScreen.kt
│   │   │   │   │   ├── viewmodel/          # Chứa ViewModels (MVVM)
│   │   │   │   │   │   └── MathViewModel.kt
│   │   │   │   │   ├── data/               # Quản lý dữ liệu
│   │   │   │   │   │   ├── repository/     # Lớp Repository
│   │   │   │   │   │   └── remote/         # ⬅️ HUY: Định nghĩa ApiService (Retrofit)
│   │   │   │   │   │       └── ApiService.kt
│   │   │   │   │   ├── model/              # ⬅️ HUY: Data class (MathProblem.kt)
│   │   │   │   │   └── ...
│   │   │   │   └── AndroidManifest.xml
│   │   ├── build.gradle.kts  # File build của app
│   ├── build.gradle.kts      # File build của project
│   └── .gitignore            # (Bỏ qua các file build, .idea...)
│
│
├── docs/                 # (Tùy chọn) Nơi viết tài liệu chung
│   └── api_specification.md  # ⬅️ QUÂN & HUY: Thống nhất API response/request ở đây
│
└── README.md             # (File tổng quan dự án bạn đang xem)
