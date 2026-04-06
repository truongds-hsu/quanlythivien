package com.library.controller;

import com.library.model.Member;
import com.library.model.Role;
import com.library.repository.BookRepository;
import com.library.repository.BorrowRecordRepository;
import com.library.repository.MemberRepository;
import com.library.repository.ReservationRepository;
import com.library.service.FineService;
import com.library.service.ReminderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;
    private final BorrowRecordRepository borrowRecordRepository;
    private final ReservationRepository reservationRepository;
    private final FineService fineService;
    private final ReminderService reminderService;

    public HomeController(BookRepository bookRepository, MemberRepository memberRepository, BorrowRecordRepository borrowRecordRepository, ReservationRepository reservationRepository, FineService fineService, ReminderService reminderService) {
        this.bookRepository = bookRepository;
        this.memberRepository = memberRepository;
        this.borrowRecordRepository = borrowRecordRepository;
        this.reservationRepository = reservationRepository;
        this.fineService = fineService;
        this.reminderService = reminderService;
    }

    @GetMapping("/")
    public String index(Model model, HttpSession session) {
        Member currentUser = (Member) session.getAttribute("currentUser");
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("bookCount", bookRepository.count());
        model.addAttribute("memberCount", memberRepository.count());
        model.addAttribute("borrowCount", borrowRecordRepository.count());
        model.addAttribute("borrowingCount", borrowRecordRepository.count());
        model.addAttribute("activeReservationCount", reservationRepository.count());
        model.addAttribute("reservationCount", reservationRepository.count());
        model.addAttribute("unpaidFineTotal", fineService.unpaidTotal());
        Object reminders = currentUser.getRole() == Role.ADMIN ? reminderService.getGlobalNotifications() : reminderService.getNotificationsFor(currentUser);
        model.addAttribute("notifications", reminders);
        model.addAttribute("reminders", reminders);
        return "index";
    }
}
