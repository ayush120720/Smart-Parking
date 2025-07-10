package com.example.Smart_Parking.Model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Payment_ID")
    private int Payment_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "User_ID", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Reserve_ID", nullable = false)
    private Reserve reservation;

    @Column(name = "Amount", nullable = false)
    private Double amount;

    @Column(name = "Payment_Status", nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @Column(name = "Payment_Method", nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentMethod method;

    @Column(name = "UPI_Transaction_Id")
    private String upiTransactionId;

    @Column(name = "Payment_Date", nullable = false)
    private LocalDateTime paymentDate;

    // Constructors, getters, setters
    public Payment() {
        this.paymentDate = LocalDateTime.now();
    }

    public int getPayment_id() {
        return Payment_id;
    }

    public void setPayment_id(int payment_id) {
        Payment_id = payment_id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Reserve getReservation() {
        return reservation;
    }

    public void setReservation(Reserve reservation) {
        this.reservation = reservation;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public PaymentMethod getMethod() {
        return method;
    }

    public void setMethod(PaymentMethod method) {
        this.method = method;
    }

    public String getUpiTransactionId() {
        return upiTransactionId;
    }

    public void setUpiTransactionId(String upiTransactionId) {
        this.upiTransactionId = upiTransactionId;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = LocalDateTime.now();
    }
}


