package com.library.controller;

import com.library.model.Member;
import com.library.model.Role;
import com.library.service.ReportService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.FORBIDDEN;

@Controller
@RequestMapping("/reports")
public class ReportController {
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping
    public String index(Model model, HttpSession session) {
        Member currentUser = requireAdmin(session);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("summary", reportService.getSummary());
        model.addAttribute("categoryStats", reportService.getCategoryStats());
        model.addAttribute("topBooks", reportService.getTopBorrowedBooks(5));
        model.addAttribute("topMembers", reportService.getTopBorrowers(5));
        model.addAttribute("overdueRecords", reportService.getOverdueRecords());
        model.addAttribute("lowStockBooks", reportService.getLowStockBooks(2));
        model.addAttribute("recentReservations", reportService.getRecentReservations(5));
        return "reports/index";
    }

    private Member requireAdmin(HttpSession session) {
        Member currentUser = (Member) session.getAttribute("currentUser");
        if (currentUser == null || currentUser.getRole() != Role.ADMIN) {
            throw new ResponseStatusException(FORBIDDEN, "Bạn không có quyền xem báo cáo");
        }
        return currentUser;
    }
}
