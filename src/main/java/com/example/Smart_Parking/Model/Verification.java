package com.example.Smart_Parking.Model;

public class Verification{
    private String email;
    private String token;
    private long expiryTime;

    public Verification(String email, String token, long expiryTime) {
        this.email = email;
        this.token = token;
        this.expiryTime = expiryTime;
    }

    // Getters
    public String getEmail() { return email; }
    public String getToken() { return token; }
    public long getExpiryTime() { return expiryTime; }
}
