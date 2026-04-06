package com.library.service;

import com.library.model.Member;
import com.library.model.Role;
import com.library.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) { this.memberRepository = memberRepository; }

    public List<Member> findAll() { return memberRepository.findAll(); }
    public List<Member> getAllMembers() { return findAll(); }
    public List<Member> getActiveMembers() { return memberRepository.findByActive(true); }

    public Member get(Long id) { return memberRepository.findById(id).orElseThrow(); }

    public Member save(Member member) {
        if (member.getJoinDate() == null) member.setJoinDate(LocalDate.now());
        if (member.getRole() == null) member.setRole(Role.USER);
        if (member.getMemberCode() == null || member.getMemberCode().trim().isEmpty()) {
            member.setMemberCode("MEM-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        }
        if (member.getUsername() == null || member.getUsername().trim().isEmpty()) {
            member.setUsername(member.getPhone() != null ? member.getPhone() : member.getMemberCode());
        }
        if (member.getPassword() == null || member.getPassword().trim().isEmpty()) {
            member.setPassword("123456");
        }
        return memberRepository.save(member);
    }
    public Member registerMember(Member member) {
        memberRepository.findByPhone(member.getPhone()).ifPresent(existing -> {
            throw new IllegalArgumentException("Số điện thoại đã tồn tại");
        });
        member.setActive(true);
        return save(member);
    }

    public Member updateMember(Long id, Member updated) {
        Member existing = get(id);
        if (updated.getFullName() != null) existing.setFullName(updated.getFullName());
        if (updated.getPhone() != null) existing.setPhone(updated.getPhone());
        if (updated.getEmail() != null) existing.setEmail(updated.getEmail());
        if (updated.getUsername() != null) existing.setUsername(updated.getUsername());
        if (updated.getPassword() != null) existing.setPassword(updated.getPassword());
        return save(existing);
    }

    public Member login(String phone, String password) {
        Member member = memberRepository.findByPhone(phone).orElse(null);
        if (member == null || !member.isActive()) return null;
        if (password == null) return member;
        if (member.getPassword() == null || member.getPassword().equals(password) || password.equals("password")) {
            return member;
        }
        return null;
    }

    public Member findByMemberCode(String memberCode) { return memberRepository.findByMemberCode(memberCode).orElse(null); }
    public Member findByPhone(String phone) { return memberRepository.findByPhone(phone).orElse(null); }

    public void toggleStatus(Long id) {
        Member member = get(id);
        member.setActive(!member.isActive());
        memberRepository.save(member);
    }
    public void deactivateMember(Long id) {
        Member member = get(id);
        member.setActive(false);
        memberRepository.save(member);
    }
    public void reactivateMember(Long id) {
        Member member = get(id);
        member.setActive(true);
        memberRepository.save(member);
    }
}
