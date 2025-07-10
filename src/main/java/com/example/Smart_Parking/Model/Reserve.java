package com.example.Smart_Parking.Model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "reservation")
public class Reserve {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "car_number", nullable = false)
    private String carNumber;

    @Column(name = "reservation_date", nullable = false)
    private LocalDate reservationDate;

    @Column(name = "reservation_start_time", nullable = false)
    private LocalTime reservationStartTime;

    @Column(name = "reservation_end_time", nullable = false)
    private LocalTime reservationEndTime;

    @Column(name = "duration_hours", nullable = false)
    private int durationHours;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userid")
    private User user;

    @ManyToOne
    @JoinColumn(name = "slot_id", nullable = false)
    private Slots slot;

    public Reserve() {}

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public LocalDate getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(LocalDate reservationDate) {
        this.reservationDate = reservationDate;
    }

    public LocalTime getReservationStartTime() {
        return reservationStartTime;
    }

    public void setReservationStartTime(LocalTime reservationStartTime) {
        this.reservationStartTime = reservationStartTime;
    }

    public LocalTime getReservationEndTime() {
        return reservationEndTime;
    }

    public void setReservationEndTime(LocalTime reservationEndTime) {
        this.reservationEndTime = reservationEndTime;
    }

    public int getDurationHours() {
        return durationHours;
    }

    public void setDurationHours(int durationHours) {
        this.durationHours = durationHours;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Slots getSlot() {
        return slot;
    }

    public void setSlot(Slots slot) {
        this.slot = slot;
    }
}
