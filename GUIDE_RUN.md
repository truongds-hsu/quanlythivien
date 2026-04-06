# 🚀 LibraryManagement - Hướng Dẫn Chạy Project

---

## 📋 **FULL WORKFLOW - Từ Bắt Đầu Đến Chạy**

### **Bước 1: Vào Thư Mục Project**

```bash
cd /Users/alanhjhj/Downloads/LibraryManagement
```

---

### **Bước 2: Build Project (Biên Dịch Code)**

```bash
mvn clean install
```

**Giải thích:**
- `mvn clean` = Xóa các file cũ
- `install` = Biên dịch, chạy test, lưu vào local repo

**Kế quả nếu thành công:**
```
BUILD SUCCESS
Total time: 45 seconds
```

⏱️ **Thời gian:** Lần đầu ~1-2 phút (download dependencies), lần sau ~30 giây

---

### **Bước 3: Chạy Tests (Kiểm Tra Code)**

```bash
mvn test
```

**Giải thích:**
- Chạy tất cả tests trong project
- Kiểm tra code có lỗi gì không

**Kế quả nếu thành công:**
```
Tests run: 35, Failures: 0, Errors: 0

BUILD SUCCESS
```

---

### **Bước 4: Chạy Ứng Dụng (Start Server)**

```bash
mvn spring-boot:run
```

**Giải thích:**
- Khởi động Spring Boot server
- Tạo H2 database trong memory
- Load test data từ `data.sql` tự động

**Kế quả khi thành công:**
```
2024-04-01 10:30:00.000  INFO 12345 --- [main] com.library.LibraryApp : Starting LibraryApp v1.0.0
2024-04-01 10:30:05.123  INFO 12345 --- [main] sql.init : Executing SQL script from class path resource [data.sql]
2024-04-01 10:30:06.456  INFO 12345 --- [main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http)
2024-04-01 10:30:06.789  INFO 12345 --- [main] com.library.LibraryApp : Started LibraryApp in 6.789 seconds (JVM running for 7.234)

Application started successfully! 🚀
```

---

## 🌐 **Bước 5: Truy Cập Ứng Dụng**

### **Ứng Dụng (Website)**

```
http://localhost:8080
```

Sẽ thấy trang chủ (home page) của ứng dụng.

---

### **H2 Database Console (Xem Database)**

```
http://localhost:8080/h2-console
```

**Thông tin đăng nhập:**
```
JDBC URL: jdbc:h2:mem:testdb
Username: sa
Password: (để trống)
```

**Sau đó nhấp "Connect"**

---

## 🚀 **QUICK START - Server Đang Chạy? Làm Gì Bây Giờ?**

### **Tình Huống: Anh Mở Terminal + Chạy `mvn spring-boot:run`**

```bash
$ mvn spring-boot:run
...
2024-04-01 10:30:06.789  INFO : Started LibraryApp in 6.789 seconds
```

**Terminal bây giờ bị "khóa" (không thể nhập lệnh), server đang chạy)**

---

### **✅ Cách Truy Cập Web**

**Từ trình duyệt (Chrome, Safari, Firefox):**

1. **Truy cập ứng dụng:**
   ```
   http://localhost:8080
   ```
   
2. **Xem database (H2 Console):**
   ```
   http://localhost:8080/h2-console
   ```
   
3. **Test API (Postman):**
   ```
   GET http://localhost:8080/api/books
   ```

**Server vẫn chạy ở Terminal, web bình thường hoạt động!**

---

### **🛑 Cách Tắt Server**

#### **Cách 1: Simple - Dùng Ctrl + C (KHUYÊN NHẤT)**

**Trong terminal đang chạy `mvn spring-boot:run`:**

```
(Nhấn tổ hợp phím)
Ctrl + C
```

**Kết quả:**
```
$ mvn spring-boot:run
...
^C
$  ← Quay về prompt, terminal lại có thể nhập lệnh
```

✅ **Server đã tắt!**

---

#### **Cách 2: Kill Process (Nếu Ctrl+C không hoạt động)**

