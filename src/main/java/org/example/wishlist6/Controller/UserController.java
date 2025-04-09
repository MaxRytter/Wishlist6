package org.example.wishlist6.Controller;

import org.example.wishlist6.Module.User;
import org.example.wishlist6.Service.UserService;
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

    public String redirectToUserPage() {
        return "redirect:/user";
    }

    @GetMapping("/u")
    public String showFrontPage() {
        return "index";
    }

//    @GetMapping("/user")
//    public String getUsers(Model model) {
//        /** bruges kun i testning, men her viser den alle users**/
//        List<User> users = userService.getAllUsers();
//        model.addAttribute("users", users);
//        return "user";  // This avoids a circular view path issue
//    }

    @GetMapping("/user")
    public String getUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "user-list"; // Makes frontend work and tests succeed
    }

    @GetMapping("/user/create")
    public String showCreateForm(Model model) {
        model.addAttribute("user", new User());
        return "add-user";
    }

    @PostMapping("/user/create")
    public String addUser(@ModelAttribute User user) {
        userService.addUser(user);
        return redirectToUserPage();
    }
    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable int id) {
        userService.deleteUserById(id);
        return redirectToUserPage();
    }
    @GetMapping("/user/{id}/edit")
    public String showEditUserForm(@PathVariable("id") int userId, Model model) {
        User user = userService.getUserById(userId);
        model.addAttribute("user", user);
        return "edit-user";
    }

    @PostMapping("/user/{id}/edit")
    public String updateUser(@PathVariable("id") int userId, @ModelAttribute User user) {
        user.setUserId(userId);
        userService.updateUser(user);
        return redirectToUserPage();
    }

}
