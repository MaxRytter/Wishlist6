package org.example.wishlist6.Controller;

import org.example.wishlist6.Module.Wishitem;
import org.example.wishlist6.Service.WishListService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.http.MediaType;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class WishListRestControllerTest {

    @Mock
    private WishListService wishListService;

    @InjectMocks
    private WishListRestController wishListRestController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(wishListRestController).build();
    }

    @Test
    void testAddWish() throws Exception {
        // Arrange
        String wishlistName = "My Wishlist";
        String wishItemName = "New Phone";
        String wishItemDescription = "The latest model of phone";

        // Act & Assert
        mockMvc.perform(post("/api/wishlist/add")
                        .param("wishlistName", wishlistName)
                        .param("wishItemName", wishItemName)
                        .param("wishItemDescription", wishItemDescription))
                .andExpect(status().isOk())
                .andExpect(content().string("Wish added successfully!"));

        // Verify the interaction with the service
        verify(wishListService, times(1)).addWish(wishlistName, wishItemName, wishItemDescription);
    }

    @Test
    void testUpdateWishItem() throws Exception {
        // Arrange
        int wishItemId = 1;
        Wishitem wishItem = new Wishitem("Updated Wish", "Updated description", "http://updated.url", 1);

        // Act & Assert
        mockMvc.perform(put("/api/wishlist/wishlist/update/{id}", wishItemId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"wishItemName\": \"Updated Wish\", \"wishItemDescription\": \"Updated description\", \"wishUrl\": \"http://updated.url\", \"wishlistId\": 1}"))
                .andExpect(status().isOk());

        // Verify the interaction with the service
        verify(wishListService, times(1)).updateWish(wishItemId, wishItem);
    }
}
