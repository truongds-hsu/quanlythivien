package com.library.service;

import com.library.model.*;
import com.library.repository.BookRepository;
import com.library.repository.BorrowRecordRepository;
import com.library.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BorrowService {
    private final BorrowRecordRepository borrowRecordRepository;
    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;
    private final FineService fineService;

    public BorrowService(BorrowRecordRepository borrowRecordRepository, MemberRepository memberRepository, BookRepository bookRepository, FineService fineService) {
        this.borrowRecordRepository = borrowRecordRepository;
        this.memberRepository = memberRepository;
        this.bookRepository = bookRepository;
        this.fineService = fineService;
    }

    public List<BorrowRecord> findAll() { return borrowRecordRepository.findAll(); }
    public List<BorrowRecord> getAllBorrows() { return findAll(); }
    public BorrowRecord getBorrowById(Long id) { return borrowRecordRepository.findById(id).orElse(null); }
    public List<BorrowRecord> findByMember(Member member) { return borrowRecordRepository.findByMemberOrderByBorrowDateDesc(member); }
    public List<BorrowRecord> getBorrowHistory(Long memberId) {
        return borrowRecordRepository.findAll().stream()
                .filter(r -> r.getMember() != null && memberId.equals(r.getMember().getId()))
                .collect(Collectors.toList());
    }
    public List<BorrowRecord> getActiveBorrowsByMember(Long memberId) {
        return getBorrowHistory(memberId).stream()
                .filter(r -> BorrowStatus.BORROWED.name().equals(r.getStatus()) || BorrowStatus.OVERDUE.name().equals(r.getStatus()))
                .collect(Collectors.toList());
    }
    public List<BorrowRecord> getOverdueBooks() {
        LocalDate today = LocalDate.now();
        return borrowRecordRepository.findAll().stream()
                .filter(r -> r.getDueDate() != null && r.getDueDate().isBefore(today) && !BorrowStatus.RETURNED.name().equals(r.getStatus()))
                .collect(Collectors.toList());
    }

    @Transactional
    public BorrowRecord borrow(Long memberId, Long bookId, LocalDate dueDate) {
        Member member = memberRepository.findById(memberId).orElseThrow();
        Book book = bookRepository.findById(bookId).orElseThrow();
        if (!member.isActive()) throw new IllegalStateException("Tài khoản đang bị khóa");
        if (book.getAvailableCopies() <= 0) throw new IllegalStateException("Sách đã hết");

        BorrowRecord record = new BorrowRecord();
        record.setMember(member);
        record.setBook(book);
        record.setBorrowDate(LocalDate.now());
        record.setDueDate(dueDate == null ? LocalDate.now().plusDays(14) : dueDate);
        record.setStatus(BorrowStatus.BORROWED);
        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookRepository.save(book);
        return borrowRecordRepository.save(record);
    }
    public BorrowRecord borrowBook(Long memberId, Long bookId) { return borrow(memberId, bookId, LocalDate.now().plusDays(14)); }

    @Transactional
    public BorrowRecord returnBook(Long borrowId) {
        BorrowRecord record = borrowRecordRepository.findById(borrowId).orElseThrow();
        if (BorrowStatus.RETURNED.name().equals(record.getStatus())) return record;
        record.setReturnDate(LocalDate.now());
        long overdueDays = Math.max(0, record.getReturnDate().toEpochDay() - record.getDueDate().toEpochDay());
        record.setStatus(overdueDays > 0 ? BorrowStatus.OVERDUE : BorrowStatus.RETURNED);
        Book book = record.getBook();
        book.setAvailableCopies(book.getAvailableCopies() + 1);
        bookRepository.save(book);
        BorrowRecord saved = borrowRecordRepository.save(record);
        if (overdueDays > 0) {
            fineService.createFine(saved);
        }
        return saved;
    }

    @Transactional
    public int updateOverdueStatus() {
        List<BorrowRecord> due = borrowRecordRepository.findByStatusAndDueDateLessThanEqual(BorrowStatus.BORROWED, LocalDate.now().minusDays(1));
        for (BorrowRecord record : due) {
            record.setStatus(BorrowStatus.OVERDUE);
        }
        borrowRecordRepository.saveAll(due);
        return due.size();
    }
}
