# Setup Guide - Todo List App

## Hướng dẫn thiết lập dự án

### 1. Kiểm tra môi trường phát triển

#### Yêu cầu hệ thống

-   **Android Studio**: Arctic Fox (2020.3.1) hoặc mới hơn
-   **JDK**: Version 11 hoặc 17
-   **Android SDK**: API level 24 (Android 7.0) trở lên
-   **Kotlin**: Version 1.9.0 trở lên

#### Kiểm tra Android Studio

```bash
# Kiểm tra version Android Studio
Android Studio -> About Android Studio

# Kiểm tra JDK
File -> Project Structure -> SDK Location -> JDK Location
```

### 2. Clone và Import Project

```bash
# Clone repository (nếu có)
git clone [repository-url]
cd MyApplication

# Hoặc mở project hiện tại trong Android Studio
File -> Open -> Chọn thư mục dự án
```

### 3. Gradle Sync

1. Mở Android Studio
2. Import/Open project
3. Chờ Gradle sync tự động
4. Nếu có lỗi, chạy:
    ```bash
    ./gradlew clean
    ./gradlew build
    ```

### 4. Cấu hình Dependencies

Kiểm tra file `gradle/libs.versions.toml` và `app/build.gradle.kts` đã được cấu hình đúng:

#### libs.versions.toml

```toml
[versions]
room = "2.6.1"
hilt = "2.51.1"
coroutines = "1.8.1"
# ... other versions
```

#### build.gradle.kts (app level)

```kotlin
plugins {
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.kapt)
}

dependencies {
    // Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    kapt(libs.androidx.room.compiler)

    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    // ... other dependencies
}
```

### 5. Verify Project Structure

Đảm bảo cấu trúc thư mục đã được tạo đúng:

```
app/src/main/java/com/abc/myapplication/
├── domain/
├── data/
├── presentation/
├── di/
├── MainActivity.kt
└── TodoApplication.kt
```

### 6. Build và Test

#### Build project

```bash
./gradlew build
```

#### Run on device/emulator

1. Kết nối Android device hoặc khởi động emulator
2. Trong Android Studio: Run -> Run 'app'
3. Hoặc dùng command line:
    ```bash
    ./gradlew installDebug
    ```

### 7. Troubleshooting

#### Lỗi thường gặp và cách khắc phục

##### 1. Gradle Sync Failed

```bash
# Solution:
./gradlew clean
./gradlew --refresh-dependencies
```

##### 2. KAPT Configuration Error

Thêm vào `gradle.properties`:

```properties
kapt.use.worker.api=false
kapt.incremental.apt=false
```

##### 3. Hilt Compilation Error

Đảm bảo:

-   `@HiltAndroidApp` được thêm vào `TodoApplication`
-   `@AndroidEntryPoint` được thêm vào `MainActivity`
-   Application class được khai báo trong `AndroidManifest.xml`

##### 4. Room Database Error

Kiểm tra:

-   Entity annotations đúng
-   DAO queries syntax
-   Database version

##### 5. Compose Navigation Error

Kiểm tra:

-   Navigation dependencies
-   Route definitions
-   NavHost setup

#### Debug Tools

##### 1. Logcat

```bash
# Xem logs
adb logcat | grep "MyApplication"
```

##### 2. Database Inspector

-   Android Studio -> View -> Tool Windows -> App Inspection
-   Chọn device và app
-   Tab Database Inspector

##### 3. Layout Inspector

-   Android Studio -> Tools -> Layout Inspector
-   Connect to running app

### 8. Development Workflow

#### 1. Code Style

-   Follow Kotlin coding conventions
-   Use meaningful variable names
-   Add comments cho complex logic

#### 2. Git Workflow

```bash
# Create feature branch
git checkout -b feature/new-feature

# Make changes
git add .
git commit -m "Add new feature"

# Push changes
git push origin feature/new-feature
```

#### 3. Testing

```bash
# Run unit tests
./gradlew test

# Run instrumented tests
./gradlew connectedAndroidTest
```

### 9. IDE Configuration

#### Android Studio Settings

1. **Code Style**:

    - Settings -> Editor -> Code Style -> Kotlin
    - Set to follow Kotlin conventions

2. **Auto Import**:

    - Settings -> Editor -> General -> Auto Import
    - Enable optimize imports on the fly

3. **Live Templates**:
    - Tạo shortcuts cho common code patterns

#### Useful Plugins

-   **Hilt Navigation**: Hỗ trợ Hilt injection
-   **Room**: Database schema visualization
-   **Compose Preview**: UI preview support

### 10. Performance Optimization

#### Build Performance

Thêm vào `gradle.properties`:

```properties
org.gradle.jvmargs=-Xmx4g -XX:MaxMetaspaceSize=512m
org.gradle.parallel=true
org.gradle.caching=true
android.enableBuildCache=true
kapt.use.worker.api=true
```

#### App Performance

-   Use ProGuard/R8 for release builds
-   Optimize images and resources
-   Profile memory usage

### 11. Release Build

#### Generate Release APK

```bash
./gradlew assembleRelease
```

#### Generate App Bundle

```bash
./gradlew bundleRelease
```

#### Signing Configuration

Thêm vào `app/build.gradle.kts`:

```kotlin
android {
    signingConfigs {
        release {
            storeFile file("keystore.jks")
            storePassword "password"
            keyAlias "alias"
            keyPassword "password"
        }
    }

    buildTypes {
        release {
            signingConfig signingConfigs.release
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
}
```

## Next Steps

1. **Học Clean Architecture**: Hiểu rõ về separation of concerns
2. **Explore Jetpack Compose**: Tìm hiểu thêm về modern UI development
3. **Testing**: Viết unit tests và integration tests
4. **CI/CD**: Setup automated build và deployment
5. **Monitoring**: Thêm analytics và crash reporting

## Resources

-   [Android Developer Documentation](https://developer.android.com/)
-   [Jetpack Compose Documentation](https://developer.android.com/jetpack/compose)
-   [Clean Architecture Guide](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)
-   [Hilt Documentation](https://dagger.dev/hilt/)
-   [Room Documentation](https://developer.android.com/training/data-storage/room)

## Support

Nếu gặp vấn đề:

1. Kiểm tra documentation
2. Tìm kiếm trên Stack Overflow
3. Xem GitHub issues
4. Hỏi trong developer communities
