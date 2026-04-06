package com.library.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "fines")
public class Fine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "borrow_record_id")
    private BorrowRecord borrowRecord;

    private Integer overdueDays;
    private BigDecimal amount;
    private LocalDate fineDate;
    private boolean paid;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public BorrowRecord getBorrowRecord() { return borrowRecord; }
    public void setBorrowRecord(BorrowRecord borrowRecord) { this.borrowRecord = borrowRecord; }
    public Integer getOverdueDays() { return overdueDays; }
    public void setOverdueDays(Integer overdueDays) { this.overdueDays = overdueDays; }
    public double getAmount() { return amount == null ? 0.0 : amount.doubleValue(); }
    public BigDecimal getAmountDecimal() { return amount == null ? BigDecimal.ZERO : amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public void setAmount(double amount) { this.amount = BigDecimal.valueOf(amount); }
    public LocalDate getFineDate() { return fineDate; }
    public void setFineDate(LocalDate fineDate) { this.fineDate = fineDate; }
    public boolean isPaid() { return paid; }
    public void setPaid(boolean paid) { this.paid = paid; }
}
