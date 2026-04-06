# 📋 TESTING GUIDE - Library Management System

**Dự Án**: Hệ Thống Quản Lý Thư Viện  
**Ngày**: 2026-04-01  
**Yêu cầu**: Mỗi người chọn 3 chức năng, mỗi chức năng có 7 bước test

---

## 🎯 Phân Công Team (12 Chức Năng)

### 👤 **Person 1 - 📖 Book Management** (3 Chức Năng)

| # | Chức Năng | Mô Tả | Người Đảm Nhiệm |
|---|-----------|-------|-----------------|
| 1.1 | **Add Book** (Thêm Sách) | Nhân viên thêm sách mới vào hệ thống | Person 1 |
| 1.2 | **Edit Book** (Sửa Sách) | Nhân viên sửa thông tin sách (tên, tác giả, số lượng) | Person 1 |
| 1.3 | **Delete Book** (Xóa Sách) | Nhân viên xóa sách hư hoặc không còn phục vụ | Person 1 |

**Test Cases**: 9-10 test cases  
**Test Methods**: Black Box (Manual), White Box (JUnit), Integration (Spring Boot Test), E2E (Playwright)

---

### 🔄 **Person 2 - 📚 Borrow/Return** (3 Chức Năng)

| # | Chức Năng | Mô Tả | Người Đảm Nhiệm |
|---|-----------|-------|-----------------|
| 2.1 | **Borrow Book** (Mượn Sách) | Thành viên đăng nhập bằng SĐT, chọn sách để mượn | Person 2 |
| 2.2 | **Return Book** (Trả Sách) | Thành viên trả sách, tính tiền phạt nếu quá hạn | Person 2 |
| 2.3 | **Check Availability** (Kiểm Tra Tồn Kho) | Kiểm tra số lượng sách còn trị trong kho | Person 2 |

**Test Cases**: 9-10 test cases  
**Test Methods**: Black Box (Manual), White Box (JUnit), Integration (Spring Boot Test), E2E (Playwright)

---

### 📌 **Person 3 - 🎫 Reservation** (3 Chức Năng)

| # | Chức Năng | Mô Tả | Người Đảm Nhiệm |
|---|-----------|-------|-----------------|
| 3.1 | **Make Reservation** (Đặt Trước) | Thành viên đặt trước sách chưa có sẵn | Person 3 |
| 3.2 | **Cancel Reservation** (Hủy Đặt) | Hủy đặt sách khi không cần mượn nữa | Person 3 |
| 3.3 | **View Reservations** (Xem Danh Sách Đặt) | Xem tất cả sách đã đặt trước | Person 3 |

**Test Cases**: 9-10 test cases  
**Test Methods**: Black Box (Manual), White Box (JUnit), Integration (Spring Boot Test), E2E (Playwright)

---

### 👥 **Person 4 - 🔐 User + 👨‍💼 Admin + 📊 Report** (3 Chức Năng)

| # | Chức Năng | Mô Tả | Người Đảm Nhiệm |
|---|-----------|-------|-----------------|
| 4.1 | **User Management** (Quản Lý Người Dùng) | Admin: Thêm/Khóa/Kích hoạt tài khoản thành viên | Person 4 |
| 4.2 | **Role Management** (Phân Quyền) | Admin/User: Phân quyền Admin, Librarian, Member | Person 4 |
| 4.3 | **Report Statistics** (Báo Cáo Thống Kê) | Báo cáo: Sách mượn, phạt tiền, thống kê người dùng | Person 4 |

**Test Cases**: 9-10 test cases  
**Test Methods**: Black Box (Manual), White Box (JUnit), Integration (Spring Boot Test), E2E (Playwright)

---

## 📝 Template Cho Mỗi Chức Năng

### **1️⃣ Đặc Tả Chức Năng (Specification)**

```markdown
## Chức Năng: [Tên Chức Năng]

### User Story
- Như một [User Type], tôi muốn [Hành Động] để [Mục Đích]
- Ví dụ: "Như một thành viên, tôi muốn mượn sách để có thể đọc"

### Input
- Điều kiện đầu vào, dữ liệu từ user

### Output
- Kết quả mong đợi

### Workflow
- Step 1: ...
- Step 2: ...
- Step N: ...

### Edge Cases
- Trường hợp đặc biệt cần xử lý
```

### **2️⃣ Black Box Testing (Kiểm Tra Hộp Đen - Manual)**

```
Test Case 1: Happy Path (Kịch bản tích cực)
- Pre-condition: ...
- Step: ...
- Expected: ✅ Thành công

Test Case 2: Invalid Input (Dữ liệu không hợp lệ)
- Pre-condition: ...
- Step: ...
- Expected: ❌ Hiển thị lỗi

Test Case 3: Edge Case (Trường hợp biên)
- Pre-condition: ...
- Step: ...
- Expected: ✅/❌ Xử lý đúng
```

