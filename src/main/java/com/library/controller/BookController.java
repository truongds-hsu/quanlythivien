package com.library.controller;

import com.library.model.Book;
import com.library.model.Member;
import com.library.model.Role;
import com.library.service.BookService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.FORBIDDEN;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) { this.bookService = bookService; }

    @GetMapping
    public String list(@RequestParam(required = false) String q, Model model, HttpSession session) {
        model.addAttribute("books", bookService.search(q));
        model.addAttribute("q", q);
        model.addAttribute("currentUser", session.getAttribute("currentUser"));
        return "books/list";
    }

    @GetMapping("/new")
    public String createForm(Model model, HttpSession session) {
        requireAdmin(session);
        model.addAttribute("book", new Book());
        return "books/form";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model, HttpSession session) {
        requireAdmin(session);
        model.addAttribute("book", bookService.get(id));
        return "books/form";
    }

    @PostMapping
    public String save(@ModelAttribute Book book, HttpSession session) {
        requireAdmin(session);
        bookService.save(book);
        return "redirect:/books";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id, HttpSession session) {
        requireAdmin(session);
        bookService.delete(id);
        return "redirect:/books";
    }

    private void requireAdmin(HttpSession session) {
        Member currentUser = (Member) session.getAttribute("currentUser");
        if (currentUser == null || currentUser.getRole() != Role.ADMIN) {
            throw new ResponseStatusException(FORBIDDEN, "Bạn không có quyền thực hiện chức năng này");
        }
    }
}
