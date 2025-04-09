package org.example.wishlist6.Controller;

import org.example.wishlist6.Module.User;
import org.example.wishlist6.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;



@Controller

public class LoginController {
@Autowired
    private UserService userService;

@GetMapping("/login")
    public String showLoginPage() {
    return "login";
}

@PostMapping("/login")
    public String loginUser(@RequestParam String userEmail,@RequestParam String userPassword, Model model, HttpSession session) {

    System.out.println("User Email: " + userEmail);
    System.out.println("User Password: " + userPassword); //slet prints senere, bruger dem bare til debugging

    boolean isAuthenticated = userService.authenticateUser(userEmail, userPassword);
    if (isAuthenticated) {
        session.setAttribute("userEmail", userEmail);
        return "redirect:/home";
    } else {
        model.addAttribute("errorMessage", "Brugeren kunne ikke genkendes, prøv igen");
        return "login";
    }
}

@GetMapping("/home")
    public String showHome(HttpSession session, Model model) {
    String userEmail = (String) session.getAttribute("userEmail");
    if (userEmail == null){
        return "redirect:/login";
    }

    User user = userService.getUserByEmail(userEmail);
    if (user != null) {
        model.addAttribute("userEmail", userEmail);
        model.addAttribute("userName", user.getUserName());
    }
    return "home";
}


@GetMapping("/logout")
    public String logout(HttpSession session) {
    session.invalidate();
    return "redirect:/login";
}
}
