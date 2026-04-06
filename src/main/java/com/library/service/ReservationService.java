package com.library.service;

import com.library.model.*;
import com.library.repository.BookRepository;
import com.library.repository.MemberRepository;
import com.library.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;

    public ReservationService(ReservationRepository reservationRepository, MemberRepository memberRepository, BookRepository bookRepository) {
        this.reservationRepository = reservationRepository;
        this.memberRepository = memberRepository;
        this.bookRepository = bookRepository;
    }

    public List<Reservation> findAll() { return reservationRepository.findAll(); }
    public List<Reservation> findByMember(Member member) { return reservationRepository.findByMemberOrderByReservationDateDesc(member); }

    public Reservation reserve(Long memberId, Long bookId) {
        Reservation reservation = new Reservation();
        reservation.setMember(memberRepository.findById(memberId).orElseThrow());
        reservation.setBook(bookRepository.findById(bookId).orElseThrow());
        reservation.setReservationDate(LocalDate.now());
        reservation.setStatus(ReservationStatus.ACTIVE);
        return reservationRepository.save(reservation);
    }

    public void cancel(Long id) {
        Reservation reservation = reservationRepository.findById(id).orElseThrow();
        reservation.setStatus(ReservationStatus.CANCELLED);
        reservation.setCancelledDate(LocalDate.now());
        reservationRepository.save(reservation);
    }

    // Backward-compatible methods for tests
    public Reservation makeReservation(Member member, Book book) {
        if (member == null) throw new IllegalArgumentException("member is required");
        if (book == null) throw new IllegalArgumentException("book is required");
        Reservation reservation = new Reservation();
        reservation.setMember(member);
        reservation.setBook(book);
        reservation.setReservationDate(LocalDate.now());
        reservation.setStatus(ReservationStatus.ACTIVE);
        return reservationRepository.save(reservation);
    }
    public boolean cancelReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id).orElse(null);
        if (reservation == null) return false;
        reservation.setStatus(ReservationStatus.CANCELLED);
        reservation.setCancelledDate(LocalDate.now());
        reservationRepository.save(reservation);
        return true;
    }
    public List<Reservation> getReservationsByMember(Member member) { return reservationRepository.findByMember(member); }
    public List<Reservation> getActiveReservations() { return reservationRepository.findByStatus("ACTIVE"); }
    public Reservation getReservationById(Long id) { return reservationRepository.findById(id).orElse(null); }
    public void deleteReservation(Long id) { reservationRepository.deleteById(id); }
}
