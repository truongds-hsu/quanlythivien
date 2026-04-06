package com.library.service;

import com.library.dto.CategoryStat;
import com.library.dto.ReportSummary;
import com.library.dto.TopBookStat;
import com.library.dto.TopMemberStat;
import com.library.model.Book;
import com.library.model.BorrowRecord;
import com.library.model.BorrowStatus;
import com.library.model.Member;
import com.library.model.Reservation;
import com.library.model.ReservationStatus;
import com.library.repository.BookRepository;
import com.library.repository.BorrowRecordRepository;
import com.library.repository.FineRepository;
import com.library.repository.MemberRepository;
import com.library.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportService {
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;
    private final BorrowRecordRepository borrowRecordRepository;
    private final ReservationRepository reservationRepository;
    private final FineRepository fineRepository;

    public ReportService(BookRepository bookRepository,
                         MemberRepository memberRepository,
                         BorrowRecordRepository borrowRecordRepository,
                         ReservationRepository reservationRepository,
                         FineRepository fineRepository) {
        this.bookRepository = bookRepository;
        this.memberRepository = memberRepository;
        this.borrowRecordRepository = borrowRecordRepository;
        this.reservationRepository = reservationRepository;
        this.fineRepository = fineRepository;
    }

    public ReportSummary getSummary() {
        List<Book> books = bookRepository.findAll();
        long totalTitles = books.size();
        long totalCopies = books.stream().mapToLong(b -> safeInt(b.getTotalCopies())).sum();
        long availableCopies = books.stream().mapToLong(b -> safeInt(b.getAvailableCopies())).sum();
        long totalMembers = memberRepository.count();
        long activeMembers = memberRepository.findByActive(true).size();
        long borrowingCount = borrowRecordRepository.findByStatus(BorrowStatus.BORROWED).size();
        long overdueCount = borrowRecordRepository.findByStatus(BorrowStatus.OVERDUE).size();
        long reservationCount = reservationRepository.count();
        long activeReservationCount = reservationRepository.countByStatus(ReservationStatus.ACTIVE);
        BigDecimal unpaidFineTotal = fineRepository.findByPaidFalse().stream()
                .map(f -> f.getAmountDecimal())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return new ReportSummary(totalTitles, totalMembers, activeMembers, borrowingCount, overdueCount,
                reservationCount, activeReservationCount, totalTitles, totalCopies, availableCopies, unpaidFineTotal);
    }

    public List<CategoryStat> getCategoryStats() {
        Map<String, List<Book>> grouped = bookRepository.findAll().stream()
                .collect(Collectors.groupingBy(b -> emptyToDefault(b.getCategory(), "Chưa phân loại"), LinkedHashMap::new, Collectors.toList()));
        List<CategoryStat> stats = new ArrayList<>();
        for (Map.Entry<String, List<Book>> entry : grouped.entrySet()) {
            long totalBooks = entry.getValue().size();
            long totalCopies = entry.getValue().stream().mapToLong(b -> safeInt(b.getTotalCopies())).sum();
            long availableCopies = entry.getValue().stream().mapToLong(b -> safeInt(b.getAvailableCopies())).sum();
            stats.add(new CategoryStat(entry.getKey(), totalBooks, totalCopies, availableCopies));
        }
        stats.sort(Comparator.comparing(CategoryStat::getCategory));
        return stats;
    }

    public List<TopBookStat> getTopBorrowedBooks(int limit) {
        Map<Long, Long> borrowCounts = borrowRecordRepository.findAll().stream()
                .filter(r -> r.getBook() != null && r.getBook().getId() != null)
                .collect(Collectors.groupingBy(r -> r.getBook().getId(), Collectors.counting()));
        return borrowCounts.entrySet().stream()
                .map(entry -> {
                    Book book = bookRepository.findById(entry.getKey()).orElse(null);
                    if (book == null) return null;
                    return new TopBookStat(book.getBookCode(), book.getTitle(), book.getAuthor(), entry.getValue());
                })
                .filter(b -> b != null)
                .sorted(Comparator.comparingLong(TopBookStat::getBorrowCount).reversed().thenComparing(TopBookStat::getTitle))
                .limit(limit)
                .collect(Collectors.toList());
    }

    public List<TopMemberStat> getTopBorrowers(int limit) {
        List<BorrowRecord> records = borrowRecordRepository.findAll();
        Map<Long, List<BorrowRecord>> grouped = records.stream()
                .filter(r -> r.getMember() != null && r.getMember().getId() != null)
                .collect(Collectors.groupingBy(r -> r.getMember().getId()));
        return grouped.entrySet().stream()
                .map(entry -> {
                    Member member = memberRepository.findById(entry.getKey()).orElse(null);
                    if (member == null) return null;
                    long overdueCount = entry.getValue().stream()
                            .filter(r -> BorrowStatus.OVERDUE.name().equals(r.getStatus()))
                            .count();
                    return new TopMemberStat(member.getMemberCode(), member.getFullName(), entry.getValue().size(), overdueCount);
                })
                .filter(m -> m != null)
                .sorted(Comparator.comparingLong(TopMemberStat::getBorrowCount).reversed().thenComparing(TopMemberStat::getFullName))
                .limit(limit)
                .collect(Collectors.toList());
    }

    public List<BorrowRecord> getOverdueRecords() {
        return borrowRecordRepository.findAll().stream()
                .filter(r -> BorrowStatus.OVERDUE.name().equals(r.getStatus()))
                .sorted(Comparator.comparing(BorrowRecord::getDueDate))
                .collect(Collectors.toList());
    }

    public List<Book> getLowStockBooks(int threshold) {
        return bookRepository.findAll().stream()
                .filter(b -> safeInt(b.getAvailableCopies()) <= threshold)
                .sorted(Comparator.comparingInt((Book b) -> safeInt(b.getAvailableCopies())).thenComparing(Book::getTitle))
                .collect(Collectors.toList());
    }

    public List<Reservation> getRecentReservations(int limit) {
        return reservationRepository.findAll().stream()
                .sorted(Comparator.comparing(Reservation::getReservationDate, Comparator.nullsLast(Comparator.reverseOrder())))
                .limit(limit)
                .collect(Collectors.toList());
    }

    private int safeInt(Integer value) {
        return value == null ? 0 : value;
    }

    private String emptyToDefault(String value, String defaultValue) {
        return value == null || value.trim().isEmpty() ? defaultValue : value;
    }
}
