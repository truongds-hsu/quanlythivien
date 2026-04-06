package com.library.repository;

import com.library.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUsername(String username);
    Optional<Member> findByPhone(String phone);
    Optional<Member> findByMemberCode(String memberCode);
    List<Member> findByActive(boolean active);
}
