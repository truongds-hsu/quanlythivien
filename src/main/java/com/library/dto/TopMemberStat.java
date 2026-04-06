package com.library.dto;

public class TopMemberStat {
    private final String memberCode;
    private final String fullName;
    private final long borrowCount;
    private final long overdueCount;

    public TopMemberStat(String memberCode, String fullName, long borrowCount, long overdueCount) {
        this.memberCode = memberCode;
        this.fullName = fullName;
        this.borrowCount = borrowCount;
        this.overdueCount = overdueCount;
    }

    public String getMemberCode() { return memberCode; }
    public String getFullName() { return fullName; }
    public long getBorrowCount() { return borrowCount; }
    public long getOverdueCount() { return overdueCount; }
}
