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
        // Arrange: No specific data needed for form rendering.
        // Act: Perform GET request to show wishlist creation form.
        mockMvc.perform(get("/wishlist/create"))
                // Assert: Verify status, view name, and model attribute.
                .andExpect(status().isOk())
                .andExpect(view().name("add-wishlist"))
                .andExpect(model().attributeExists("wishlist"));
    }

    @Test
    public void testAddWishlist() throws Exception {
        // Arrange: Setup wishlist name.
        String wishListName = "New Wishlist";
        // Act: Perform POST request to create new wishlist.
        mockMvc.perform(post("/wishlist/create")
                        .param("wishListName", wishListName))
                // Assert: Verify redirection after successful creation.
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/wishlist"));
    }

    @Test
    public void testViewWishlist() throws Exception {
        // Arrange: Mock wishlist and its wish items.
        int wishlistId = 1;
        Wishlist wishlist = new Wishlist("Wishlist 1", wishlistId, 1);
        List<Wishitem> wishitems = List.of(new Wishitem("Wish 1", "Description", "url", wishlistId));

        when(wishListService.getWishlistById(wishlistId)).thenReturn(wishlist);
        when(wishListService.getWishesByWishlistId(wishlistId)).thenReturn(wishitems);

        // Act: Perform GET request to view specific wishlist.
        mockMvc.perform(get("/wishlist/{id}", wishlistId))
                // Assert: Verify status, view name, and model attributes.
                .andExpect(status().isOk())
                .andExpect(view().name("view-wishlist"))
                .andExpect(model().attribute("wishlist", wishlist))
                .andExpect(model().attribute("wishes", wishitems));
    }

    @Test
    public void testDeleteWishlist() throws Exception {
        // Arrange: Set wishlist ID to delete.
        int wishlistId = 1;
        // Act: Perform GET request to delete wishlist.
        mockMvc.perform(get("/wishlist/delete/{id}", wishlistId))
                // Assert: Verify redirection after deletion.
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/wishlist"));

        // Assert: Verify delete method was called.
        verify(wishListService, times(1)).deleteWishlistById(wishlistId);
    }

    @Test
    public void testDeleteWish() throws Exception {
        // Arrange: Set wishlist ID and wish ID to delete.
        int wishlistId = 1;
        int wishId = 1;
        // Act: Perform GET request to delete a specific wish.
        mockMvc.perform(get("/wishlist/{wishlistId}/delete-wish/{wishId}", wishlistId, wishId))
                // Assert: Verify redirection after deletion.
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/wishlist/" + wishlistId));

        // Assert: Verify delete method was called.
        verify(wishListService, times(1)).deleteWishById(wishId);
    }

    @Test
    public void testEditWish() throws Exception {
        // Arrange: Mock wish data for editing.
        int wishlistId = 1;
        int wishId = 1;
        Wishitem wish = new Wishitem("Wish 1", "Description", "url", wishlistId);
        when(wishListService.getWishById(wishId)).thenReturn(wish);

        // Act: Perform GET request to edit a specific wish.
        mockMvc.perform(get("/wishlist/{wishlistId}/edit-wish/{wishId}", wishlistId, wishId))
                // Assert: Verify status, view name, and model attributes.
                .andExpect(status().isOk())
                .andExpect(view().name("edit-wish"))
                .andExpect(model().attribute("wish", wish))
                .andExpect(model().attribute("wishlistId", wishlistId));
    }

    @Test
    public void testUpdateWish() throws Exception {
        // Arrange: Set up wish data for updating.
        int wishlistId = 1;
        int wishId = 1;
        Wishitem wish = new Wishitem("Updated Wish", "Updated Description", "url", wishlistId);

        // Act: Perform POST request to update the wish.
        mockMvc.perform(post("/wishlist/{wishlistId}/edit-wish/{wishId}", wishlistId, wishId)
                        .flashAttr("wish", wish))
                // Assert: Verify redirection after update.
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/wishlist/" + wishlistId));

        // Assert: Verify update method was called.
        verify(wishListService, times(1)).updateWishItem(wish);
    }

    @Test
    public void testEditWishlist() throws Exception {
        // Arrange: Mock wishlist data for editing.
        int wishlistId = 1;
        Wishlist wishlist = new Wishlist("Wishlist 1", wishlistId, 1);
        when(wishListService.getWishlistById(wishlistId)).thenReturn(wishlist);

        // Act: Perform GET request to edit specific wishlist.
        mockMvc.perform(get("/wishlist/{id}/edit", wishlistId))
                // Assert: Verify status, view name, and model attributes.
                .andExpect(status().isOk())
                .andExpect(view().name("edit-wishlist"))
                .andExpect(model().attribute("wishlist", wishlist))
                .andExpect(model().attribute("wishlistId", wishlistId));
    }

    @Test
    public void testUpdateWishlist() throws Exception {
        // Arrange: Set up wishlist data for updating.
        int wishlistId = 1;
        Wishlist wishlist = new Wishlist("Updated Wishlist", wishlistId, 1);

        // Act: Perform POST request to update the wishlist.
        mockMvc.perform(post("/wishlist/{id}/edit", wishlistId)
                        .flashAttr("wishlist", wishlist))
                // Assert: Verify redirection after update.
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/wishlist"));

        // Assert: Verify update method was called.
        verify(wishListService, times(1)).updateWishlist(wishlist);
    }
}
