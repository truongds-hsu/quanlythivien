package com.library.controller;

import com.library.model.Member;
import com.library.model.Role;
import com.library.repository.BookRepository;
import com.library.repository.MemberRepository;
import com.library.service.ReservationService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Controller
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationService reservationService;
    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;

    public ReservationController(ReservationService reservationService, MemberRepository memberRepository, BookRepository bookRepository) {
        this.reservationService = reservationService;
        this.memberRepository = memberRepository;
        this.bookRepository = bookRepository;
    }

    @GetMapping
    public String list(Model model, HttpSession session, @RequestParam(required = false) String error) {
        Member currentUser = (Member) session.getAttribute("currentUser");
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("reservations", currentUser.getRole() == Role.ADMIN ? reservationService.findAll() : reservationService.findByMember(currentUser));
        model.addAttribute("members", memberRepository.findAll());
        model.addAttribute("books", bookRepository.findAll());
        model.addAttribute("error", error);
        return "reservations/list";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model, HttpSession session) {
        Member currentUser = (Member) session.getAttribute("currentUser");
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("reservation", reservationService.findAll().stream()
                .filter(r -> id.equals(r.getId()))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Không tìm thấy phiếu đặt trước")));
        return "reservations/detail";
    }

    @PostMapping("/new")
    public String reserve(@RequestParam(required = false) Long memberId, @RequestParam Long bookId, HttpSession session) {
        try {
            Member currentUser = (Member) session.getAttribute("currentUser");
            Long effectiveMemberId = currentUser.getRole() == Role.ADMIN && memberId != null ? memberId : currentUser.getId();
            reservationService.reserve(effectiveMemberId, bookId);
            return "redirect:/reservations";
        } catch (Exception e) {
            return "redirect:/reservations?error=" + e.getMessage().replace(" ", "%20");
        }
    }

    @PostMapping("/cancel/{id}")
    public String cancel(@PathVariable Long id) {
        reservationService.cancel(id);
        return "redirect:/reservations";
    }
}