**Mở terminal KHÁC (không phải terminal chạy mvn):**

```bash
# Bước 1: Tìm Process ID (PID) của Java
lsof -i :8080
```

**Output:**
```
COMMAND  PID     USER   FD   TYPE             DEVICE SIZE/OFF NODE NAME
java    9820 alanhjhj   67u  IPv6 0xbd1611d0e3dcbfb6      0t0  TCP *:http-alt (LISTEN)
```

**PID là: 9820**

```bash
# Bước 2: Kill process
kill -9 9820
```

✅ **Server đã tắt!**

---

#### **Cách 3: Kill Toàn Bộ Java (Nuclear Option)**

```bash
# Tắt tất cả Java processes
killall java
```

⚠️ **Chỉ dùng khi có nhiều Java processes chạy, không biết PID nào**

---

### **📋 Các Tác Vụ Liên Quan**

#### **Tác Vụ 1: Check Port 8080 Có Bế Tắc Không**

```bash
# Xem process nào dùng port 8080
lsof -i :8080
```

**Nếu có output:** Server/ứng dụng khác đang dùng port 8080  
**Nếu không có output:** Port 8080 rảnh, có thể chạy

---

#### **Tác Vụ 2: Chạy Server Trên Port Khác (Nếu 8080 Bế)**

```bash
# Thay vì port 8080, dùng port 8081
mvn spring-boot:run -Dserver.port=8081
```

**Truy cập:** `http://localhost:8081`

---

#### **Tác Vụ 3: Chạy Server + Để Server Chạy Ở Background**

**Mở 2 terminals:**

**Terminal 1: Chạy server**
```bash
mvn spring-boot:run
```

**Terminal 2: Mở terminal khác, làm việc khác**
```bash
# Terminal 2 này vẫn có thể nhập lệnh
# VD: Git commands, code editing, tests, etc.
cd src
ls
git add .
...
```

**Server vẫn chạy ở Terminal 1, không ảnh hưởng!**

---

#### **Tác Vụ 4: Restart Server**

**Cách 1: Stop + Start (Khuyên)**
```bash
# Trong terminal chạy mvn:
Ctrl + C

# Rồi chạy lại
mvn spring-boot:run
```

**Cách 2: Kill + Rebuild + Start**
```bash
# Terminal khác:
kill -9 <PID>

# Rebuild code
mvn clean install -DskipTests

# Chạy lại
mvn spring-boot:run
```

---

#### **Tác Vụ 5: Check Xem Server Đang Chạy Không**

```bash
# Cách 1: Xem trên terminal
# (Nếu terminal đang chạy mvn và không quay về prompt, server đang chạy)

# Cách 2: Check qua curl
curl http://localhost:8080

# Cách 3: Check qua lsof
lsof -i :8080
```

---

#### **Tác Vụ 6: Xem Log Khi Server Chạy**

```bash
# Server sẽ in log trên terminal
# Nếu muốn lưu log vào file:
mvn spring-boot:run 2>&1 | tee server.log

# Rồi xem log:
cat server.log
```

---

### **💡 Workflow Thực Tế**

**Từ đầu đến cuối:**

```bash
# 1️⃣  Vào thư mục project
cd /Users/alanhjhj/Downloads/LibraryManagement

# 2️⃣  Build (lần đầu)
mvn clean install -DskipTests

# 3️⃣  Chạy server
mvn spring-boot:run
# → Server chạy, terminal bị khóa

# 4️⃣  Mở trình duyệt (KHÁC TERMINAL)
# Vào: http://localhost:8080

# 5️⃣  Xem H2 console
# Vào: http://localhost:8080/h2-console

# 6️⃣  Test API với Postman
# GET http://localhost:8080/api/books

# 7️⃣  Khi xong, tắt server
# (Quay lại terminal chạy mvn, nhấn Ctrl + C)
```

---

### **⚠️ Vấn Đề Thường Gặp**

#### **Vấn đề 1: Terminal Bị Khóa Khi Chạy Server**

