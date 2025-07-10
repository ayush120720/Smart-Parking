package com.example.Smart_Parking.Service;

import com.example.Smart_Parking.Model.User;
import com.example.Smart_Parking.Repository.UserRepository;
import jakarta.validation.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private final UserRepository repo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }


    public boolean register(User user) {
        if (repo.findByEmail(user.getEmail()).isPresent()){
            return false; // Username or Email taken
        }

        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        user.setEmailVerified(false);
        repo.save(user);
        return true;
    }

    public boolean markEmailVerified(String email) {
        Optional<User> opt = repo.findByEmail(email);
        if (opt.isEmpty()) return false;
        User u = opt.get();
        u.setEmailVerified(true);
        repo.save(u);
        return true;
    }

    public boolean authenticate(String username, String rawPassword) {
        return authenticateAndGetUser(username, rawPassword).isPresent();
    }

    public Optional<User> authenticateAndGetUser(String email, String rawPassword) {
        Optional<User> opt = repo.findByEmail(email);
        if (opt.isPresent()) {
            User user = opt.get();
            if (!user.isEmailVerified()) {
                return Optional.empty();
            }
            if (passwordEncoder.matches(rawPassword, user.getPassword())) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    public Optional<User> getUserById(Long id) {
        return repo.findById(id);
    }
}
