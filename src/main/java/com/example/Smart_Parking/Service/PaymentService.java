package com.example.Smart_Parking.Service;

import com.example.Smart_Parking.Model.Payment;
import com.example.Smart_Parking.Model.PaymentMethod;
import com.example.Smart_Parking.Model.PaymentStatus;
import com.example.Smart_Parking.Model.Reserve;
import com.example.Smart_Parking.Model.User;
import com.example.Smart_Parking.Repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepo;

    public PaymentService(PaymentRepository paymentRepo) {
        this.paymentRepo = paymentRepo;
    }

    @Transactional
    public Payment createPayment(User user, Reserve reservation, double amount, PaymentMethod method) {
        Payment payment = new Payment();
        payment.setUser(user);
        payment.setReservation(reservation);
        payment.setAmount(amount);
        payment.setMethod(method);
        payment.setStatus(PaymentStatus.PENDING);
        payment.setPaymentDate(LocalDateTime.now());
        return paymentRepo.save(payment);
    }

    @Transactional
    public boolean updatePaymentStatus(Long paymentId, PaymentStatus status, String upiTransactionId) {
        Optional<Payment> opt = paymentRepo.findById(paymentId);
        if (opt.isEmpty()) return false;
        Payment payment = opt.get();
        payment.setStatus(status);
        if (upiTransactionId != null) payment.setUpiTransactionId(upiTransactionId);
        paymentRepo.save(payment);
        return true;
    }

    public List<Payment> getPaymentsForUser(Long userId) {
        return paymentRepo.findAllByUserUserId(userId);
    }

}