**Hiểu đúng:**
- ✅ Bình thường! Server chiếm terminal, không thể nhập lệnh
- ✅ Muốn nhập lệnh khác? Mở terminal mới
- ✅ Muốn code? Mở editor (VS Code) → code vẫn được

**Giải pháp:**
```bash
# Mở Terminal khác (Command+T trên Mac)
# Task manager trên Windows

# Terminal mới này có thể nhập lệnh
git add .
git commit ...
```

---

#### **Vấn đề 2: Quên Tắt Server, Chạy Lại → Port Bế**

**Lỗi:**
```
Error: Port 8080 already in use
```

**Nguyên nhân:**
- Server cũ chưa tắt
- Process Java cũ còn chiếm port 8080

**Giải pháp:**
```bash
# Bước 1: Tìm PID
lsof -i :8080

# Bước 2: Kill
kill -9 <PID>

# Bước 3: Chạy lại
mvn spring-boot:run
```

---

#### **Vấn đề 3: Web Không Load (http://localhost:8080 lỗi)**

**Nguyên nhân có thể:**
- ❌ Server chưa chạy
- ❌ Chạy nhưng chưa xong (check terminal xem có "Started" message không)
- ❌ Chạy sai port (check lsof -i :8080)

**Kiểm tra:**
```bash
# Terminal khác: Check xem server có chạy không
lsof -i :8080

# Nếu có output java → Server chạy OK
# Nếu không có output → Server chưa chạy, cần chạy mvn spring-boot:run
```

---

### **🎯 Tóm Tắt 5 Lệnh Quan Trọng**

| Tác Vụ | Lệnh | Kết Quả |
|--------|------|--------|
| **Chạy server** | `mvn spring-boot:run` | Server chạy trên port 8080 |
| **Tắt server (Simple)** | `Ctrl + C` | Server tắt, terminal quay về prompt |
| **Check port 8080** | `lsof -i :8080` | Xem có process nào dùng port 8080 |
| **Tắt process** | `kill -9 <PID>` | Tắt process theo ID |
| **Tắt tất cả Java** | `killall java` | Tắt toàn bộ Java processes |

---

## 🧪 **Bước 6: Test API từ Postman**

### **Tải Postman (nếu chưa có)**

Download: https://www.postman.com/downloads/

---

### **Test Endpoint 1: Lấy Danh Sách Sách**

```
Method: GET
URL: http://localhost:8080/api/books
```

**Response (nếu thành công):**
```json
[
  {
    "id": 1,
    "bookCode": "DM001",
    "title": "Dế Mèn",
    "author": "Tô Hoài",
    "publisher": "Kim Đồng",
    "category": "Truyện Thiếu Nhi",
    "totalCopies": 5,
    "availableCopies": 3
  },
  {
    "id": 2,
    "bookCode": "TTHV01",
    "title": "Tôi Thấy Hoa Vàng",
    "author": "Nguyễn Nhật Ánh",
    "publisher": "Trẻ",
    "category": "Tiểu Thuyết",
    "totalCopies": 3,
    "availableCopies": 2
  }
]
```

---

### **Test Endpoint 2: Lấy Chi Tiết 1 Sách**

```
Method: GET
URL: http://localhost:8080/api/books/1
```

**Response (nếu thành công):**
```json
{
  "id": 1,
  "bookCode": "DM001",
  "title": "Dế Mèn",
  "author": "Tô Hoài",
  "publisher": "Kim Đồng",
  "category": "Truyện Thiếu Nhi",
  "totalCopies": 5,
  "availableCopies": 3
}
```

---

### **Test Endpoint 3: Thêm Sách Mới (POST)**

```
Method: POST
URL: http://localhost:8080/api/books
Header: Content-Type: application/json

Body (JSON):
{
  "bookCode": "NEW001",
  "title": "Harry Potter",
  "author": "J.K. Rowling",
  "publisher": "Bloomsbury",
  "category": "Fantasy",
  "totalCopies": 10,
  "availableCopies": 10
}
```

