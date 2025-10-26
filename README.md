Dự án: Bé Học Toán (Math for Kids)

Hệ thống ứng dụng Android (Kotlin) và backend (Python) giúp trẻ 5 tuổi học toán cơ bản (đếm, cộng, trừ) một cách vui nhộn và tương tác.

👥 Phân công vai trò (Team Roles)

Quân (Backend Lead): Phụ trách logic API (backend/app/routers/), tổng thể kiến trúc backend, và phối hợp chặt chẽ với Frontend về API.

Huy (Frontend Lead): Phụ trách toàn bộ dự án Android (frontend/), bao gồm UI/UX (Jetpack Compose) và gọi API (Retrofit).

Nam (Database Specialist): Phụ trách logic cơ sở dữ liệu, bao gồm thiết kế CSDL, viết CRUD (Create, Read, Update, Delete) và định nghĩa schemas (backend/app/db/, backend/app/crud/, backend/app/models/).

🏗️ Cấu trúc thư mục (Project Structure)

Dự án được chia thành hai thư mục cấp cao: backend và frontend để cho phép phát triển độc lập và song song.

/be-hoc-toan (Repository)
│
├── backend/              # ⬅️ Quân (Backend) & Nam (Database) làm việc ở đây
│   ├── app/              # Thư mục chứa logic chính của FastAPI
│   │   ├── __init__.py
│   │   ├── main.py         # File FastAPI chính (nơi chạy app)
│   │   ├── routers/        # ⬅️ QUÂN: Nơi định nghĩa các API (ví dụ: /problems)
│   │   │   ├── __init__.py
│   │   │   └── problems.py   # Logic cho các bài toán
│   │   │
│   │   ├── models/         # ⬅️ NAM: Pydantic schemas (định nghĩa cấu trúc data)
│   │   │   ├── __init__.py
│   │   │   └── schemas.py    # Ví dụ: class MathProblem(BaseModel): ...
│   │   │
│   │   ├── crud/           # ⬅️ NAM: Logic tương tác vớI DB (CRUD)
│   │   │   ├── __init__.py
│   │   │   └── crud_problems.py # Hàm lấy bài toán từ DB, lưu câu trả lời...
│   │   │
│   │   └── db/             # ⬅️ NAM: Cài đặt và kết nối database (SQLAlchemy)
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
│   │   ├── src/main/java/com/behoctoan/
│   │   │   ├── ui/                 # Chứa các màn hình (Compose)
│   │   │   │   └── math/
│   │   │   │       └── MathScreen.kt
│   │   │   ├── viewmodel/          # ViewModels (MVVM)
│   │   │   │   └── MathViewModel.kt
│   │   │   ├── data/
│   │   │   │   ├── repository/     # Lớp Repository
│   │   │   │   └── remote/         # ⬅️ HUY: Định nghĩa ApiService (Retrofit)
│   │   │   │       └── ApiService.kt
│   │   │   └── model/              # ⬅️ HUY: Data class (MathProblem.kt)
│   │   ├── build.gradle.kts
│   └── ... (Các file cấu hình Android Studio khác)
│
│
├── docs/                 # Nơi viết tài liệu chung
│   └── api_specification.md  # ⬅️ QUÂN & HUY: Thống nhất API response/request ở đây
│
└── README.md             # File tổng quan bạn đang đọc
