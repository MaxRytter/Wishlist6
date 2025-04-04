package org.example.wishlist6.Service;

import org.example.wishlist6.Module.User;
import org.example.wishlist6.Module.Wishlist;
import org.example.wishlist6.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService{

    @Autowired
    private UserRepository userRepository;


    public void addUser(User user) {
        userRepository.addUser(user);
    }

    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }



}
/**
import java.util.Scanner;

public class UserRegistration {
    private final WishlistRepository wishlistRepository;


    public UserRegistration(WishlistRepository wishlistRepository) {
        this.wishlistRepository = wishlistRepository;
    }


    public void register() {

        Scanner scanner = new Scanner(System.in);


        System.out.println("Indtast brugernavn");
        String userName = scanner.nextLine();

        System.out.println("Indtast email");
        String userEmail = scanner.nextLine();

        System.out.println("Indtast password");
        String passwordHash = scanner.nextLine();

        // String passwordHash = passwordHash(password);


        if (userName.isEmpty() || userEmail.isEmpty() || passwordHash.isEmpty()) {
            System.out.println("Alle felter skal udfyldes.");
            return;
        }



        User user = new User(userName, userEmail, passwordHash);
        wishlistRepository.saveUser(user);


    }

}

**/