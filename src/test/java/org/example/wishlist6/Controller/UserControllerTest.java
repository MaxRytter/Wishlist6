//package org.example.wishlist6.Controller;
//
//import org.example.wishlist6.Module.User;
//import org.example.wishlist6.Service.UserService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import java.util.Arrays;
//import java.util.List;
//
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@ExtendWith(MockitoExtension.class)
//public class UserControllerTest {
//
//    @Mock
//    private UserService userService;
//
//    @InjectMocks
//    private UserController userController;
//
//    private MockMvc mockMvc;
//
//    @BeforeEach
//    public void setup() {
//        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
//    }
//
//    @Test
//    public void testGetUsers() throws Exception {
//        // Act & Assert
//        mockMvc.perform(get("/user"))
//                .andExpect(status().isOk())  // Status should be OK, not a redirect
//                .andExpect(view().name("user-list"));  // Ensure it matches the updated view name
//    }
//
//
//    @Test
//    void testShowCreateForm() throws Exception {
//        // Act & Assert
//        mockMvc.perform(get("/user/create"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("add-user"))  // Make sure this matches the view
//                .andExpect(model().attributeExists("user"));  // Ensure "user" model attribute exists
//    }
//
//    @Test
//    void testAddUser() throws Exception {
//        // Arrange
//        User user = new User("Alice Johnson", "alice@example.com", "password789", 0);
//
//        // Act & Assert
//        mockMvc.perform(post("/user/create")
//                        .param("userName", user.getUserName())
//                        .param("userEmail", user.getUserEmail())
//                        .param("userPassword", user.getUserPassword()))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(view().name("redirect:/user"));  // Ensure correct redirect URL
//    }
//
//    @Test
//    void testDeleteUser() throws Exception {
//        // Arrange
//        int userId = 1;
//
//        // Act & Assert
//        mockMvc.perform(get("/user/delete/{id}", userId))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(view().name("redirect:/user"));  // Ensure redirect after deletion
//    }
//
//    @Test
//    void testShowEditUserForm() throws Exception {
//        // Arrange
//        int userId = 1;
//        User user = new User("John Doe", "john@example.com", "password123", userId);
//        when(userService.getUserById(userId)).thenReturn(user);
//
//        // Act & Assert
//        mockMvc.perform(get("/user/{id}/edit", userId))
//                .andExpect(status().isOk())
//                .andExpect(view().name("edit-user"))  // Ensure this is the correct view
//                .andExpect(model().attribute("user", user));  // Ensure the correct user is in the model
//    }
//
//    @Test
//    void testUpdateUser() throws Exception {
//        // Arrange
//        int userId = 1;
//        User user = new User("Updated Name", "updated@example.com", "newpassword", userId);
//
//        // Act & Assert
//        mockMvc.perform(post("/user/{id}/edit", userId)
//                        .param("userName", user.getUserName())
//                        .param("userEmail", user.getUserEmail())
//                        .param("userPassword", user.getUserPassword()))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(view().name("redirect:/user"));  // Ensure the correct redirect
//    }
//}
