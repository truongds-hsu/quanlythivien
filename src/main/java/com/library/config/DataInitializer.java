package com.library.config;

import com.library.model.*;
import com.library.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDate;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner seedDemoData(
            MemberRepository memberRepository,
            BookRepository bookRepository,
            BorrowRecordRepository borrowRecordRepository,
            FineRepository fineRepository,
            ReservationRepository reservationRepository) {
        return args -> {
            if (memberRepository.count() > 0 || bookRepository.count() > 0) {
                return;
            }

            Member admin = new Member();
            admin.setMemberCode("A001");
            admin.setFullName("Quản trị viên");
            admin.setUsername("admin");
            admin.setPassword("admin123");
            admin.setPhone("0900000001");
            admin.setEmail("admin@library.local");
            admin.setJoinDate(LocalDate.of(2026, 1, 1));
            admin.setActive(true);
            admin.setRole(Role.ADMIN);
            memberRepository.save(admin);

            Member user01 = createMember("M001", "Nguyễn Văn A", "user01", "123456", "0901234567", "nguyenvana@email.com", LocalDate.of(2026, 1, 1), true);
            Member user02 = createMember("M002", "Trần Thị B", "user02", "123456", "0912345678", "tranthib@email.com", LocalDate.of(2026, 1, 15), true);
            Member user03 = createMember("M003", "Lê Minh C", "user03", "123456", "0923456789", "leminhc@email.com", LocalDate.of(2026, 2, 1), true);
            Member user04 = createMember("M004", "Phạm Quốc D", "user04", "123456", "0934567890", "phamquocd@email.com", LocalDate.of(2026, 2, 10), false);
            Member user05 = createMember("M005", "Hoàng Tuyết E", "user05", "123456", "0945678901", "hoangtuyet@email.com", LocalDate.of(2026, 2, 20), true);
            user01 = memberRepository.save(user01);
            user02 = memberRepository.save(user02);
            user03 = memberRepository.save(user03);
            user04 = memberRepository.save(user04);
            user05 = memberRepository.save(user05);

            Book b1 = bookRepository.save(createBook("BK001", "Dế Mèn Phiêu Lưu Ký", "Tô Hoài", "Kim Đồng", "Văn học trẻ em", 10, 8));
            Book b2 = bookRepository.save(createBook("BK002", "Cho Tôi Xin Một Vé Đi Tuổi Thơ", "Nguyễn Nhật Ánh", "Trẻ", "Văn học trẻ em", 8, 7));
            Book b3 = bookRepository.save(createBook("BK003", "Tôi Thấy Hoa Vàng Trên Cỏ Xanh", "Nguyễn Nhật Ánh", "Trẻ", "Văn học trẻ em", 12, 11));
            Book b4 = bookRepository.save(createBook("BK004", "Mắt Biếc", "Nguyễn Nhật Ánh", "Trẻ", "Văn học trẻ em", 7, 7));
            Book b5 = bookRepository.save(createBook("BK005", "Lão Hạc", "Nam Cao", "Văn Học", "Văn học", 6, 6));
            Book b6 = bookRepository.save(createBook("BK006", "Tắt Đèn", "Ngô Tất Tố", "Văn Học", "Văn học", 5, 4));
            Book b7 = bookRepository.save(createBook("BK007", "Số Đỏ", "Vũ Trọng Phụng", "Văn Học", "Văn học", 9, 8));
            Book b8 = bookRepository.save(createBook("BK008", "Truyện Kiều", "Nguyễn Du", "Giáo Dục", "Văn học cổ điển", 11, 10));
            Book b9 = bookRepository.save(createBook("BK009", "Nhà Giả Kim", "Paulo Coelho", "Hội Nhà Văn", "Phát triển cá nhân", 10, 10));
            Book b10 = bookRepository.save(createBook("BK010", "Đắc Nhân Tâm", "Dale Carnegie", "Trẻ", "Phát triển cá nhân", 14, 14));

            BorrowRecord br1 = borrowRecordRepository.save(createBorrow(user01, b1, LocalDate.now().minusDays(10), LocalDate.now().plusDays(1), null, BorrowStatus.BORROWED));
            BorrowRecord br2 = borrowRecordRepository.save(createBorrow(user02, b2, LocalDate.now().minusDays(15), LocalDate.now().minusDays(1), null, BorrowStatus.BORROWED));
            BorrowRecord br3 = borrowRecordRepository.save(createBorrow(user03, b3, LocalDate.now().minusDays(5), LocalDate.now().plusDays(3), null, BorrowStatus.BORROWED));
            BorrowRecord br4 = borrowRecordRepository.save(createBorrow(user01, b8, LocalDate.now().minusDays(20), LocalDate.now().minusDays(5), LocalDate.now().minusDays(3), BorrowStatus.RETURNED));
            BorrowRecord br5 = borrowRecordRepository.save(createBorrow(user05, b6, LocalDate.now().minusDays(14), LocalDate.now(), null, BorrowStatus.BORROWED));

            Fine fine = new Fine();
            fine.setBorrowRecord(br4);
            fine.setOverdueDays(2);
            fine.setAmount(BigDecimal.valueOf(4000));
            fine.setFineDate(LocalDate.now().minusDays(3));
            fine.setPaid(true);
            fineRepository.save(fine);

            reservationRepository.save(createReservation(user02, b6, LocalDate.now().minusDays(3), ReservationStatus.ACTIVE, null));
            reservationRepository.save(createReservation(user03, b7, LocalDate.now().minusDays(2), ReservationStatus.ACTIVE, null));
            reservationRepository.save(createReservation(user04, b1, LocalDate.now().minusDays(1), ReservationStatus.CANCELLED, LocalDate.now()));
        };
    }

    private Member createMember(String code, String fullName, String username, String password, String phone, String email, LocalDate joinDate, boolean active) {
        Member member = new Member();
        member.setMemberCode(code);
        member.setFullName(fullName);
        member.setUsername(username);
        member.setPassword(password);
        member.setPhone(phone);
        member.setEmail(email);
        member.setJoinDate(joinDate);
        member.setActive(active);
        member.setRole(Role.USER);
        return member;
    }

    private Book createBook(String code, String title, String author, String publisher, String category, int total, int available) {
        Book book = new Book();
        book.setBookCode(code);
        book.setTitle(title);
        book.setAuthor(author);
        book.setPublisher(publisher);
        book.setCategory(category);
        book.setTotalCopies(total);
        book.setAvailableCopies(available);
        return book;
    }

    private BorrowRecord createBorrow(Member member, Book book, LocalDate borrowDate, LocalDate dueDate, LocalDate returnDate, BorrowStatus status) {
        BorrowRecord record = new BorrowRecord();
        record.setMember(member);
        record.setBook(book);
        record.setBorrowDate(borrowDate);
        record.setDueDate(dueDate);
        record.setReturnDate(returnDate);
        record.setStatus(status);
        return record;
    }

    private Reservation createReservation(Member member, Book book, LocalDate reservationDate, ReservationStatus status, LocalDate cancelledDate) {
        Reservation reservation = new Reservation();
        reservation.setMember(member);
        reservation.setBook(book);
        reservation.setReservationDate(reservationDate);
        reservation.setStatus(status);
        reservation.setCancelledDate(cancelledDate);
        return reservation;
    }
}
