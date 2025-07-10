package com.example.Smart_Parking.Repository;

import com.example.Smart_Parking.Model.Reserve;
import com.example.Smart_Parking.Model.Slots;
import com.example.Smart_Parking.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface ReserveRepository extends JpaRepository<Reserve, Long> {

    List<Reserve> findAllByUser(User user);

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END " +
            "FROM Reserve r " +
            "WHERE r.slot = :slot " +
            "AND r.reservationDate = :date " +
            "AND NOT (r.reservationEndTime <= :startTime OR r.reservationStartTime >= :endTime)")
    boolean existsBySlotAndDateTimeOverlap(
            @Param("slot") Slots slot,
            @Param("date") LocalDate date,
            @Param("startTime") LocalTime startTime,
            @Param("endTime") LocalTime endTime
    );

    @Query("SELECT r FROM Reserve r WHERE " +
            "r.reservationDate = :date AND " +
            "(r.reservationEndTime > :startTime AND r.reservationStartTime < :endTime)")
    List<Reserve> findOverlappingReservations(
            @Param("date") LocalDate date,
            @Param("startTime") LocalTime startTime,
            @Param("endTime") LocalTime endTime);


    List<Reserve> findBySlotAndReservationDate(Slots slot, LocalDate date);
}
