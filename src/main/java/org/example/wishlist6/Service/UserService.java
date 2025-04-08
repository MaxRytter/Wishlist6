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
    public void deleteUserById(int id) {
        userRepository.deleteUserById(id);
    }
    public void updateUser(User user) {
        userRepository.updateUser(user);
    }
    public User getUserById(int userId) {
        return userRepository.getUserById(userId);
    }
    public User getUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

//    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public boolean authenticateUser(String userEmail, String userPassword) {
        User user = userRepository.findUserByEmail(userEmail);
        if (user !=null) {
            return userPassword.equals(user.getUserPassword());
        }
        return false;
    }


}