package com.library.service;

import com.library.model.BorrowRecord;
import com.library.model.Fine;
import com.library.repository.FineRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class FineService {
    private final FineRepository fineRepository;

    public FineService(FineRepository fineRepository) { this.fineRepository = fineRepository; }

    public List<Fine> findAll() { return fineRepository.findAll(); }
    public List<Fine> getAllFines() { return findAll(); }
    public List<Fine> unpaid() { return fineRepository.findByPaidFalse(); }
    public List<Fine> getUnpaidFines() { return fineRepository.findByPaid(false); }

    public double calculateFine(int overdueDays) { return overdueDays <= 0 ? 0.0 : overdueDays * 2000.0; }

    public Fine createFine(BorrowRecord borrowRecord) {
        Fine fine = new Fine();
        fine.setBorrowRecord(borrowRecord);
        int overdueDays = 0;
        if (borrowRecord.getDueDate() != null) {
            LocalDate endDate = borrowRecord.getReturnDate() != null ? borrowRecord.getReturnDate() : LocalDate.now();
            overdueDays = Math.max(0, (int) (endDate.toEpochDay() - borrowRecord.getDueDate().toEpochDay()));
        }
        fine.setOverdueDays(overdueDays);
        fine.setAmount(calculateFine(overdueDays));
        fine.setFineDate(LocalDate.now());
        fine.setPaid(false);
        return fineRepository.save(fine);
    }

    public Fine payFine(Long id) {
        Fine fine = fineRepository.findById(id).orElseThrow();
        fine.setPaid(true);
        return fineRepository.save(fine);
    }
    public void markPaid(Long id) { payFine(id); }

    public Fine getFineById(Long id) { return fineRepository.findById(id).orElse(null); }

    public BigDecimal unpaidTotal() {
        return fineRepository.findByPaidFalse().stream().map(Fine::getAmountDecimal).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    public double getTotalUnpaidAmount() {
        return fineRepository.findByPaid(false).stream().mapToDouble(Fine::getAmount).sum();
    }
    public double getTotalFinesByMonth(int year, int month) {
        return fineRepository.findAll().stream()
                .filter(f -> f.getFineDate() != null && f.getFineDate().getYear() == year && f.getFineDate().getMonthValue() == month)
                .mapToDouble(Fine::getAmount)
                .sum();
    }
}
