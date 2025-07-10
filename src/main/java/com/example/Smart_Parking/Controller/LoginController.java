package com.example.Smart_Parking.Controller;

import com.example.Smart_Parking.Model.User;
import com.example.Smart_Parking.Service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class LoginController {

    private final UserService svc;

    public LoginController(UserService svc) {
        this.svc = svc;
    }

    @GetMapping("/")
    public String root() {
        return "redirect:/Userlogin";
    }

    @GetMapping("/Userlogin")
    public String loginForm() {
        return "Userlogin";
    }

    @PostMapping("/Userlogin")
    public String loginSubmit(@RequestParam String username,
                              @RequestParam String password,
                              HttpSession session) {
        // 1) authenticate and get the User instance
        Optional<User> maybeUser = svc.authenticateAndGetUser(username, password);

        // 2) if present, stash the actual User in session
        if (maybeUser.isPresent()) {
            User user = maybeUser.get();
            session.setAttribute("user", user);
            return "redirect:/Home";
        }

        // 3) otherwise, send them to the error page
        return "redirect:/loginError";
    }

    @GetMapping("/loginError")
    public String loginErrorPage() {
        return "loginError";
    }
}