**Response (nếu thành công):**
```json
{
  "id": 11,
  "bookCode": "NEW001",
  "title": "Harry Potter",
  "author": "J.K. Rowling",
  "publisher": "Bloomsbury",
  "category": "Fantasy",
  "totalCopies": 10,
  "availableCopies": 10
}
```

Status: **201 Created** ✅

---

### **Test Endpoint 4: Sửa Sách (PUT)**

```
Method: PUT
URL: http://localhost:8080/api/books/11
Header: Content-Type: application/json

Body (JSON):
{
  "bookCode": "NEW001",
  "title": "Harry Potter - Phiên Bản Tiếng Việt",
  "author": "J.K. Rowling",
  "publisher": "Bloomsbury",
  "category": "Fantasy",
  "totalCopies": 15,
  "availableCopies": 12
}
```

**Response (nếu thành công):**
```json
{
  "id": 11,
  "bookCode": "NEW001",
  "title": "Harry Potter - Phiên Bản Tiếng Việt",
  ...
}
```

Status: **200 OK** ✅

---

### **Test Endpoint 5: Xóa Sách (DELETE)**

```
Method: DELETE
URL: http://localhost:8080/api/books/11
```

**Response (nếu thành công):**
```
(Trả về trống)
```

Status: **204 No Content** ✅

---

## 📊 **Xem Database Qua H2 Console**

### **Bước 1: Mở H2 Console**

```
http://localhost:8080/h2-console
```

### **Bước 2: Nhấp "Connect"**

---

### **Bước 3: Chạy SQL Queries**

```sql
-- Xem tất cả sách
SELECT * FROM book;

-- Xem tất cả thành viên
SELECT * FROM member;

-- Xem tất cả mượn sách
SELECT * FROM borrow_record;

-- Xem tất cả phạt
SELECT * FROM fine;

-- Xem tất cả đặt sách
SELECT * FROM reservation;

-- Đếm tổng số sách
SELECT COUNT(*) FROM book;

-- Xem sách có ID = 1
SELECT * FROM book WHERE id = 1;
```

---

## 🔧 **Các Lệnh Hữu Ích Khác**

### **Chạy Chỉ 1 Test Cụ Thể**

```bash
mvn test -Dtest=BookServiceTest
```

---

### **Build Mà KHÔNG Chạy Test (Nhanh Hơn)**

```bash
mvn clean install -DskipTests
```

---

### **Compile Mà Không Chạy**

```bash
mvn clean compile
```

---

### **Xem Danh Sách Dependencies**

```bash
mvn dependency:tree
```

---

### **Xóa Folder target (Clean)**

```bash
mvn clean
```

---

### **Dừng Ứng Dụng (Khi Chạy)**

Nhấn trong terminal: **`Ctrl + C`**

---

## ⚠️ **Nếu Gặp Lỗi**

### **Lỗi 1: "Port 8080 already in use" (Cổng 8080 đang bị dùng)**

```bash
# Tìm process dùng port 8080
lsof -i :8080

# Tắt process đó (thay PID bằng số hiển thị)
kill -9 <PID>

# Hoặc dùng port khác
mvn spring-boot:run -Dserver.port=8081
```

---

### **Lỗi 2: "Maven command not found"**

```bash
# Kiểm tra Maven đã cài không
mvn -version

# Nếu chưa cài, cài Maven:
# macOS:
brew install maven

# Hoặc dùng Maven wrapper (không cần cài Maven):
./mvnw clean install
./mvnw spring-boot:run
```

---

### **Lỗi 3: "Compilation error" (Code bị lỗi)**

```bash
# Kiểm tra lỗi
mvn clean compile

# Xem chi tiết lỗi và sửa code
# Rồi chạy lại
```

---

### **Lỗi 4: "ERROR: Cannot run program "java": No such file or directory"**

```bash
# Kiểm tra Java đã cài không
java -version

# Nếu chưa cài, cài Java:
# macOS:
brew install openjdk@25

# Hoặc cài từ https://www.oracle.com/java/technologies/downloads/
```

---