### **3️⃣ White Box Testing (Kiểm Tra Hộp Trắng - JUnit)**

```java
@Test
public void testBorrowBook_ValidInput_ShouldReturnSuccess() {
    // Arrange
    Member member = new Member("M001", "0901234567");
    Book book = new Book("BK001", "Dế Mèn");
    
    // Act
    BorrowRecord result = borrowService.borrowBook(member, book);
    
    // Assert
    assertNotNull(result);
    assertEquals("BORROWED", result.getStatus());
}
```

### **4️⃣ Integration Testing (Kiểm Thử Tích Hợp)**

```java
@SpringBootTest
public class BorrowServiceIntegrationTest {
    @Autowired
    private BorrowService borrowService;
    
    @Test
    public void testBorrowBook_ShouldUpdateDatabase() {
        // Arrange, Act, Assert
        // Test lưu vào database
    }
}
```

### **5️⃣ JUnit Test Cases (9-10 Test Cases)**

```
- TC-1: Happy path - successful execution
- TC-2: Invalid input - null input
- TC-3: Boundary - empty string
- TC-4: Edge case - maximum value
- TC-5: Error handling - exception thrown
- TC-6: Business logic - correct calculation
- TC-7: Database - data persisted correctly
- TC-8: Integration - multiple components
- TC-9: Performance - response time acceptable
- TC-10: Security - unauthorized access denied
```

### **6️⃣ Automation Test - Playwright (Video)**

```java
// Example E2E test with Playwright
@Test
public void testBorrowBook_ViaUI_Success() {
    // 1. Navigate
    page.navigate("http://localhost:8080");
    
    // 2. Login
    page.fill("input[name='phone']", "0901234567");
    page.click("button:has-text('Login')");
    
    // 3. Select book
    page.click("button:has-text('Browse Books')");
    page.click("text=Dế Mèn");
    
    // 4. Borrow
    page.click("button:has-text('Borrow')");
    
    // 5. Verify
    assertTrue(page.locator("text=Successfully borrowed").isVisible());
}
```

**Record Video**: Ghi lại màn hình khi chạy test Playwright

### **7️⃣ Tool Quản Lý Test Cases (TestCase Tracking)**

**Option 1: Excel Spreadsheet**
```
| TC ID | Chức Năng | Mô Tả | Expected | Actual | Status | Notes |
|-------|-----------|-------|----------|--------|--------|-------|
| TC-1  | Add Book  | Thêm sách hợp lệ | ✅ Pass | ✅ Pass | ✅ PASS | - |
| TC-2  | Add Book  | Dữ liệu không đủ | ❌ Fail | ❌ Fail | ✅ PASS | Lỗi validate |
```

**Option 2: Google Sheet**
- Chia sẻ link: https://docs.google.com/spreadsheets/...

**Option 3: TestRail** (nếu có)
- Setup tại: https://testrail.io

**Option 4: Markdown File**
- Tạo file: `TEST_CASES.md`

---

## 📊 Test Case Example Template

### **Chức Năng: Thêm Sách (Add Book)**

#### **1. Đặc Tả**
```
User Story: Như một nhân viên thư viện, tôi muốn thêm sách mới để mở rộng bộ sưu tập

Input:
- Mã sách (BK001)
- Tên sách (Dế Mèn)
- Tác giả (Tô Hoài)
- Nhà XB (Kim Đồng)
- Số lượng (10)

Output:
- Thêm thành công, hiển thị "Sách đã được thêm"
- Sách xuất hiện trong danh sách

Workflow:
1. Click "Add Book"
2. Điền form
3. Click "Save"
4. Xác nhận thêm thành công
```

#### **2. Black Box Test Cases**

| # | Test Case | Input | Expected Output | Result |
|---|-----------|-------|-----------------|--------|
| TC-001 | Happy Path | Valid book data | ✅ Add success | PASS |
| TC-002 | Missing Title | Title = Empty | ❌ Error message | PASS |
| TC-003 | Duplicate ISBN | ISBN = BK001 | ❌ Already exists | PASS |
| TC-004 | Invalid Quantity | Quantity = -5 | ❌ Error | PASS |

#### **3. White Box Test (JUnit - 10 cases)**

```java
@SpringBootTest
public class BookServiceTest {
    @Test void TC-001_AddBook_ValidData_ShouldReturn_Success() { }
    @Test void TC-002_AddBook_NullTitle_ShouldThrow_Exception() { }
    @Test void TC-003_AddBook_DuplicateISBN_ShouldThrow_Exception() { }
    @Test void TC-004_AddBook_InvalidQuantity_ShouldFail() { }
    @Test void TC-005_AddBook_LongTitle_ShouldTruncate() { }
    @Test void TC-006_AddBook_SaveToDatabase_ShouldPersist() { }
    @Test void TC-007_AddBook_EmptyPublisher_ShouldAllow() { }
    @Test void TC-008_AddBook_SpecialCharacter_ShouldEscape() { }
    @Test void TC-009_AddBook_CaseInsensitive_ShouldNormalize() { }
    @Test void TC-010_AddBook_Performance_ShouldCompleteUnder_1Second() { }
}
```

