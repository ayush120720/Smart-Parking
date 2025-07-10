package com.example.Smart_Parking.Controller;

import com.example.Smart_Parking.Service.SlotService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalTime;

@Controller
public class SlotController {
    private final SlotService slotService;

    public SlotController(SlotService slotService) {
        this.slotService = slotService;
    }

    @GetMapping("/Slots")
    public String viewSlots(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime startTime,
                            @RequestParam(required = false) Integer durationHours,
                            Model model) {

        if (date != null && startTime != null && durationHours != null) {
            model.addAttribute("availableSlots", slotService.getAvailableSlots(date, startTime, startTime.plusHours(durationHours)));
            model.addAttribute("date", date);
            model.addAttribute("startTime", startTime);
            model.addAttribute("durationHours", durationHours);
        } else {
            model.addAttribute("availableSlots", slotService.getAllSlots());
        }
        return "Slots";
    }
}
