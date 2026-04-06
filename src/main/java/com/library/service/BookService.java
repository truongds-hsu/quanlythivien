package com.library.service;

import com.library.model.Book;
import com.library.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) { this.bookRepository = bookRepository; }

    public List<Book> findAll() { return bookRepository.findAll(); }
    public List<Book> getAllBooks() { return findAll(); }

    public Book get(Long id) { return bookRepository.findById(id).orElseThrow(); }
    public Book getBookById(Long id) { return get(id); }

    public Book save(Book book) {
        if (book.getAvailableCopies() > book.getTotalCopies()) {
            book.setAvailableCopies(book.getTotalCopies());
        }
        if (book.getAvailableCopies() < 0) {
            book.setAvailableCopies(0);
        }
        return bookRepository.save(book);
    }
    public Book addBook(Book book) { return save(book); }

    public Book updateBook(Long id, Book updated) {
        Book existing = get(id);
        existing.setBookCode(updated.getBookCode());
        existing.setTitle(updated.getTitle());
        existing.setAuthor(updated.getAuthor());
        existing.setPublisher(updated.getPublisher());
        existing.setCategory(updated.getCategory());
        existing.setTotalCopies(updated.getTotalCopies());
        existing.setAvailableCopies(updated.getAvailableCopies());
        return save(existing);
    }

    public void delete(Long id) { bookRepository.deleteById(id); }
    public void deleteBook(Long id) { delete(id); }

    public List<Book> search(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) return findAll();
        return bookRepository.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCaseOrCategoryContainingIgnoreCase(keyword, keyword, keyword);
    }
    public List<Book> searchBooks(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) return findAll();
        return bookRepository.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(keyword, keyword);
    }
    public List<Book> searchByTitle(String title) { return bookRepository.findByTitleContainingIgnoreCase(title); }
    public List<Book> searchByAuthor(String author) { return bookRepository.findByAuthorContainingIgnoreCase(author); }
}
