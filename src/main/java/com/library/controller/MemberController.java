package com.library.controller;

import com.library.model.Member;
import com.library.model.Role;
import com.library.service.MemberService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.FORBIDDEN;

@Controller
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) { this.memberService = memberService; }

    @GetMapping
    public String list(Model model, HttpSession session) {
        requireAdmin(session);
        model.addAttribute("members", memberService.findAll());
        return "members/list";
    }

    @GetMapping("/new")
    public String createForm(Model model, HttpSession session) {
        requireAdmin(session);
        model.addAttribute("member", new Member());
        return "members/form";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model, HttpSession session) {
        requireAdmin(session);
        model.addAttribute("member", memberService.get(id));
        return "members/form";
    }

    @PostMapping
    public String save(@ModelAttribute Member member, HttpSession session) {
        requireAdmin(session);
        memberService.save(member);
        return "redirect:/members";
    }

    @PostMapping("/toggle-status/{id}")
    public String toggleStatus(@PathVariable Long id, HttpSession session) {
        requireAdmin(session);
        memberService.toggleStatus(id);
        return "redirect:/members";
    }

    private void requireAdmin(HttpSession session) {
        Member currentUser = (Member) session.getAttribute("currentUser");
        if (currentUser == null || currentUser.getRole() != Role.ADMIN) {
            throw new ResponseStatusException(FORBIDDEN, "Bạn không có quyền thực hiện chức năng này");
        }
    }
}
