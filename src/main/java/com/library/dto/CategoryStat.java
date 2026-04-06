package com.library.dto;

public class CategoryStat {
    private final String category;
    private final long totalBooks;
    private final long totalCopies;
    private final long availableCopies;

    public CategoryStat(String category, long totalBooks, long totalCopies, long availableCopies) {
        this.category = category;
        this.totalBooks = totalBooks;
        this.totalCopies = totalCopies;
        this.availableCopies = availableCopies;
    }

    public String getCategory() { return category; }
    public long getTotalBooks() { return totalBooks; }
    public long getTotalCopies() { return totalCopies; }
    public long getAvailableCopies() { return availableCopies; }
}
