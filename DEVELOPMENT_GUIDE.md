# 📚 LibraryManagement - Development Guide

---

## 📌 **Mục Đích Của Tài Liệu Này**

Hướng dẫn chi tiết cách gọi API, cách code Controllers, HTML Templates và Tests cho từng team member.

**Ai cần đọc?** Mỗi person (A, B, C, D, E) cần đọc phần của họ để hiểu công việc.

---

## 🌐 **PHẦN 1: CÁCH GỌI API - 3 CÁCH THỰC TẾ**

### **Cách 1: Gọi từ Browser (URL)**

```
GET http://localhost:8080/api/books
↓
Response (Danh sách sách JSON):
[
  {"id": 1, "bookCode": "DM001", "title": "Dế Mèn", "author": "Tô Hoài"},
  {"id": 2, "bookCode": "TTHV01", "title": "Tôi Thấy Hoa Vàng", "author": "Nguyễn Nhật Ánh"}
]
```

✅ **Cách này chỉ dùng được với GET request!**

---

### **Cách 2: Gọi từ HTML Form (Gửi data)**

```html
<!-- File: books/form.html -->
<form action="/api/books" method="POST">
    <input type="text" name="bookCode" placeholder="Mã sách" value="ABC123">
    <input type="text" name="title" placeholder="Tiêu đề" value="Harry Potter">
    <input type="text" name="author" placeholder="Tác giả" value="J.K. Rowling">
    <input type="text" name="publisher" placeholder="Nhà xuất bản" value="Bloomsbury">
    <input type="text" name="category" placeholder="Thể loại" value="Fantasy">
    <input type="number" name="totalCopies" placeholder="Tổng số" value="10">
    <button type="submit">✅ Thêm Sách</button>
</form>
```

**Khi anh click nút "Thêm Sách":**
- Browser gửi `POST /api/books`
- BookController nhận request
- Xử lý data → lưu vào DB
- Trả về response (JSON)

---

### **Cách 3: Gọi Bằng Postman (Công Cụ Test API)**

**Postman là ứng dụng để test API (mạnh hơn browser)**

```
┌─────────────────────────────────────────┐
│  POSTMAN                                │
├─────────────────────────────────────────┤
│ Method: POST ▼                          │
│ URL: http://localhost:8080/api/books   │
├─────────────────────────────────────────┤
│ Headers:                                │
│ Content-Type: application/json         │
├─────────────────────────────────────────┤
│ Body (JSON):                            │
│ {                                       │
│   "bookCode": "ABC123",                │
│   "title": "Harry Potter",             │
│   "author": "J.K. Rowling",            │
│   "publisher": "Bloomsbury",           │
│   "category": "Fantasy",               │
│   "totalCopies": 10,                   │
│   "availableCopies": 10                │
│ }                                       │
├─────────────────────────────────────────┤
│ [Send] ← Nhấp nút này                  │
└─────────────────────────────────────────┘
         ↓
    Response (trả về):
✅ 201 Created
{
  "id": 11,
  "bookCode": "ABC123",
  "title": "Harry Potter",
  ...
}
```

---

## 🎯 **PHẦN 2: 5 ENDPOINTS CỦA BOOKCONTROLLER**

| # | HTTP Method | Endpoint | Tác Dụng | Person A Feature |
|---|-------------|----------|---------|------------------|
| 1 | GET | /api/books | Lấy danh sách sách | - |
| 2 | POST | /api/books | **THÊM SÁCH MỚI** | ✅ Feature 1 |
| 3 | GET | /api/books/{id} | Lấy chi tiết 1 sách | - |
| 4 | PUT | /api/books/{id} | **SỬA SÁCH** | ✅ Feature 2 |
| 5 | DELETE | /api/books/{id} | **XÓA SÁCH** | ✅ Feature 3 |

---

### **📖 Endpoint 1: Lấy danh sách sách**

```
HTTP Method: GET
URL: http://localhost:8080/api/books
Request: Không cần gửi data

Response (200 OK):
[
  { "id": 1, "bookCode": "DM001", "title": "Dế Mèn", ... },
  { "id": 2, "bookCode": "TTHV01", "title": "Tôi Thấy Hoa Vàng", ... }
]
```

---

### **➕ Endpoint 2: THÊM SÁCH MỚI (Person A Feature 1)**

```
HTTP Method: POST
URL: http://localhost:8080/api/books

Request Body (JSON):
{
  "bookCode": "ABC123",
  "title": "Harry Potter",
  "author": "J.K. Rowling",
  "publisher": "Bloomsbury",
  "category": "Fantasy",
  "totalCopies": 10,
  "availableCopies": 10
}

Response (201 Created):
{
  "id": 11,
  "bookCode": "ABC123",
  "title": "Harry Potter",
  "author": "J.K. Rowling",
  "publisher": "Bloomsbury",
  "category": "Fantasy",
  "totalCopies": 10,
  "availableCopies": 10
}
```

