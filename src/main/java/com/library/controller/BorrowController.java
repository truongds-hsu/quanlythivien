package com.library.controller;

import com.library.model.Member;
import com.library.model.Role;
import com.library.repository.BookRepository;
import com.library.repository.MemberRepository;
import com.library.service.BorrowService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/borrow")
public class BorrowController {
    private final BorrowService borrowService;
    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;

    public BorrowController(BorrowService borrowService, MemberRepository memberRepository, BookRepository bookRepository) {
        this.borrowService = borrowService;
        this.memberRepository = memberRepository;
        this.bookRepository = bookRepository;
    }

    @GetMapping
    public String list(Model model, HttpSession session, @RequestParam(required = false) String error) {
        Member currentUser = (Member) session.getAttribute("currentUser");
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("records", currentUser.getRole() == Role.ADMIN ? borrowService.findAll() : borrowService.findByMember(currentUser));
        model.addAttribute("members", memberRepository.findAll());
        model.addAttribute("books", bookRepository.findAll());
        model.addAttribute("today", LocalDate.now().plusDays(14));
        model.addAttribute("error", error);
        return "borrow/list";
    }

    @PostMapping("/new")
    public String borrow(@RequestParam(required = false) Long memberId, @RequestParam Long bookId, @RequestParam String dueDate, HttpSession session) {
        try {
            Member currentUser = (Member) session.getAttribute("currentUser");
            Long effectiveMemberId = currentUser.getRole() == Role.ADMIN && memberId != null ? memberId : currentUser.getId();
            borrowService.borrow(effectiveMemberId, bookId, LocalDate.parse(dueDate));
            return "redirect:/borrow";
        } catch (Exception e) {
            return "redirect:/borrow?error=" + e.getMessage().replace(" ", "%20");
        }
    }

    @PostMapping("/return/{id}")
    public String returnBook(@PathVariable Long id) {
        borrowService.returnBook(id);
        return "redirect:/borrow";
    }
}
