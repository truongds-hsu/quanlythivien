# Chạy project với Java 17

## Yêu cầu
- JDK 17
- Maven 3.9+

## Kiểm tra môi trường
```bash
java -version
javac -version
mvn -version
```
Cả `java` và `javac` phải là phiên bản 17.

## Chạy project
```bash
mvn clean spring-boot:run
```

## Chạy test
```bash
mvn clean test
```

## Nếu IDE vẫn báo `UnsupportedClassVersionError`
Xóa toàn bộ artifact build cũ rồi nạp lại project:
- xóa thư mục `target/`, `bin/`, `out/`
- trong IDE, refresh/reimport Maven project
- chạy lại class `com.library.LibraryApp`
