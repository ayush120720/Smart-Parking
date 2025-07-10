package com.example.Smart_Parking.Service;

import com.example.Smart_Parking.DTO.SlotDTO;
import com.example.Smart_Parking.Model.Slots;
import com.example.Smart_Parking.Repository.ReserveRepository;
import com.example.Smart_Parking.Repository.SlotRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class SlotService {
    private final SlotRepository slotRepository;
    private final ReserveRepository reserveRepository;

    public SlotService(SlotRepository slotRepository, ReserveRepository reserveRepository) {
        this.slotRepository = slotRepository;
        this.reserveRepository = reserveRepository;
    }

    public List<SlotDTO> getAllSlots() {
        return slotRepository.findAll().stream()
                .map(slot -> new SlotDTO(slot.getSlotId(), slot.getSlotLabel(), true)) // assume available
                .toList();
    }

    public List<SlotDTO> getAvailableSlots(LocalDate date, LocalTime startTime, LocalTime endTime) {
        return slotRepository.findAll().stream()
                .map(slot -> {
                    boolean isAvailable = !reserveRepository.existsBySlotAndDateTimeOverlap(
                            slot, date, startTime, endTime);
                    return new SlotDTO(slot.getSlotId(), slot.getSlotLabel(), isAvailable);
                })
                .toList();
    }
}