---

### **🔍 Endpoint 3: Lấy chi tiết 1 sách**

```
HTTP Method: GET
URL: http://localhost:8080/api/books/1  (1 là ID sách)
Request: Không cần gửi data

Response (200 OK):
{
  "id": 1,
  "bookCode": "DM001",
  "title": "Dế Mèn",
  "author": "Tô Hoài",
  ...
}
```

---

### **✏️ Endpoint 4: SỬA SÁCH (Person A Feature 2)**

```
HTTP Method: PUT
URL: http://localhost:8080/api/books/1  (1 là ID sách cần sửa)

Request Body (JSON - chỉ gửi fields cần sửa):
{
  "bookCode": "DM001",
  "title": "Dế Mèn (Tái Bản)",  ← Sửa tiêu đề
  "author": "Tô Hoài",
  "publisher": "Kim Đồng",
  "category": "Truyện Thiếu Nhi",
  "totalCopies": 8,  ← Sửa số lượng
  "availableCopies": 5
}

Response (200 OK):
{
  "id": 1,
  "bookCode": "DM001",
  "title": "Dế Mèn (Tái Bản)",
  ...
}
```

---

### **🗑️ Endpoint 5: XÓA SÁCH (Person A Feature 3)**

```
HTTP Method: DELETE
URL: http://localhost:8080/api/books/1  (1 là ID sách cần xóa)
Request: Không cần gửi data

Response (204 No Content):
(Trả về trống, chỉ để xác nhận đã xóa)
```

---

## 💻 **PHẦN 3: PERSON A - CODE BOOKCONTROLLER (5 HÀM)**

**File**: `src/main/java/com/library/controller/BookController.java`

```java
package com.library.controller;

import com.library.model.Book;
import com.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * @Person A: BookController cho quản lý sách
 * - Thêm sách mới (Feature 1)
 * - Sửa sách (Feature 2)
 * - Xóa sách (Feature 3)
 */
@RestController
@RequestMapping("/api/books")
public class BookController {
    
    @Autowired
    private BookService bookService;  // ← Em đã tạo BookService rồi
    
    // ═══════════════════════════════════════════════════════════
    // HÀM 1: GET /api/books - Lấy danh sách tất cả sách
    // ═══════════════════════════════════════════════════════════
    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }
    
    // ═══════════════════════════════════════════════════════════
    // HÀM 2: POST /api/books - THÊM SÁCH MỚI (Person A Feature 1)
    // ═══════════════════════════════════════════════════════════
    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        Book newBook = bookService.addBook(book);
        return ResponseEntity.status(201).body(newBook);  // 201 = Created
    }
    
    // ═══════════════════════════════════════════════════════════
    // HÀM 3: GET /api/books/{id} - Lấy chi tiết 1 sách
    // ═══════════════════════════════════════════════════════════
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Book book = bookService.getBookById(id);
        return ResponseEntity.ok(book);  // 200 = OK
    }
    
    // ═══════════════════════════════════════════════════════════
    // HÀM 4: PUT /api/books/{id} - SỬA SÁCH (Person A Feature 2)
    // ═══════════════════════════════════════════════════════════
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book bookDetails) {
        Book updated = bookService.updateBook(id, bookDetails);
        return ResponseEntity.ok(updated);  // 200 = OK
    }
    
    // ═══════════════════════════════════════════════════════════
    // HÀM 5: DELETE /api/books/{id} - XÓA SÁCH (Person A Feature 3)
    // ═══════════════════════════════════════════════════════════
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();  // 204 = No Content
    }
}
```

---

## 🎨 **PHẦN 4: PERSON A - CODE HTML TEMPLATES (5-6 TRANG)**

### **Template 1: books/list.html (Danh sách sách)**

