# Lưu dữ liệu bền vững với H2

- Database dùng file: `jdbc:h2:file:./data/librarydb;MODE=MySQL`
- Dữ liệu được giữ lại sau khi tắt/mở ứng dụng.
- Dữ liệu mẫu chỉ nạp một lần khi database đang trống, thông qua `com.library.config.DataInitializer`.

## Reset về dữ liệu mẫu ban đầu

1. Tắt ứng dụng
2. Xóa thư mục `data/` nằm cùng cấp với project
3. Chạy lại ứng dụng

## Kiểm tra thêm sách có lưu vào DB không

1. Mở ứng dụng và thêm một cuốn sách mới
2. Tắt ứng dụng
3. Chạy lại ứng dụng
4. Vào danh sách sách hoặc H2 Console để xác nhận dữ liệu vẫn còn
