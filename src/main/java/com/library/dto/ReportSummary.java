package com.library.dto;

import java.math.BigDecimal;

public class ReportSummary {
    private final long totalBooks;
    private final long totalMembers;
    private final long activeMembers;
    private final long borrowingCount;
    private final long overdueCount;
    private final long reservationCount;
    private final long activeReservationCount;
    private final long totalTitles;
    private final long totalCopies;
    private final long availableCopies;
    private final BigDecimal unpaidFineTotal;

    public ReportSummary(long totalBooks, long totalMembers, long activeMembers, long borrowingCount,
                         long overdueCount, long reservationCount, long activeReservationCount,
                         long totalTitles, long totalCopies, long availableCopies, BigDecimal unpaidFineTotal) {
        this.totalBooks = totalBooks;
        this.totalMembers = totalMembers;
        this.activeMembers = activeMembers;
        this.borrowingCount = borrowingCount;
        this.overdueCount = overdueCount;
        this.reservationCount = reservationCount;
        this.activeReservationCount = activeReservationCount;
        this.totalTitles = totalTitles;
        this.totalCopies = totalCopies;
        this.availableCopies = availableCopies;
        this.unpaidFineTotal = unpaidFineTotal;
    }

    public long getTotalBooks() { return totalBooks; }
    public long getTotalMembers() { return totalMembers; }
    public long getActiveMembers() { return activeMembers; }
    public long getBorrowingCount() { return borrowingCount; }
    public long getOverdueCount() { return overdueCount; }
    public long getReservationCount() { return reservationCount; }
    public long getActiveReservationCount() { return activeReservationCount; }
    public long getTotalTitles() { return totalTitles; }
    public long getTotalCopies() { return totalCopies; }
    public long getAvailableCopies() { return availableCopies; }
    public BigDecimal getUnpaidFineTotal() { return unpaidFineTotal; }
}
