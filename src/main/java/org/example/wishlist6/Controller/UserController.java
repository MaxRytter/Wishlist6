package org.example.wishlist6.Controller;

import jakarta.servlet.http.HttpSession;
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

    @GetMapping("/u")
    public String showFrontPage() {
        return "index";
    }

    @GetMapping("/user")
    public String getUsers(HttpSession session, Model model) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }
        /** bruges kun i testning, men her viser den alle users**/
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "user";
    }

    @GetMapping("/user/create")
    public String showCreateForm(Model model) {
        model.addAttribute("user", new User());
        return "add-user";
    }

    @PostMapping("/user/create")
    public String addUser(@ModelAttribute User user) {
        userService.addUser(user);
        return "redirect:/user";
    }
    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable int id) {
        userService.deleteUserById(id);
        return "redirect:/user";
    }
    @GetMapping("/user/{id}/edit")
    public String showEditUserForm(@PathVariable("id") int userId, HttpSession session, Model model) {
        Integer loggedInUserId = (Integer) session.getAttribute("userId");
        if (loggedInUserId == null || loggedInUserId != userId) {
            return "redirect:/";
        }

        User user = userService.getUserById(userId);
        model.addAttribute("user", user);
        return "edit-user";
    }

    @PostMapping("/user/{id}/edit")
    public String updateUser(@PathVariable("id") int userId, @ModelAttribute User user, HttpSession session) {
        Integer loggedInUserId = (Integer) session.getAttribute("userId");
        if (loggedInUserId == null || loggedInUserId != userId) {
            return "redirect:/";
        }
        user.setUserId(userId);
        userService.updateUser(user);
        return "redirect:/home";
    }

}
