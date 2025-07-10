package com.example.Smart_Parking.Service;

import com.example.Smart_Parking.DTO.SlotDTO;
import com.example.Smart_Parking.Model.Reserve;
import com.example.Smart_Parking.Model.Slots;
import com.example.Smart_Parking.Model.User;
import com.example.Smart_Parking.Repository.ReserveRepository;
import com.example.Smart_Parking.Repository.SlotRepository;
import com.example.Smart_Parking.Repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReserveService {
    private final ReserveRepository reserveRepo;
    private final SlotRepository slotRepo;
    private final UserRepository userRepo;

    public ReserveService(ReserveRepository reserveRepo,
                          SlotRepository slotRepo,
                          UserRepository userRepo) {
        this.reserveRepo = reserveRepo;
        this.slotRepo = slotRepo;
        this.userRepo = userRepo;
    }

    public List<SlotDTO> getAvailableSlots(LocalDate date, LocalTime startTime, LocalTime endTime) {
        return slotRepo.findAll().stream()
                .map(slot -> {
                    boolean isAvailable = !reserveRepo.existsBySlotAndDateTimeOverlap(
                            slot, date, startTime, endTime);
                    return new SlotDTO(slot.getSlotId(), slot.getSlotLabel(), isAvailable);
                })
                .toList();
    }

    public boolean isSlotAvailable(int slotId, LocalDate date, LocalTime start, LocalTime end) {
        Optional<Slots> slot = slotRepo.findById(slotId);
        return slot.isPresent() && !reserveRepo.existsBySlotAndDateTimeOverlap(
                slot.get(), date, start, end);
    }

    @Transactional
    public Long reserveSlot(Long userId, int slotId, LocalDate date,
                            LocalTime startTime, int durationHours, String vehicleNumber) {
        Optional<User> userOpt = userRepo.findById(userId);
        Optional<Slots> slotOpt = slotRepo.findById(slotId);
        if (userOpt.isEmpty() || slotOpt.isEmpty()) return null;

        LocalTime endTime = startTime.plusHours(durationHours);
        if (!isSlotAvailable(slotId, date, startTime, endTime)) return null;

        Reserve r = new Reserve();
        r.setUser(userOpt.get());
        r.setSlot(slotOpt.get());
        r.setReservationDate(date);
        r.setReservationStartTime(startTime);
        r.setReservationEndTime(endTime);
        r.setDurationHours(durationHours);
        r.setCarNumber(vehicleNumber);

        reserveRepo.save(r);
        return r.getId();
    }

    public Reserve getById(Long id) {
        return reserveRepo.findById(id).orElse(null);
    }
}