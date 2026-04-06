package com.library.dto;

public class TopBookStat {
    private final String bookCode;
    private final String title;
    private final String author;
    private final long borrowCount;

    public TopBookStat(String bookCode, String title, String author, long borrowCount) {
        this.bookCode = bookCode;
        this.title = title;
        this.author = author;
        this.borrowCount = borrowCount;
    }

    public String getBookCode() { return bookCode; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public long getBorrowCount() { return borrowCount; }
}
