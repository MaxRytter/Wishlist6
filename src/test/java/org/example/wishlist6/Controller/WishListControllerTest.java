//
package org.example.wishlist6.Controller;

import org.example.wishlist6.Module.Wishlist;
import org.example.wishlist6.Module.Wishitem;
import org.example.wishlist6.Service.WishListService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class WishListControllerTest {

    @Mock
    private WishListService wishListService;

    @InjectMocks
    private WishListController wishListController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(wishListController).build();
    }

    @Test
    public void testGetAllWishlists() throws Exception {
        // Arrange: Mocked data for the service call
        List<Wishlist> wishlists = Arrays.asList(
                new Wishlist("Wishlist 1", 1),
                new Wishlist("Wishlist 2", 2)
        );

        // Mock the service call to return the mocked wishlists
        when(wishListService.getAllWishlists()).thenReturn(wishlists);

        // Act: Perform the GET request to the /wishlist endpoint
        mockMvc.perform(get("/wishlist"))

                // Assert: Verify the status, view name, and model attributes
                .andExpect(status().isOk()) // Status is 200 OK
                .andExpect(view().name("wishlist")) // View name is "wishlist"
                .andExpect(model().attributeExists("wishlists")) // "wishlists" attribute exists in model
                .andExpect(model().attribute("wishlists", wishlists)); // Check if the model contains the correct wishlists
    }
    @Test
    public void testShowCreateWishlistForm() throws Exception {
        mockMvc.perform(get("/wishlist/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("add-wishlist"))
                .andExpect(model().attributeExists("wishlist"));
    }

    @Test
    public void testAddWishlist() throws Exception {
        Wishlist wishlist = new Wishlist("New Wishlist", 1);

        mockMvc.perform(post("/wishlist/create")
                        .param("wishListName", wishlist.getWishListName())
                        .param("wishListDescription", "Description of the wishlist"))

                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/wishlist"));
    }

    @Test
    public void testViewWishlist() throws Exception {
        Wishlist wishlist = new Wishlist("Wishlist 1", 1);
        List<Wishitem> wishitems = List.of(new Wishitem("Wish 1", "Description", "url", 1));

        when(wishListService.getWishlistById(1)).thenReturn(wishlist);
        when(wishListService.getWishesByWishlistId(1)).thenReturn(wishitems);

        mockMvc.perform(get("/wishlist/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("view-wishlist"))
                .andExpect(model().attribute("wishlist", wishlist))
                .andExpect(model().attribute("wishes", wishitems));
    }

    @Test
    public void testDeleteWishlist() throws Exception {
        mockMvc.perform(get("/wishlist/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/wishlist"));
    }
}
