package com.example.Smart_Parking.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {

    @GetMapping("/Home")
    public String homePage(@RequestParam(required = false) String user, Model model) {
        model.addAttribute("user", user);
        return "Home";
    }

    @GetMapping("/Reserve")
    public String showReservationPage() {
        return "Reserve"; // corresponds to Reserve.html or Reserve.html inside /templates if using Thymeleaf
    }
}
