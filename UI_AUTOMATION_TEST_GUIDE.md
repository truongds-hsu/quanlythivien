# Hướng dẫn kiểm tra UI + Automation Test

## 1. Kiểm tra giao diện thủ công (Manual UI Test)

### Bước 1: Chạy ứng dụng
```bash
mvn clean spring-boot:run
```

### Bước 2: Mở ứng dụng
- App: http://localhost:8080
- H2 Console: http://localhost:8080/h2-console
- JDBC URL: `jdbc:h2:file:./data/librarydb`
- User: `sa`
- Password: để trống

### Bước 3: Kiểm tra các màn hình chính
- Đăng nhập / đăng xuất
- Danh sách sách, thêm sách, sửa sách, xóa sách
- Mượn / trả sách
- Đặt trước, hủy đặt trước, xem chi tiết đặt trước
- Quản lý tài khoản
- Báo cáo thống kê

## 2. Kiểm tra chức năng Báo cáo thống kê

### Đường dẫn
- `/reports`
- Chỉ tài khoản `ADMIN` truy cập được.

### Nội dung cần kiểm tra
- Thẻ tổng hợp: đầu sách, tổng bản sách, đang mượn, quá hạn, thành viên hoạt động, đặt trước đang hoạt động
- Tổng quan tài chính và tồn kho
- Sách sắp hết
- Top sách được mượn nhiều
- Top thành viên mượn nhiều
- Thống kê theo thể loại
- Phiếu mượn quá hạn
- Đặt trước gần đây

### Cách đối chiếu dữ liệu
1. Đăng nhập admin.
2. Vào `/reports`.
3. Mở H2 Console và chạy các câu lệnh ví dụ:
```sql
SELECT COUNT(*) FROM books;
SELECT SUM(total_copies) FROM books;
SELECT SUM(available_copies) FROM books;
SELECT COUNT(*) FROM members WHERE active = true;
SELECT COUNT(*) FROM borrow_records WHERE status = 'BORROWED';
SELECT COUNT(*) FROM borrow_records WHERE status = 'OVERDUE';
SELECT COUNT(*) FROM reservations WHERE status = 'ACTIVE';
SELECT * FROM fines WHERE paid = false;
```
4. So sánh kết quả trên DB với số liệu ở `/reports`.

## 3. Chạy automation test bằng Playwright

### Điều kiện trước khi chạy
- Ứng dụng phải đang chạy ở `http://localhost:8080`
- Máy đã có Maven

### Chạy test
```bash
mvn -Dtest=LibraryE2ETest test
```

### Chạy test có mở trình duyệt để quay demo
```bash
mvn -Dtest=LibraryE2ETest -Dpw.headless=false test
```

### Các case automation hiện có
- `E2E_TC001`: Đăng nhập admin thành công
- `E2E_TC002`: Thêm sách mới qua giao diện web
- `E2E_TC003`: Mở trang báo cáo thống kê
- `E2E_TC004`: Đăng xuất

### Video test
Nếu Playwright chạy thành công, video sẽ được lưu trong thư mục `test-videos/`.

## 4. Gợi ý mở rộng automation test
- Đăng nhập sai tài khoản
- User thường không truy cập được `/reports`
- Tạo phiếu mượn và trả sách
- Đặt trước và hủy đặt trước
- Tạo tài khoản mới và khóa/mở khóa tài khoản
