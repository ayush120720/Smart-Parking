package com.example.Smart_Parking.Controller;

import com.example.Smart_Parking.Model.Payment;
import com.example.Smart_Parking.Repository.PaymentRepository;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.OutputStream;
import java.util.Optional;

@Controller
@RequestMapping("/Payment")
public class PaymentSuccessController {

    @Autowired
    private PaymentRepository paymentRepo;

    @GetMapping("/success/{paymentId}")
    public String showSuccessPage(@PathVariable Long paymentId, Model model) {
        model.addAttribute("paymentId", paymentId);
        return "Success";
    }

    @GetMapping("/receipt/{paymentId}")
    public void downloadReceipt(@PathVariable Long paymentId, HttpServletResponse response) {
        Optional<Payment> paymentOpt = paymentRepo.findById(paymentId);

        if (paymentOpt.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        Payment payment = paymentOpt.get();

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=receipt_" + paymentId + ".pdf");

        try (OutputStream out = response.getOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, out);
            document.open();

            document.add(new Paragraph("Smart Parking - Payment Receipt"));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Payment ID: " + payment.getPayment_id()));
            document.add(new Paragraph("User ID: " + payment.getUser().getUserid()));
            document.add(new Paragraph("Slot ID: " + payment.getReservation().getSlot().getSlotId()));
            document.add(new Paragraph("Reservation ID: " + payment.getReservation().getId()));
            document.add(new Paragraph("Amount Paid: ₹" + payment.getAmount()));
            document.add(new Paragraph("Payment Method: " + payment.getMethod()));
            document.add(new Paragraph("Transaction ID (UPI): " +
                    (payment.getUpiTransactionId() != null ? payment.getUpiTransactionId() : "N/A")));
            document.add(new Paragraph("Payment Status: " + payment.getStatus()));
            document.add(new Paragraph("Date & Time: " + payment.getPaymentDate().toString()));

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