**File**: `src/main/resources/templates/books/list.html`

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Danh sách sách</title>
    <style>
        body { font-family: Arial; margin: 20px; }
        table { border-collapse: collapse; width: 100%; }
        td, th { border: 1px solid #ddd; padding: 10px; text-align: left; }
        th { background-color: #4CAF50; color: white; }
        a { margin: 0 5px; }
    </style>
</head>
<body>
    <h1>📚 Danh sách sách</h1>
    <a href="/books/form" style="padding: 10px 20px; background: green; color: white; text-decoration: none;">➕ Thêm sách mới</a>
    
    <table>
        <tr>
            <th>ID</th>
            <th>Mã sách</th>
            <th>Tiêu đề</th>
            <th>Tác giả</th>
            <th>NXB</th>
            <th>Thể loại</th>
            <th>Tổng số</th>
            <th>Còn lại</th>
            <th>Hành động</th>
        </tr>
        <tr th:each="book : ${books}">
            <td th:text="${book.id}"></td>
            <td th:text="${book.bookCode}"></td>
            <td th:text="${book.title}"></td>
            <td th:text="${book.author}"></td>
            <td th:text="${book.publisher}"></td>
            <td th:text="${book.category}"></td>
            <td th:text="${book.totalCopies}"></td>
            <td th:text="${book.availableCopies}"></td>
            <td>
                <a th:href="@{/books/{id}(id=${book.id})}" style="color: blue;">👁️ Xem</a>
                <a th:href="@{/books/{id}/edit(id=${book.id})}" style="color: orange;">✏️ Sửa</a>
                <a th:href="@{/books/{id}/delete(id=${book.id})}" style="color: red;">🗑️ Xóa</a>
            </td>
        </tr>
    </table>
</body>
</html>
```

---

### **Template 2: books/form.html (Thêm/Sửa sách)**

**File**: `src/main/resources/templates/books/form.html`

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Form sách</title>
    <style>
        body { font-family: Arial; margin: 20px; }
        .form-group { margin-bottom: 15px; }
        label { display: block; margin-bottom: 5px; font-weight: bold; }
        input, textarea { width: 100%; padding: 8px; box-sizing: border-box; }
        button { padding: 10px 20px; background: #4CAF50; color: white; border: none; cursor: pointer; }
        a { margin-left: 10px; padding: 10px 20px; background: gray; color: white; text-decoration: none; }
    </style>
</head>
<body>
    <h1>📝 Biểu mẫu sách</h1>
    
    <form th:action="@{/api/books}" method="POST" th:object="${book}">
        <div class="form-group">
            <label>Mã sách:</label>
            <input type="text" th:field="*{bookCode}" required>
        </div>
        
        <div class="form-group">
            <label>Tiêu đề:</label>
            <input type="text" th:field="*{title}" required>
        </div>
        
        <div class="form-group">
            <label>Tác giả:</label>
            <input type="text" th:field="*{author}" required>
        </div>
        
        <div class="form-group">
            <label>Nhà xuất bản:</label>
            <input type="text" th:field="*{publisher}" required>
        </div>
        
        <div class="form-group">
            <label>Thể loại:</label>
            <input type="text" th:field="*{category}" required>
        </div>
        
        <div class="form-group">
            <label>Tổng số:</label>
            <input type="number" th:field="*{totalCopies}" required>
        </div>
        
        <div class="form-group">
            <label>Còn lại:</label>
            <input type="number" th:field="*{availableCopies}" required>
        </div>
        
        <button type="submit">✅ Lưu</button>
        <a href="/books">❌ Hủy</a>
    </form>
</body>
</html>
```

---

### **Template 3: books/detail.html (Chi tiết sách)**

**File**: `src/main/resources/templates/books/detail.html`

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Chi tiết sách</title>
    <style>
        body { font-family: Arial; margin: 20px; }
        .detail-section { margin-bottom: 15px; padding: 10px; border: 1px solid #ddd; }
        strong { color: #333; }
        a { margin-right: 10px; padding: 8px 15px; text-decoration: none; display: inline-block; }
    </style>
</head>
<body>
    <h1>📖 Chi tiết sách</h1>
    
    <div class="detail-section">
        <strong>ID:</strong> <span th:text="${book.id}"></span>
    </div>
    <div class="detail-section">
        <strong>Mã sách:</strong> <span th:text="${book.bookCode}"></span>
    </div>
    <div class="detail-section">
        <strong>Tiêu đề:</strong> <span th:text="${book.title}"></span>
    </div>
    <div class="detail-section">
        <strong>Tác giả:</strong> <span th:text="${book.author}"></span>
    </div>
    <div class="detail-section">
        <strong>Nhà xuất bản:</strong> <span th:text="${book.publisher}"></span>
    </div>
    <div class="detail-section">
        <strong>Thể loại:</strong> <span th:text="${book.category}"></span>
    </div>
    <div class="detail-section">
        <strong>Tổng số:</strong> <span th:text="${book.totalCopies}"></span>
    </div>
    <div class="detail-section">
        <strong>Còn lại:</strong> <span th:text="${book.availableCopies}"></span>
    </div>
    
    <hr>
    <a href="/books" style="background: #999; color: white;">⬅️ Quay lại</a>
    <a th:href="@{/books/{id}/edit(id=${book.id})}" style="background: orange; color: white;">✏️ Sửa</a>
    <a th:href="@{/books/{id}/delete(id=${book.id})}" style="background: red; color: white;">🗑️ Xóa</a>
</body>
</html>
```

---

## 🧪 **PHẦN 5: PERSON A - CODE JUNIT TESTS (3 test tối thiểu)**

**File**: `src/test/java/com/library/controller/BookControllerTest.java`

```java
package com.library.controller;

import com.library.model.Book;
import com.library.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @Person A: Tests cho BookController
 * - Test Thêm sách (Feature 1)
 * - Test Sửa sách (Feature 2)
 * - Test Xóa sách (Feature 3)
 */
@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private BookService bookService;
    
    // ═══════════════════════════════════════════════════════════
    // TEST 1: THÊM SÁCH (Feature 1)
    // ═══════════════════════════════════════════════════════════
    @Test
    public void testAddBook() throws Exception {
        // Arrange: Chuẩn bị data
        Book book = new Book();
        book.setBookCode("TEST001");
        book.setTitle("Test Book");
        book.setAuthor("Test Author");
        book.setPublisher("Test Publisher");
        book.setCategory("Test Category");
        book.setTotalCopies(5);
        book.setAvailableCopies(5);
        
        Book savedBook = new Book();
        savedBook.setId(100L);
        savedBook.setBookCode("TEST001");
        savedBook.setTitle("Test Book");
        
        // Mock: Khi gọi bookService.addBook() sẽ trả về savedBook
        when(bookService.addBook(any(Book.class))).thenReturn(savedBook);
        
        // Act & Assert: Gửi request và kiểm tra response
        mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"bookCode\":\"TEST001\",\"title\":\"Test Book\",\"author\":\"Test Author\",\"publisher\":\"Test Publisher\",\"category\":\"Test Category\",\"totalCopies\":5,\"availableCopies\":5}"))
            .andExpect(status().isCreated());  // ✅ Expect 201 Created
        
        // Verify: Kiểm tra bookService.addBook() được gọi
        verify(bookService, times(1)).addBook(any(Book.class));
    }
    
    // ═══════════════════════════════════════════════════════════
    // TEST 2: SỬA SÁCH (Feature 2)
    // ═══════════════════════════════════════════════════════════
    @Test
    public void testUpdateBook() throws Exception {
        // Arrange: Chuẩn bị data
        Book updatedBook = new Book();
        updatedBook.setId(1L);
        updatedBook.setTitle("Updated Title");
        updatedBook.setAuthor("Updated Author");
        
        // Mock: Khi gọi bookService.updateBook() sẽ trả về updatedBook
        when(bookService.updateBook(eq(1L), any(Book.class))).thenReturn(updatedBook);
        
        // Act & Assert: Gửi request PUT và kiểm tra response
        mockMvc.perform(put("/api/books/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"Updated Title\",\"author\":\"Updated Author\"}"))
            .andExpect(status().isOk());  // ✅ Expect 200 OK
        
        // Verify: Kiểm tra bookService.updateBook() được gọi
        verify(bookService, times(1)).updateBook(eq(1L), any(Book.class));
    }
    
    // ═══════════════════════════════════════════════════════════
    // TEST 3: XÓA SÁCH (Feature 3)
    // ═══════════════════════════════════════════════════════════
    @Test
    public void testDeleteBook() throws Exception {
        // Arrange: Không cần chuẩn bị data
        
        // Mock: Khi gọi bookService.deleteBook() không trả về gì
        doNothing().when(bookService).deleteBook(1L);
        
        // Act & Assert: Gửi request DELETE và kiểm tra response
        mockMvc.perform(delete("/api/books/1"))
            .andExpect(status().isNoContent());  // ✅ Expect 204 No Content
        
        // Verify: Kiểm tra bookService.deleteBook() được gọi
        verify(bookService, times(1)).deleteBook(1L);
    }
}
```

---

## 📊 **PHẦN 6: TÓM TẮT - CÔNG VIỆC CỦA MỖI PERSON**

### **Person A - Book Management**

```
┌─────────────────────────────────────────────┐
│  Person A - Quản Lý Sách (Book CRUD)        │
├─────────────────────────────────────────────┤
│                                             │
│  ✅ 1️⃣  CODE BookController (5 hàm)      │
│     • getAllBooks() → GET /api/books       │
│     • addBook() → POST /api/books (Feat 1) │
│     • getBookById() → GET /api/books/{id}  │
│     • updateBook() → PUT /api/books/{id} (Feat 2)
│     • deleteBook() → DELETE /api/books/{id} (Feat 3)
│                                             │
│  ✅ 2️⃣  CODE HTML Templates (5+ trang)   │
│     • books/list.html (Danh sách)         │
│     • books/form.html (Thêm/Sửa)          │
│     • books/detail.html (Chi tiết)        │
│                                             │
│  ✅ 3️⃣  CODE JUnit Tests (3 test)        │
│     • testAddBook() (Thêm sách)           │
│     • testUpdateBook() (Sửa sách)         │
│     • testDeleteBook() (Xóa sách)         │
│                                             │
│  ✅ 4️⃣  CODE E2E Tests Playwright (2-3)  │
│     • Test thêm sách                      │
│     • Test sửa sách                       │
│     • Test xóa sách                       │
│                                             │
└─────────────────────────────────────────────┘
```

---

### **Person B - Member Management**

```
┌─────────────────────────────────────────────┐
│  Person B - Quản Lý Thành Viên (Member)    │
├─────────────────────────────────────────────┤
│  • Đăng ký thành viên (Feature 1)          │
│  • Đăng nhập (Feature 2)                   │
│  • Quản lý hồ sơ (Feature 3)               │
│                                             │
│  ➜ CODE MemberController (5 hàm)           │
│  ➜ CODE HTML Templates (6+ trang)          │
│  ➜ CODE JUnit Tests (3 test)               │
│  ➜ CODE E2E Tests Playwright (2-3)         │
└─────────────────────────────────────────────┘
```

---

### **Person C - Borrow/Return Management**

```
┌─────────────────────────────────────────────┐
│  Person C - Mượn/Trả Sách (Borrow)         │
├─────────────────────────────────────────────┤
│  • Mượn sách (Feature 1)                   │
│  • Trả sách (Feature 2)                    │
│  • Xem lịch sử mượn (Feature 3)            │
│                                             │
│  ➜ CODE BorrowController (5 hàm)           │
│  ➜ CODE HTML Templates (5+ trang)          │
│  ➜ CODE JUnit Tests (3 test)               │
│  ➜ CODE E2E Tests Playwright (2-3)         │
└─────────────────────────────────────────────┘
```

---

### **Person D - Fine Management**

```
┌─────────────────────────────────────────────┐
│  Person D - Quản Lý Phạt (Fine)            │
├─────────────────────────────────────────────┤
│  • Xem danh sách phạt (Feature 1)          │
│  • Đóng phạt (Feature 2)                   │
│  • Xem lịch sử phạt (Feature 3)            │
│                                             │
│  ➜ CODE FineController (4-5 hàm)           │
│  ➜ CODE HTML Templates (4+ trang)          │
│  ➜ CODE JUnit Tests (3 test)               │
│  ➜ CODE E2E Tests Playwright (2-3)         │
└─────────────────────────────────────────────┘
```

---

### **Person E - Reservation Management**

```
┌─────────────────────────────────────────────┐
│  Person E - Quản Lý Đặt Sách (Reservation) │
├─────────────────────────────────────────────┤
│  • Đặt sách (Feature 1)                    │
│  • Hủy đặt sách (Feature 2)                │
│  • Xem danh sách đặt (Feature 3)           │
│                                             │
│  ➜ CODE ReservationController (4-5 hàm)    │
│  ➜ CODE HTML Templates (4+ trang)          │
│  ➜ CODE JUnit Tests (3 test)               │
│  ➜ CODE E2E Tests Playwright (2-3)         │
└─────────────────────────────────────────────┘
```

---

## 🚀 **CÁC BƯỚC ĐỂ CHẠY ĐƯỢC**

### **Bước 1: Build Project**

```bash
mvn clean install
```

### **Bước 2: Chạy Tests**

```bash
mvn test
```

### **Bước 3: Chạy Ứng Dụng**

```bash
mvn spring-boot:run
```

### **Bước 4: Truy Cập**

- **Ứng dụng**: http://localhost:8080
- **H2 Console**: http://localhost:8080/h2-console

---

## 📝 **Ghi Chú**

- **Services đã hoàn thành**: BookService, MemberService, BorrowService, FineService, ReservationService ✅
- **Repositories đã hoàn thành**: Tất cả custom queries ✅
- **Domain Models đã hoàn thành**: 6 JPA entities ✅
- **Test data đã hoàn thành**: data.sql với 10 books, 5 members, v.v. ✅

**Mỗi person chỉ cần CODE:**
1. Controllers
2. HTML Templates
3. JUnit Tests
4. E2E Tests Playwright

---

**✅ Đã hoàn thành! Tài liệu này lưu tại: `DEVELOPMENT_GUIDE.md`** 📚