## 📋 **Tóm Tắt Các Lệnh Chính**

| Lệnh | Tác Dụng |
|------|---------|
| `mvn clean install` | Build project (đầy đủ) |
| `mvn test` | Chạy tất cả tests |
| `mvn spring-boot:run` | Chạy ứng dụng |
| `mvn clean install -DskipTests` | Build mà bỏ tests (nhanh) |
| `mvn clean compile` | Chỉ compile, không chạy |
| `mvn test -Dtest=BookServiceTest` | Chạy 1 test cụ thể |
| `mvn -version` | Kiểm tra Maven version |
| `java -version` | Kiểm tra Java version |

---

## 🎯 **Workflow Thường Ngày**

### **Lần Đầu Tiên**

```bash
# 1. Build
mvn clean install

# 2. Chạy
mvn spring-boot:run
```

### **Lần Tiếp Theo (Sau Khi Sửa Code)**

```bash
# 1. Stop ứng dụng cũ (Ctrl + C)

# 2. Chạy lại
mvn spring-boot:run
```

### **Khi Gặp Lỗi**

```bash
# 1. Kiểm tra lỗi
mvn clean compile

# 2. Sửa code

# 3. Chạy lại
mvn spring-boot:run
```

---

## 📌 **Ghi Chú Quan Trọng**

✅ **data.sql sẽ tự động load** khi ứng dụng khởi động

✅ **10 sách test** đã có sẵn trong database

✅ **Mỗi lần restart, database sẽ được reset** (xóa dữ liệu cũ, load lại data.sql)

✅ **H2 chạy trong memory** (không lưu trên ổ cứng)

❌ **KHÔNG copy paste nhiều command cơ** - chạy từng cái một để xem output

---

## 🔴 **Lỗi Build Tests - GIải Pháp**

### **Lỗi: "Only void methods can doNothing()" (MemberServiceTest)**

**Nguyên nhân:** `doNothing()` chỉ dùng cho void methods

**Giải pháp nhanh:** Skip tests lần đầu

```bash
mvn clean install -DskipTests
```

---

### **Lỗi: "net::ERR_CONNECTION_REFUSED" (E2E Tests)**

**Nguyên nhân:** E2E tests cần server chạy trong background

**Giải pháp:** 

**Terminal 1: Chạy server**
```bash
mvn spring-boot:run
```

**Terminal 2: Chạy tests**
```bash
mvn test
```

---

### **Workflow Đúng - Lần Đầu**

```bash
# 1️⃣  Build nhanh (skip tests)
mvn clean install -DskipTests

# 2️⃣  Chạy server
mvn spring-boot:run
```

**Server sẽ chạy trên:** http://localhost:8080

---

### **Workflow - Lần Thứ 2+ (Sau Khi Code)**

```bash
# Terminal 1: Chạy server
mvn spring-boot:run

# Terminal 2: (mở terminal khác) Chạy tests
mvn test
```

---

## 🔴 **Lỗi: "Process terminated with exit code: 1" - Khi Chạy mvn spring-boot:run**

### **Nguyên Nhân Có Thể:**

1. ❌ Port 8080 đã được sử dụng
2. ❌ Lỗi database configuration (application.properties)
3. ❌ Lỗi trong code (import, syntax, v.v.)
4. ❌ Controllers chưa được implement đủ

---

### **Cách Sửa - Bước Từng Bước**

#### **Bước 1: Kiểm Tra Port 8080 Có Bị Dùng Không**

```bash
# Xem process dùng port 8080
lsof -i :8080
```

**Nếu có output:**
```bash
# Kill process đó
kill -9 <PID>

# Hoặc dùng port khác
mvn spring-boot:run -Dserver.port=8081
```

---

#### **Bước 2: Xem Chi Tiết Lỗi**

```bash
# Chạy với output chi tiết
mvn spring-boot:run -e
```

**Hoặc:**

```bash
# Xem full error log
mvn spring-boot:run -X
```

Điều này sẽ hiển thị stack trace đầy đủ để xem lỗi ở đâu.

---

