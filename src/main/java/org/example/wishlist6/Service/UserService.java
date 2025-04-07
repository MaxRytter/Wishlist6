package org.example.wishlist6.Service;

import org.example.wishlist6.Module.User;
import org.example.wishlist6.Module.Wishlist;
import org.example.wishlist6.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;



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


    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public boolean authenticateUser(String userEmail, String userPassword) {
        User user = userRepository.findUserByEmail(userEmail);
        if (user !=null) {
            return passwordEncoder.matches(userPassword, user.getUserPassword());
        }
        return false;
    }


}