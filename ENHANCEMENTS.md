# Bản hoàn thiện thêm

Các thay đổi chính:
- Hoàn thiện controller còn TODO: sách, mượn/trả, thành viên, tiền phạt.
- Thêm tìm kiếm sách theo tên/tác giả/thể loại.
- Thêm form mượn sách ngay trên màn quản lý mượn/trả.
- Thêm sửa thành viên, khóa/mở khóa thành viên.
- Thêm trang chi tiết reservation.
- Cải thiện seed dữ liệu H2 qua `data.sql` để tự import dữ liệu mẫu khi khởi động app.
- Đồng bộ `availableCopies` với các phiếu mượn mẫu.

Chạy app:
```bash
mvn clean spring-boot:run
```

Truy cập H2 Console:
- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:librarydb`
- user: `sa`
- password: để trống
