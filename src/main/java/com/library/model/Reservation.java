package com.library.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(optional = false)
    @JoinColumn(name = "book_id")
    private Book book;

    private LocalDate reservationDate;
    private LocalDate cancelledDate;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Member getMember() { return member; }
    public void setMember(Member member) { this.member = member; }
    public Book getBook() { return book; }
    public void setBook(Book book) { this.book = book; }
    public LocalDate getReservationDate() { return reservationDate; }
    public void setReservationDate(LocalDate reservationDate) { this.reservationDate = reservationDate; }
    public LocalDate getCancelledDate() { return cancelledDate; }
    public void setCancelledDate(LocalDate cancelledDate) { this.cancelledDate = cancelledDate; }
    public String getStatus() { return status == null ? null : status.name(); }
    public ReservationStatus getStatusEnum() { return status; }
    public void setStatus(ReservationStatus status) { this.status = status; }
    public void setStatus(String status) { this.status = status == null ? null : ReservationStatus.valueOf(status); }
}