#### **Bước 3: Kiểm Tra application.properties Có Đúng Không**

```bash
# Mở file
cat src/main/resources/application.properties
```

**Phải có:**
```properties
spring.application.name=LibraryManagement
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.h2.console.enabled=true
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.sql.init.mode=always
spring.sql.init.data-locations=classpath:data.sql
```

Nếu thiếu, em sẽ tạo file này cho anh.

---

#### **Bước 4: Kiểm Tra Compile Có Lỗi Không**

```bash
# Chỉ compile, không chạy
mvn clean compile
```

Nếu có lỗi compile, sẽ hiện ra ngay.

---

#### **Bước 5: Dùng -DskipTests Khi Chạy**

```bash
# Chạy server mà bỏ tests
mvn spring-boot:run -DskipTests
```

---

### **Full Troubleshooting Sequence**

```bash
# 1️⃣  Kill port 8080 (nếu cần)
lsof -i :8080
kill -9 <PID>

# 2️⃣  Compile để kiểm tra lỗi
mvn clean compile

# 3️⃣  Chạy với output chi tiết
mvn spring-boot:run -e

# Nếu còn lỗi, chạy:
mvn spring-boot:run -X
```

---

### **Nếu Vẫn Gặp Lỗi**

**Anh làm theo các bước này:**

1. Chạy: `mvn spring-boot:run -e`
2. Copy lỗi gửi cho em (dòng ERROR đầu tiên)
3. Em sẽ sửa ngay cho anh

---

## 🔴 **Lỗi: "[DEBUG] Shutting down adapter factory" - Không Có Root Cause**

### **Nguyên Nhân:**

Lỗi này không hiển thị root cause rõ ràng. Cần xem log TRƯỚC dòng "[DEBUG] Shutting down..." để tìm lỗi thực sự.

---

### **Cách Tìm Root Cause:**

#### **Bước 1: Chạy lệnh và SCROLL LÊN để xem toàn bộ log**

```bash
mvn spring-boot:run -e 2>&1 | tee build.log
```

Điều này sẽ:
- Chạy lệnh
- Lưu output vào file `build.log`
- Hiển thị trên terminal

---

#### **Bước 2: Mở file `build.log` để xem toàn bộ output**

```bash
cat build.log
```

---

#### **Bước 3: Tìm dòng ERROR ĐẦU TIÊN (không phải "[DEBUG] Shutting down")**

```bash
# Tìm các dòng ERROR
grep -n "ERROR" build.log
```

**Copy dòng ERROR đầu tiên gửi cho em!**

---

#### **Bước 4: Hoặc chạy compile để xem lỗi**

```bash
mvn clean compile -e
```

Điều này sẽ dừng ở bước compile và hiển thị lỗi (nếu có).

---

### **Các Nguyên Nhân Thường Gặp:**

#### **Nguyên Nhân 1: Thiếu application.properties**

```bash
# Kiểm tra file có tồn tại không
ls -la src/main/resources/application.properties
```

**Nếu không có:**
```bash
# Em sẽ tạo file này
```

---

#### **Nguyên Nhân 2: Lỗi Import trong Code**

```bash
# Compile để kiểm tra
mvn clean compile
```

Nếu có lỗi import hoặc syntax, sẽ hiển thị ngay.

---

#### **Nguyên Nhân 3: Lỗi trong Entities/Services**

```bash
# Xem chi tiết lỗi
mvn clean compile -X | grep -A 5 "ERROR"
```

---

#### **Nguyên Nhân 4: Versioning Issues**

```bash
# Kiểm tra Java version
java -version

# Phải là Java 25 (hoặc ≥ 21)
```

---

### **Quick Fix - Thử Cách Này:**

```bash
# 1️⃣  Clean toàn bộ
mvn clean

# 2️⃣  Compile để kiểm tra
mvn compile

# 3️⃣  Nếu compile OK, build without tests
mvn clean install -DskipTests

# 4️⃣  Rồi chạy
mvn spring-boot:run
```

---