#### **4. Integration Test**

```java
@SpringBootTest
public class BookRepositoryIntegrationTest {
    @Test
    public void testAddBook_ShouldSaveToDatabase() {
        // Verify data in database
    }
}
```

#### **5. E2E Test - Playwright**

```java
@Test
public void testAddBook_ViaUI_ShouldDisplay_InList() {
    page.navigate("http://localhost:8080/books");
    page.click("button:has-text('Add Book')");
    page.fill("input[name='title']", "Dế Mèn");
    page.fill("input[name='author']", "Tô Hoài");
    page.fill("input[name='quantity']", "10");
    page.click("button:has-text('Save')");
    assertTrue(page.locator("text=Dế Mèn").isVisible());
}
```

#### **6. Test Report**

```
Chức Năng: Thêm Sách
Status: ✅ PASS
Test Cases: 10/10 PASS
Coverage: 95%
Performance: Acceptable

Issues: None
```

---

## 🔧 Implementation Checklist

### **Person 1 - Book Management**
- [ ] Đặc tả 3 chức năng (Add, Edit, Delete)
- [ ] Viết Black Box test cases (9-10)
- [ ] Viết JUnit test cases (10 test/chức năng)
- [ ] Viết Integration test
- [ ] Viết E2E test với Playwright
- [ ] Record video Playwright
- [ ] Tạo test case tracking file
- [ ] Viết báo cáo

### **Person 2 - Borrow/Return**
- [ ] Đặc tả 3 chức năng (Borrow, Return, Check Availability)
- [ ] Viết Black Box test cases (9-10)
- [ ] Viết JUnit test cases (10 test/chức năng)
- [ ] Viết Integration test
- [ ] Viết E2E test với Playwright
- [ ] Record video Playwright
- [ ] Tạo test case tracking file
- [ ] Viết báo cáo

### **Person 3 - Reservation**
- [ ] Đặc tả 3 chức năng (Make, Cancel, View)
- [ ] Viết Black Box test cases (9-10)
- [ ] Viết JUnit test cases (10 test/chức năng)
- [ ] Viết Integration test
- [ ] Viết E2E test với Playwright
- [ ] Record video Playwright
- [ ] Tạo test case tracking file
- [ ] Viết báo cáo

### **Person 4 - User & Report**
- [ ] Đặc tả 3 chức năng (User Mgmt, Role, Report)
- [ ] Viết Black Box test cases (9-10)
- [ ] Viết JUnit test cases (10 test/chức năng)
- [ ] Viết Integration test
- [ ] Viết E2E test với Playwright
- [ ] Record video Playwright
- [ ] Tạo test case tracking file
- [ ] Viết báo cáo

---

## 📚 Test Data Available

**10 Test Books:**
- BK001: Dế Mèn Phiêu Lưu Ký
- BK002: Cho Tôi Xin Một Vé Đi Tuổi Thơ
- BK003: Tôi Thấy Hoa Vàng Trên Cỏ Xanh
- BK004: Mắt Biếc
- BK005: Lão Hạc
- BK006: Tắt Đèn
- BK007: Số Đỏ
- BK008: Truyện Kiều
- BK009: Nhà Giả Kim
- BK010: Đắc Nhân Tâm

**5 Test Members:**
- M001: Nguyễn Văn A (0901234567)
- M002: Trần Thị B (0912345678)
- M003: Lê Minh C (0923456789)
- M004: Phạm Quốc D (0934567890)
- M005: Hoàng Tuyết E (0945678901)

---

## 📋 Final Submission Requirements

### **Mỗi Người Phải Nộp:**
1. ✅ **Specification Document** (Word/PDF)
   - Đặc tả chi tiết 3 chức năng của mình
   
2. ✅ **Test Cases Spreadsheet** (Excel/Google Sheet)
   - Danh sách 30-40 test cases (10-13/chức năng)
   
3. ✅ **JUnit Test Code** (GitHub)
   - 30-40 test methods
   - Coverage: >80%
   
4. ✅ **E2E Test Video** (YouTube/Drive)
   - Video chạy test Playwright
   - Duration: 5-10 phút
   
5. ✅ **Test Report** (Word/PDF)
   - Tóm tắt kết quả test
   - Issues found & fixed
   - Performance metrics

### **Team Submission:**
1. ✅ **Codebase** (GitHub)
   - Source code + test code
   
2. ✅ **Final Presentation** (PowerPoint)
   - Giới thiệu project
   - Demo
   - Test results
   
3. ✅ **Video Step-by-Step** (YouTube)
   - Đặt vấn đề
   - Đặc tả
   - Implementation
   - Testing
   - Deployment

---

**Good Luck! 🚀**
