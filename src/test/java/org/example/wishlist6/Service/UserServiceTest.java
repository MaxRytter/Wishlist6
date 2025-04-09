package org.example.wishlist6.Service;

import org.example.wishlist6.Module.User;
import org.example.wishlist6.Repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User("John Doe", "john@example.com", "password123", 1);
    }

    @Test
    void testAddUser() {
        // Arrange
        User newUser = new User("testUser", "test@example.com", "password123", 0);

        // Act
        userService.addUser(newUser);

        // Assert
        verify(userRepository, times(1)).addUser(newUser);
    }

    @Test
    public void testGetAllUsers() {
        // Arrange
        User user2 = new User("Jane Smith", "jane@example.com", "password456", 2);
        List<User> users = Arrays.asList(user, user2);
        when(userRepository.getAllUsers()).thenReturn(users);

        // Act
        List<User> result = userService.getAllUsers();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(user));
        assertTrue(result.contains(user2));
    }

    @Test
    public void testDeleteUserById() {
        // Arrange
        int userId = 1;

        // Act
        userService.deleteUserById(userId);

        // Assert
        verify(userRepository, times(1)).deleteUserById(userId);
    }

    @Test
    public void testUpdateUser() {
        // Arrange & Act
        userService.updateUser(user);

        // Assert
        verify(userRepository, times(1)).updateUser(user);
    }

    @Test
    public void testGetUserById() {
        // Arrange
        int userId = 1;
        when(userRepository.getUserById(userId)).thenReturn(user);

        // Act
        User result = userService.getUserById(userId);

        // Assert
        assertNotNull(result);
        assertEquals(user.getUserId(), result.getUserId());
        assertEquals(user.getUserName(), result.getUserName());
        assertEquals(user.getUserEmail(), result.getUserEmail());
        assertEquals(user.getUserPassword(), result.getUserPassword());
    }

    @Test
    public void testAuthenticateAndGetUser_ValidCredentials() {
        // Arrange
        String email = "john@example.com";
        String password = "password123";
        when(userRepository.getUserByEmail(email)).thenReturn(user);

        // Act
        User result = userService.authenticateAndGetUser(email, password);

        // Assert
        assertNotNull(result);
        assertEquals(user, result);
    }

    @Test
    public void testAuthenticateAndGetUser_InvalidEmail() {
        // Arrange
        when(userRepository.getUserByEmail("notfound@example.com")).thenReturn(null);

        // Act
        User result = userService.authenticateAndGetUser("notfound@example.com", "password123");

        // Assert
        assertNull(result);
    }

    @Test
    public void testAuthenticateAndGetUser_InvalidPassword() {
        // Arrange
        when(userRepository.getUserByEmail("john@example.com")).thenReturn(user);

        // Act
        User result = userService.authenticateAndGetUser("john@example.com", "wrongpass");

        // Assert
        assertNull(result);
    }
}