### **Nếu Vẫn Lỗi - Anh Làm Này:**

**Bước 1: Get full log**
```bash
mvn spring-boot:run -e 2>&1 | tee error.log
```

**Bước 2: Find first ERROR**
```bash
grep -n "ERROR" error.log | head -5
```

**Bước 3: Copy output gửi cho em**

Em sẽ xác định ngay lỗi ở đâu!

---

## 🌐 **Lỗi: "No property 'isbn' found for type 'Book'"**

### **Nguyên Nhân:**

BookRepository có method `findByIsbn()` nhưng Book entity không có field `isbn` (đã thay bằng `bookCode`)

```
Error: Could not create query for method public abstract java.util.Optional 
com.library.repository.BookRepository.findByIsbn(java.lang.String); 
No property 'isbn' found for type 'Book'
```

---

### **Cách Sửa - 2 Cách Chọn 1:**

#### **Cách 1: Xóa method findByIsbn() từ BookRepository (KHUYÊN)**

**File**: `src/main/java/com/library/repository/BookRepository.java`

Tìm và xóa dòng:
```java
Optional<Book> findByIsbn(String isbn);
```

---

#### **Cách 2: Thay đổi findByIsbn → findByBookCode**

Nếu anh muốn giữ method, thay đổi:

```java
// Cũ (XÓA)
Optional<Book> findByIsbn(String isbn);

// Mới (THÊM)
Optional<Book> findByBookCode(String bookCode);
```

---

### **Sửa Xong Rồi Chạy Lại:**

```bash
# 1️⃣  Clean
mvn clean

# 2️⃣  Build
mvn clean install -DskipTests

# 3️⃣  Chạy
mvn spring-boot:run
```

---

## 🚀 **PHẦN: ĐƯA PROJECT LÊN GITHUB**

### **Bước 1: Tạo Repository GitHub**

1. Vào https://github.com/new
2. Nhập tên: `LibraryManagement`
3. Nhập mô tả: `Library Management System with Java 25 & Spring Boot 3.5.0`
4. Chọn **Public** hoặc **Private**
5. Click "Create repository"

**Sẽ nhận được URL**: `https://github.com/your-username/LibraryManagement.git`

---

### **Bước 2: Khởi Tạo Git Trong Project (Nếu Chưa Có)**

```bash
# Vào thư mục project
cd /Users/alanhjhj/Downloads/LibraryManagement

# Khởi tạo git
git init

# Thêm remote (thay your-username bằng username GitHub của anh)
git remote add origin https://github.com/your-username/LibraryManagement.git

# Verify
git remote -v
```

---

### **Bước 3: Add Tất Cả File Vào Git**

```bash
# Add tất cả file
git add .

# Kiểm tra file được add chưa
git status
```

---

### **Bước 4: Commit Lần Đầu**

```bash
git commit -m "Initial commit: LibraryManagement with Java 25, Spring Boot 3.5.0

- Models: Book, Member, BorrowRecord, Fine, Reservation
- Services: BookService, MemberService, BorrowService, FineService, ReservationService
- Repositories: All setup with custom queries
- Test data: 10 books, 5 members, transaction records
- Configuration: H2 database, data.sql auto-load
- Team structure: 5 persons with assigned features"
```

---

### **Bước 5: Push Lên GitHub**

```bash
# Push branch main
git branch -M main
git push -u origin main
```

**Output khi thành công:**
```
Enumerating objects: 50, done.
Counting objects: 100% (50/50), done.
Writing objects: 100% (50/50), 2.15 MiB | 1.5 MiB/s
...
remote: Create a pull request for 'main' on GitHub by visiting:
remote:      https://github.com/your-username/LibraryManagement/pull/new/main
```

---

### **Bước 6: Kiểm Tra Trên GitHub**

Vào: `https://github.com/your-username/LibraryManagement`

Sẽ thấy tất cả file của project!

---

## 👥 **PHẦN: SHARE VỚI TEAM MEMBERS**

### **Cách 1: Cho Phép Access (GitHub Settings)**

