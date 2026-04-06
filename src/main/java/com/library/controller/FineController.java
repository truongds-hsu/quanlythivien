package com.library.controller;

import com.library.model.Member;
import com.library.model.Role;
import com.library.service.FineService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.FORBIDDEN;

@Controller
@RequestMapping("/fines")
public class FineController {
    private final FineService fineService;

    public FineController(FineService fineService) { this.fineService = fineService; }

    @GetMapping
    public String list(Model model, HttpSession session) {
        requireAdmin(session);
        model.addAttribute("fines", fineService.findAll());
        return "fines/list";
    }

    @PostMapping("/pay/{id}")
    public String pay(@PathVariable Long id, HttpSession session) {
        requireAdmin(session);
        fineService.markPaid(id);
        return "redirect:/fines";
    }

    private void requireAdmin(HttpSession session) {
        Member currentUser = (Member) session.getAttribute("currentUser");
        if (currentUser == null || currentUser.getRole() != Role.ADMIN) {
            throw new ResponseStatusException(FORBIDDEN, "Bạn không có quyền thực hiện chức năng này");
        }
    }
}
