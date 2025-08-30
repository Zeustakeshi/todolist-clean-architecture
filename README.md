# Todo List App - Clean Architecture

## Tổng quan

Đây là ứng dụng Todo List được xây dựng theo kiến trúc Clean Architecture với các tính năng CRUD cơ bản. Ứng dụng được phát triển bằng Android Jetpack Compose và sử dụng các thư viện hiện đại của Android.

## Kiến trúc

Dự án được tổ chức theo mô hình Clean Architecture với 3 layer chính:

### 1. Domain Layer (Business Logic)

-   **Entity**: Định nghĩa các đối tượng nghiệp vụ cốt lõi
-   **Repository Interface**: Định nghĩa các contract cho data access
-   **Use Cases**: Chứa business logic và rules

### 2. Data Layer (Data Access)

-   **Repository Implementation**: Triển khai concrete của repository interface
-   **Local Database**: Room database cho local storage
-   **Entity Mapping**: Chuyển đổi giữa domain entities và data entities

### 3. Presentation Layer (UI)

-   **ViewModels**: Quản lý UI state và business logic
-   **Compose Screens**: UI components được xây dựng bằng Jetpack Compose
-   **Navigation**: Điều hướng giữa các màn hình

## Cấu trúc thư mục

```
app/src/main/java/com/abc/myapplication/
├── domain/
│   ├── entity/
│   │   └── Todo.kt
│   ├── repository/
│   │   └── TodoRepository.kt
│   └── usecase/
│       ├── GetAllTodosUseCase.kt
│       ├── GetTodoByIdUseCase.kt
│       ├── InsertTodoUseCase.kt
│       ├── UpdateTodoUseCase.kt
│       ├── DeleteTodoUseCase.kt
│       └── DeleteAllTodosUseCase.kt
├── data/
│   ├── local/
│   │   ├── entity/
│   │   │   └── TodoEntity.kt
│   │   ├── dao/
│   │   │   └── TodoDao.kt
│   │   └── database/
│   │       └── TodoDatabase.kt
│   └── repository/
│       └── TodoRepositoryImpl.kt
├── presentation/
│   ├── viewmodel/
│   │   └── TodoViewModel.kt
│   ├── screen/
│   │   ├── TodoListScreen.kt
│   │   └── AddEditTodoScreen.kt
│   └── navigation/
│       ├── Screen.kt
│       └── TodoNavigation.kt
├── di/
│   ├── DatabaseModule.kt
│   └── RepositoryModule.kt
├── ui/theme/
├── MainActivity.kt
└── TodoApplication.kt
```

## Công nghệ sử dụng

### Core Technologies

-   **Kotlin**: Ngôn ngữ lập trình chính
-   **Jetpack Compose**: Modern UI toolkit cho Android
-   **Coroutines**: Asynchronous programming
-   **Flow**: Reactive streams

### Architecture & DI

-   **Hilt**: Dependency Injection framework
-   **Navigation Compose**: Điều hướng trong Compose
-   **ViewModel**: Architecture component for UI state

### Database

-   **Room**: SQLite ORM cho Android
-   **Local Storage**: Lưu trữ dữ liệu offline

## Tính năng

### 1. Quản lý Todo

-   ✅ Thêm todo mới
-   ✅ Xem danh sách todos
-   ✅ Chỉnh sửa todo
-   ✅ Xóa todo
-   ✅ Đánh dấu hoàn thành/chưa hoàn thành
-   ✅ Xóa tất cả todos

### 2. Tìm kiếm và Lọc

-   ✅ Tìm kiếm theo tiêu đề và mô tả
-   ✅ Lọc hiển thị chỉ các todo đã hoàn thành
-   ✅ Realtime search

### 3. UI/UX

-   ✅ Material Design 3
-   ✅ Dark/Light theme support
-   ✅ Responsive layout
-   ✅ Loading states
-   ✅ Error handling

## Chi tiết Implementation

### Domain Layer

#### Todo Entity

```kotlin
data class Todo(
    val id: Long = 0,
    val title: String,
    val description: String = "",
    val isCompleted: Boolean = false,
    val createdAt: Date = Date(),
    val updatedAt: Date = Date()
)
```

