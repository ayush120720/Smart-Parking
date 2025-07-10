package com.example.Smart_Parking.DTO;

import com.example.Smart_Parking.Model.PaymentMethod;

public class PaymentDTO {

    private Long userId;
    private Long reserveId;
    private Double amount;
    private PaymentMethod method;
    private String upiTransactionId;

    // Constructors
    public PaymentDTO() {}

    public PaymentDTO(Long userId, Long reserveId, Double amount, PaymentMethod method, String upiTransactionId) {
        this.userId = userId;
        this.reserveId = reserveId;
        this.amount = amount;
        this.method = method;
        this.upiTransactionId = upiTransactionId;
    }

    // Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getReserveId() {
        return reserveId;
    }

    public void setReserveId(Long reserveId) {
        this.reserveId = reserveId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
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
}