1. Vào repo → Settings
2. Collaborators → Add people
3. Thêm GitHub username của team members
4. Choose role: **Collaborator** hoặc **Reviewer**

---

### **Cách 2: Team Members Clone Project**

```bash
# Clone repository
git clone https://github.com/your-username/LibraryManagement.git

# Vào thư mục
cd LibraryManagement

# Tạo branch riêng cho team member (VD: Person A)
git checkout -b feature/person-a-book-management

# Hoặc update branch chính
git pull origin main
```

---

### **Cách 3: Team Members Submit Code (Pull Request)**

**Person A làm xong BookController:**

```bash
# Commit code
git add .
git commit -m "Feature: Implement BookController with 5 endpoints

- GET /api/books: List all books
- POST /api/books: Add new book
- GET /api/books/{id}: Get book detail
- PUT /api/books/{id}: Update book
- DELETE /api/books/{id}: Delete book

Tests: 3 unit tests + E2E tests"

# Push lên GitHub
git push origin feature/person-a-book-management
```

**Rồi tạo Pull Request (PR) trên GitHub:**

1. Vào repo GitHub
2. Click "Compare & pull request"
3. Từ branch `feature/person-a-book-management` vào `main`
4. Nhập description + click "Create pull request"
5. Anh review code → Approve → Merge

---

### **Cách 4: Update Code Mới Nhất**

```bash
# Trước khi code, cập nhật code từ GitHub
git pull origin main

# Hoặc nếu đang ở branch khác
git checkout main
git pull origin main
```

---

## 📋 **Quy Trình Git Cho Team**

### **Workflow Hàng Ngày:**

```bash
# Sáng (Update code mới)
git checkout main
git pull origin main

# Code feature của mình
git checkout -b feature/person-a-[feature-name]
# Và code...

# Trưa (Commit)
git add .
git commit -m "Feature: [description]"
git push origin feature/person-a-[feature-name]

# Chiều (Tạo PR + Review)
# Trên GitHub: Click "Compare & pull request"
# Anh review → Approve → Merge

# Cuối ngày (Update main)
git checkout main
git pull origin main
```

---

## 🔧 **Các Lệnh Git Thường Dùng**

| Lệnh | Tác Dụng |
|------|---------|
| `git status` | Xem status của file |
| `git add .` | Add tất cả file |
| `git commit -m "message"` | Commit với message |
| `git push origin [branch]` | Push lên GitHub |
| `git pull origin main` | Update code từ GitHub |
| `git checkout -b [branch]` | Tạo branch mới |
| `git checkout [branch]` | Switch branch |
| `git log` | Xem history commits |
| `git branch -a` | Xem tất cả branches |

---

## ⚠️ **Lỗi Git Thường Gặp**

### **Lỗi 1: "Permission denied (publickey)"**

```bash
# Cần setup SSH key trước
# Xem hướng dẫn: https://docs.github.com/en/authentication/connecting-to-github-with-ssh

# Hoặc dùng HTTPS + Personal Access Token
git remote set-url origin https://your-token@github.com/your-username/LibraryManagement.git
```

---

### **Lỗi 2: "Conflicts" (Xung Đột Code)**

```bash
# Khi merge xung đột
# Sửa conflict trong code editor
# Rồi:
git add .
git commit -m "Resolve conflicts"
git push origin [branch]
```

---

### **Lỗi 3: "File Too Large"**

```bash
# Xóa file lớn khỏi Git
git rm --cached [file]
git commit -m "Remove large file"
git push
```

---

## 📚 **GitHub Best Practices**

✅ **LÀM:**
- Tạo branch riêng cho mỗi feature
- Commit messages rõ ràng, chi tiết
- Pull request trước khi merge vào main
- Review code của team members

❌ **KHÔNG LÀM:**
- Push trực tiếp vào main
- Commit với message "fix", "update", v.v.
- Quên pull code mới trước khi code
- Commit code k compile được

---

**Đã sẵn sàng! Em ở đây để hỗ trợ nếu anh gặp vấn đề gì ạ!** 😊

