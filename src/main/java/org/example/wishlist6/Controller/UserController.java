package org.example.wishlist6.Controller;

import org.example.wishlist6.Module.User;
import org.example.wishlist6.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public String showFrontPage() {
        return "index";
    }

    @GetMapping("/users")
    public String getUsers(Model model) {
        /** bruges kun i testning, men her viser den alle users**/
          List<User> users = userService.getAllUsers();
          model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/users/create")
    public String showCreateForm(Model model) {
        model.addAttribute("user", new User());
        return "add-user";
    }

    @PostMapping("/users/create")
    public String addUser(@ModelAttribute User user) {
        userService.addUser(user);
        return "redirect:/users";
    }

}