#### Repository Interface

Định nghĩa contract cho data access với các method:

-   `getAllTodos()`: Lấy tất cả todos
-   `getTodoById(id: Long)`: Lấy todo theo ID
-   `insertTodo(todo: Todo)`: Thêm todo mới
-   `updateTodo(todo: Todo)`: Cập nhật todo
-   `deleteTodo(todo: Todo)`: Xóa todo
-   `deleteAllTodos()`: Xóa tất cả todos

#### Use Cases

Mỗi use case đảm nhiệm một business operation cụ thể:

-   **GetAllTodosUseCase**: Lấy danh sách todos
-   **InsertTodoUseCase**: Thêm todo với validation
-   **UpdateTodoUseCase**: Cập nhật todo với timestamp
-   **DeleteTodoUseCase**: Xóa todo
-   **DeleteAllTodosUseCase**: Xóa tất cả todos

### Data Layer

#### Room Database

-   **TodoEntity**: Entity cho Room database
-   **TodoDao**: Data Access Object với các query cần thiết
-   **TodoDatabase**: Database configuration

#### Repository Implementation

-   Implement TodoRepository interface
-   Mapping giữa domain và data entities
-   Error handling

### Presentation Layer

#### TodoViewModel

-   Quản lý UI state với StateFlow
-   Xử lý user actions
-   Error handling và loading states
-   Search và filter logic

#### UI Screens

-   **TodoListScreen**: Hiển thị danh sách todos với search và filter
-   **AddEditTodoScreen**: Form thêm/sửa todo
-   **Navigation**: Điều hướng giữa các màn hình

## Dependency Injection

### DatabaseModule

-   Provide Room database instance
-   Provide DAO instances

### RepositoryModule

-   Bind repository implementation
-   Singleton scope management

## Testing Strategy

### Unit Tests

-   Domain layer use cases
-   Repository implementation
-   ViewModel logic

### Integration Tests

-   Database operations
-   Repository integration

### UI Tests

-   Compose UI testing
-   Navigation testing
-   User interaction flows

## Cài đặt và Chạy

### Requirements

-   Android Studio Arctic Fox hoặc mới hơn
-   JDK 11
-   Android SDK API 24+

### Build và Run

1. Clone repository
2. Mở project trong Android Studio
3. Sync Gradle files
4. Build và run trên device/emulator

### Gradle Dependencies

Tất cả dependencies đã được cấu hình trong:

-   `gradle/libs.versions.toml`: Version catalog
-   `app/build.gradle.kts`: App-level dependencies

## Best Practices

### Clean Architecture

-   Separation of concerns
-   Dependency inversion
-   Single responsibility principle

### Android Development

-   MVVM pattern
-   Reactive programming với Flow
-   Modern UI với Jetpack Compose
-   Proper state management

### Code Quality

-   Kotlin coding conventions
-   Proper error handling
-   Resource management
-   Performance optimization

## Mở rộng tương lai

### Tính năng có thể thêm

-   [ ] Reminder/Notification
-   [ ] Categories/Tags
-   [ ] Priority levels
-   [ ] Due dates
-   [ ] Cloud sync
-   [ ] Sharing todos
-   [ ] Export/Import
-   [ ] Statistics and analytics

### Technical Improvements

-   [ ] Offline-first architecture
-   [ ] Multi-module setup
-   [ ] Automated testing
-   [ ] CI/CD pipeline
-   [ ] Performance monitoring
-   [ ] Accessibility improvements

## Troubleshooting

### Common Issues

1. **Build errors**: Check Gradle sync and dependencies
2. **Database errors**: Clear app data or reinstall
3. **Navigation issues**: Check route definitions
4. **State management**: Verify ViewModel scope

### Debug Tips

-   Use Android Studio debugger
-   Check Logcat for errors
-   Use Room database inspector
-   Compose layout inspector

## Đóng góp

1. Fork repository
2. Tạo feature branch
3. Implement changes
4. Write tests
5. Submit pull request

## License

[Specify your license here]
