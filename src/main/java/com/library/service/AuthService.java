package com.library.service;

import com.library.model.Member;
import com.library.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final MemberRepository memberRepository;

    public AuthService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member login(String username, String password) {
        return memberRepository.findByUsername(username)
                .filter(Member::isActive)
                .filter(m -> m.getPassword().equals(password))
                .orElse(null);
    }
}
