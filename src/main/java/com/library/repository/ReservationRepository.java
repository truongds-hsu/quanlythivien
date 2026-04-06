package com.library.repository;

import com.library.model.Member;
import com.library.model.Reservation;
import com.library.model.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByMemberOrderByReservationDateDesc(Member member);
    List<Reservation> findByMember(Member member);
    List<Reservation> findByStatus(String status);
    long countByStatus(ReservationStatus status);
}
