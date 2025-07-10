package com.example.Smart_Parking.Controller;

import com.example.Smart_Parking.DTO.PaymentDTO;
import com.example.Smart_Parking.Model.*;
import com.example.Smart_Parking.Repository.PaymentRepository;
import com.example.Smart_Parking.Repository.ReserveRepository;
import com.example.Smart_Parking.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@Controller
@RequestMapping("/Payment")
@CrossOrigin(origins = "*")
public class PaymentController {

    @Autowired private PaymentRepository paymentRepo;
    @Autowired private UserRepository userRepo;
    @Autowired private ReserveRepository reserveRepo;

    // Show payment form with pre-filled hidden fields
    @GetMapping
    public String showPaymentForm(@RequestParam("userId") Long userId,
                                  @RequestParam("reserveId") Long reserveId,
                                  Model model) {

        Optional<Reserve> reserveOpt = reserveRepo.findById(reserveId);
        if (reserveOpt.isEmpty()) {
            model.addAttribute("error", "Invalid Reservation.");
            return "error"; // or redirect
        }

        Reserve reserve = reserveOpt.get();
        int duration = reserve.getDurationHours(); // Assuming this exists
        double amount = duration * 100.0;

        PaymentDTO payment = new PaymentDTO(); // Create empty form object
        payment.setUserId(userId);
        payment.setReserveId(reserveId);
        payment.setAmount(amount);

        model.addAttribute("payment", payment);  // Bind to form
        return "Payment";  // View: Payment.html
    }

    // Handle form submission
    @PostMapping("/make")
    public String makePayment(@ModelAttribute PaymentDTO dto, Model model) {
        Optional<User> userOpt = userRepo.findById(dto.getUserId());
        Optional<Reserve> reserveOpt = reserveRepo.findById(dto.getReserveId());

        if (userOpt.isEmpty() || reserveOpt.isEmpty()) {
            model.addAttribute("result", "Invalid User ID or Reserve ID");
            model.addAttribute("success", false);
            model.addAttribute("payment", dto);
            return "Payment";
        }

        Payment payment = new Payment();
        payment.setUser(userOpt.get());
        payment.setReservation(reserveOpt.get());
        payment.setAmount(dto.getAmount());
        payment.setMethod(dto.getMethod());
        payment.setStatus(PaymentStatus.SUCCESS);
        payment.setUpiTransactionId(
                dto.getMethod() == PaymentMethod.UPI
                        ? dto.getUpiTransactionId()
                        : null
        );
        payment.setPaymentDate(LocalDateTime.now());

        paymentRepo.save(payment);

        System.out.println("Method = " + dto.getMethod());

        model.addAttribute("result", "Payment successful!");
        model.addAttribute("success", true);
        model.addAttribute("payment", new PaymentDTO());  // Clear form after success

        Payment saved = paymentRepo.save(payment);

        return "redirect:/Payment/success/" + saved.getPayment_id();
    }
}
