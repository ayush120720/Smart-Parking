package com.example.Smart_Parking.Service;

import com.example.Smart_Parking.Model.Verification;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class VerificationService {

    private Map<String,Verification> tokenMap = new HashMap<>();
    private final Random random = new Random();

    public String generateToken(String email) {
        String token = String.format("%06d", random.nextInt(999999));
        long expiry = System.currentTimeMillis() + 10 * 60 * 1000; // 10 minutes
        tokenMap.put(email, new Verification(email, token, expiry));
        return token;
    }

    public boolean verifyToken(String email, String inputToken) {
        Verification stored = tokenMap.get(email);
        if (stored == null) return false;
        if (System.currentTimeMillis() > stored.getExpiryTime()) return false;
        return stored.getToken().equals(inputToken);
    }

    public void invalidate(String email) {
        tokenMap.remove(email);
    }
}
