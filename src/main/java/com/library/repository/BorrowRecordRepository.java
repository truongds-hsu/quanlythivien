package com.library.repository;

import com.library.model.BorrowRecord;
import com.library.model.BorrowStatus;
import com.library.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface BorrowRecordRepository extends JpaRepository<BorrowRecord, Long> {
    List<BorrowRecord> findByMemberOrderByBorrowDateDesc(Member member);
    List<BorrowRecord> findByStatus(BorrowStatus status);
    List<BorrowRecord> findByStatusAndDueDateLessThanEqual(BorrowStatus status, LocalDate dueDate);
    List<BorrowRecord> findByStatusAndDueDateBetween(BorrowStatus status, LocalDate start, LocalDate end);
}
