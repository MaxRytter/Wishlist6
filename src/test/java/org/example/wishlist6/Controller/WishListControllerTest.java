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
    public void testShowCreateWishlistForm() throws Exception {
        // Arrange: No specific data to arrange here, the form is being tested.

        // Act: Perform the GET request to the wishlist creation page.
        mockMvc.perform(get("/wishlist/create"))
                // Assert: Verify the response status, view name, and that the model contains "wishlist".
                .andExpect(status().isOk())
                .andExpect(view().name("add-wishlist"))
                .andExpect(model().attributeExists("wishlist"));
    }

    @Test
    public void testAddWishlist() throws Exception {
        // Arrange: Set up the wishlist data.
        String wishListName = "New Wishlist";

        // Act: Perform the POST request to create a new wishlist.
        mockMvc.perform(post("/wishlist/create")
                        .param("wishListName", wishListName))
                // Assert: Verify the redirection and that the URL is correct.
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/wishlist"));
    }

    @Test
    public void testViewWishlist() throws Exception {
        // Arrange: Mock the wishlist and its associated wish items.
        int wishlistId = 1;
        Wishlist wishlist = new Wishlist("Wishlist 1", wishlistId, 1);
        List<Wishitem> wishitems = List.of(new Wishitem("Wish 1", "Description", "url", wishlistId));

        when(wishListService.getWishlistById(wishlistId)).thenReturn(wishlist);
        when(wishListService.getWishesByWishlistId(wishlistId)).thenReturn(wishitems);

        // Act: Perform the GET request to view the specific wishlist.
        mockMvc.perform(get("/wishlist/{id}", wishlistId))
                // Assert: Verify the response status, view name, and the model contains "wishlist" and "wishes".
                .andExpect(status().isOk())
                .andExpect(view().name("view-wishlist"))
                .andExpect(model().attribute("wishlist", wishlist))
                .andExpect(model().attribute("wishes", wishitems));
    }

    @Test
    public void testDeleteWishlist() throws Exception {
        // Arrange: Set up the wishlist ID for deletion.
        int wishlistId = 1;

        // Act: Perform the GET request to delete the wishlist.
        mockMvc.perform(get("/wishlist/delete/{id}", wishlistId))
                // Assert: Verify the redirection and that the URL is correct.
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/wishlist"));

        // Assert: Verify the delete method was called once on the service.
        verify(wishListService, times(1)).deleteWishlistById(wishlistId);
    }

    @Test
    public void testDeleteWish() throws Exception {
        // Arrange: Set up the wishlist ID and wish ID for deletion.
        int wishlistId = 1;
        int wishId = 1;

        // Act: Perform the GET request to delete the specific wish from the wishlist.
        mockMvc.perform(get("/wishlist/{wishlistId}/delete-wish/{wishId}", wishlistId, wishId))
                // Assert: Verify the redirection and the correct URL after the deletion.
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/wishlist/" + wishlistId));

        // Assert: Verify the delete method was called once on the service.
        verify(wishListService, times(1)).deleteWishById(wishId);
    }

    @Test
    public void testEditWish() throws Exception {
        // Arrange: Mock the wish item data for editing.
        int wishlistId = 1;
        int wishId = 1;
        Wishitem wish = new Wishitem("Wish 1", "Description", "url", wishlistId);
        when(wishListService.getWishById(wishId)).thenReturn(wish);

        // Act: Perform the GET request to edit a specific wish.
        mockMvc.perform(get("/wishlist/{wishlistId}/edit-wish/{wishId}", wishlistId, wishId))
                // Assert: Verify the response status, view name, and model attributes.
                .andExpect(status().isOk())
                .andExpect(view().name("edit-wish"))
                .andExpect(model().attribute("wish", wish))
                .andExpect(model().attribute("wishlistId", wishlistId));
    }

    @Test
    public void testUpdateWish() throws Exception {
        // Arrange: Set up the wish data for updating.
        int wishlistId = 1;
        int wishId = 1;
        Wishitem wish = new Wishitem("Updated Wish", "Updated Description", "url", wishlistId);

        // Act: Perform the POST request to update the wish.
        mockMvc.perform(post("/wishlist/{wishlistId}/edit-wish/{wishId}", wishlistId, wishId)
                        .flashAttr("wish", wish))
                // Assert: Verify the redirection and the updated URL.
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/wishlist/" + wishlistId));

        // Assert: Verify the update method was called once on the service.
        verify(wishListService, times(1)).updateWishItem(wish);
    }

    @Test
    public void testEditWishlist() throws Exception {
        // Arrange: Mock the wishlist data for editing.
        int wishlistId = 1;
        Wishlist wishlist = new Wishlist("Wishlist 1", wishlistId, 1);
        when(wishListService.getWishlistById(wishlistId)).thenReturn(wishlist);

        // Act: Perform the GET request to edit the specific wishlist.
        mockMvc.perform(get("/wishlist/{id}/edit", wishlistId))
                // Assert: Verify the response status, view name, and model attributes.
                .andExpect(status().isOk())
                .andExpect(view().name("edit-wishlist"))
                .andExpect(model().attribute("wishlist", wishlist))
                .andExpect(model().attribute("wishlistId", wishlistId));
    }

    @Test
    public void testUpdateWishlist() throws Exception {
        // Arrange: Set up the wishlist data for updating.
        int wishlistId = 1;
        Wishlist wishlist = new Wishlist("Updated Wishlist", wishlistId, 1);

        // Act: Perform the POST request to update the wishlist.
        mockMvc.perform(post("/wishlist/{id}/edit", wishlistId)
                        .flashAttr("wishlist", wishlist))
                // Assert: Verify the redirection and the updated URL.
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/wishlist"));

        // Assert: Verify the update method was called once on the service.
        verify(wishListService, times(1)).updateWishlist(wishlist);
    }
}
