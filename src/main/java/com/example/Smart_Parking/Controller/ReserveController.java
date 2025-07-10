package com.example.Smart_Parking.Controller;

import com.example.Smart_Parking.DTO.SlotDTO;
import com.example.Smart_Parking.Model.Slots;
import com.example.Smart_Parking.Model.User;
import com.example.Smart_Parking.Service.ReserveService;
import jakarta.servlet.http.HttpSession;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/reserve")
public class ReserveController {
    private final ReserveService reservationService;

    public ReserveController(ReserveService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public String showReservationForm(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/Userlogin";
        }

        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now().withSecond(0).withNano(0);
        String nowTime = now.format(DateTimeFormatter.ofPattern("HH:mm"));

        model.addAttribute("user", user);
        model.addAttribute("today", today);
        model.addAttribute("nowTime", nowTime);
        return "Reserve";
    }

    @PostMapping("/check")
    public String checkAvailableSlots(@RequestParam String vehicleNumber,
                                      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime startTime,
                                      @RequestParam int durationHours,
                                      HttpSession session,
                                      Model model,
                                      RedirectAttributes redirectAttrs) {

        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/Userlogin";
        }

        LocalTime endTime = startTime.plusHours(durationHours);

        List<SlotDTO> availableSlots = reservationService.getAvailableSlots(date, startTime, endTime);

        if (availableSlots.stream().noneMatch(SlotDTO::isAvailable)) {
            redirectAttrs.addFlashAttribute("error", "No slots available for selected time.");
            return "redirect:/reserve";
        }

        model.addAttribute("availableSlots", availableSlots);
        model.addAttribute("vehicleNumber", vehicleNumber);
        model.addAttribute("date", date);
        model.addAttribute("startTime", startTime);
        model.addAttribute("durationHours", durationHours);
        model.addAttribute("user", user);

        return "Slots";
    }

    @PostMapping("/finalize")
    public String finalizeReservation(@RequestParam int slotId,
                                      @RequestParam String vehicleNumber,
                                      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime startTime,
                                      @RequestParam int durationHours,
                                      HttpSession session,
                                      RedirectAttributes redirectAttrs) {
        User u = (User) session.getAttribute("user");
        if (u == null) {
            redirectAttrs.addFlashAttribute("error", "Login first");
            return "redirect:/Userlogin";
        }

        boolean ok = reservationService.isSlotAvailable(slotId, date, startTime, startTime.plusHours(durationHours));
        if (!ok) {
            redirectAttrs.addFlashAttribute("error", "Slot taken");
            return "redirect:/reserve";
        }

        Long resId = reservationService.reserveSlot(u.getUserid(), slotId, date, startTime, durationHours, vehicleNumber);
        if (resId != null) {
            return "redirect:/Payment?userId=" + u.getUserid() + "&reserveId=" + resId;
        }
        redirectAttrs.addFlashAttribute("error", "Reservation failed");
        return "redirect:/reserve";
    }
}
