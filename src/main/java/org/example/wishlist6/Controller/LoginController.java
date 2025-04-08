package org.example.wishlist6.Controller;

import org.example.wishlist6.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller

public class LoginController {
@Autowired
    private UserService userService;

@GetMapping("/login")
    public String showLoginPage() {
    return "login";
}

@PostMapping("/login")
    public String loginUser(@RequestParam String userEmail,@RequestParam String userPassword, Model model) {
    boolean isAuthenticated = userService.authenticateUser(userEmail, userPassword);
    if (isAuthenticated) {
        return "redirect:/home";
    } else
    {
        model.addAttribute("Fejl", "Brugeren kunne ikke genkendes, prøv igen");
        return "login";
    }
}
}
