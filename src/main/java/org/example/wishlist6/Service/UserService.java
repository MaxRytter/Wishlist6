package org.example.wishlist6.Service;

import org.example.wishlist6.Module.User;

import java.util.List;
import java.util.ArrayList;

public class UserService {
    private List<User> users = new ArrayList<>();

    public void registerUser(int userId, String userName, String userEmail, String passwordHash) {
        User newUser = new User(userId, userName, userEmail, passwordHash);
        users.add(newUser);
        System.out.println("Bruger registreret med følgende oplysninger: \n" +
                "brugernavn: " + userName + " og email: " + userEmail);
    }
    public List<User> getAllUsers() {
        return users;
    }

}
