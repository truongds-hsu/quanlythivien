package com.library.service;

import com.library.model.BorrowRecord;
import com.library.model.BorrowStatus;
import com.library.model.Member;
import com.library.repository.BorrowRecordRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReminderService {
    private final BorrowRecordRepository borrowRecordRepository;
    private volatile List<String> latestGlobalNotifications = new ArrayList<>();

    public ReminderService(BorrowRecordRepository borrowRecordRepository) {
        this.borrowRecordRepository = borrowRecordRepository;
    }

    public List<String> getNotificationsFor(Member member) {
        return borrowRecordRepository.findByMemberOrderByBorrowDateDesc(member).stream()
                .filter(r -> BorrowStatus.RETURNED != r.getStatusEnum())
                .map(this::toMessage)
                .filter(s -> s != null)
                .collect(Collectors.toList());
    }

    public List<String> getGlobalNotifications() { return latestGlobalNotifications; }

    private String toMessage(BorrowRecord r) {
        long days = ChronoUnit.DAYS.between(LocalDate.now(), r.getDueDate());
        if (days < 0) {
            return "Quá hạn trả sách '" + r.getBook().getTitle() + "' " + Math.abs(days) + " ngày.";
        }
        if (days <= 3) {
            return "Sách '" + r.getBook().getTitle() + "' sẽ đến hạn sau " + days + " ngày (hạn: " + r.getDueDate() + ").";
        }
        return null;
    }

    @Scheduled(cron = "0 0 */6 * * *")
    public void refreshGlobalNotifications() {
        List<BorrowRecord> dueSoon = borrowRecordRepository.findByStatusAndDueDateBetween(BorrowStatus.BORROWED, LocalDate.now(), LocalDate.now().plusDays(3));
        List<BorrowRecord> overdue = borrowRecordRepository.findByStatusAndDueDateLessThanEqual(BorrowStatus.BORROWED, LocalDate.now().minusDays(1));
        List<String> messages = new ArrayList<>();
        dueSoon.forEach(r -> messages.add("Nhắc hạn: " + r.getMember().getFullName() + " - " + r.getBook().getTitle() + " (" + r.getDueDate() + ")"));
        overdue.forEach(r -> messages.add("Quá hạn: " + r.getMember().getFullName() + " - " + r.getBook().getTitle() + " (" + r.getDueDate() + ")"));
        latestGlobalNotifications = messages;
    }
}
