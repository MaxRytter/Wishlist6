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
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
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
        // Arrange: No specific arrangements required here for this test.

        // Act: Perform a GET request to the create wishlist page.
        mockMvc.perform(get("/wishlist/create"))
                .andExpect(status().isFound());
    }

    @Test
    public void testAddWishlist() throws Exception {
        // Arrange: Prepare the request with necessary parameters
        MockHttpServletRequestBuilder request = post("/add")
                .param("name", "Test Wishlist")  // Example parameter
                .param("description", "This is a test wishlist");

        // Act: Perform the POST request and capture the result
        mockMvc.perform(request)
                // Assert: Check for expected redirection (3xx)
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection()) // Expect redirection
                .andExpect(MockMvcResultMatchers.redirectedUrl("/wishlist")) // Adjust the expected URL
                .andReturn();
    }

    @Test
    public void testViewWishlist() throws Exception {
        // Arrange: Prepare a wishlist object and associated wish items.
        int wishlistId = 1;
        Wishlist wishlist = new Wishlist("Wishlist 1", wishlistId, 1);
        List<Wishitem> wishitems = List.of(new Wishitem("Wish 1", "Description", "url", wishlistId));

        // Mocking the service methods to return the data
        when(wishListService.getWishlistById(wishlistId)).thenReturn(wishlist);
        when(wishListService.getWishesByWishlistId(wishlistId)).thenReturn(wishitems);

        // Act: Perform a GET request to view the wishlist with the provided ID.
        mockMvc.perform(get("/wishlist/{id}", wishlistId))

                // Assert: Verify the response status is OK, the view name is "view-wishlist",
                // and the model contains the correct attributes.
                .andExpect(status().isOk()) // Expect status code 200
                .andExpect(view().name("view-wishlist")) // Expect view name to be "view-wishlist"
                .andExpect(model().attribute("wishlist", wishlist)) // Expect 'wishlist' model attribute
                .andExpect(model().attribute("wishes", wishitems)); // Expect 'wishes' model attribute
    }


    @Test
    public void testDeleteWishlist() throws Exception {
        // Arrange: Prepare the wishlist ID for deletion.
        int wishlistId = 1;

        // Act: Perform a GET request to delete the wishlist with the provided ID.
        mockMvc.perform(get("/wishlist/delete/{id}", wishlistId))

                // Assert: Verify the response status is a redirect, and it redirects to the "/wishlist" page.
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/wishlist"));

        // Verify that the deleteWishlistById method was called once.
        verify(wishListService, times(1)).deleteWishlistById(wishlistId);
    }

    @Test
    public void testDeleteWish() throws Exception {
        // Arrange: Prepare the wishlist ID and wish ID for deletion.
        int wishlistId = 1;
        int wishId = 1;

        // Act: Perform a GET request to delete the wish with the provided IDs.
        mockMvc.perform(get("/wishlist/{wishlistId}/delete-wish/{wishId}", wishlistId, wishId))

                // Assert: Verify the response status is a redirect, and it redirects to the wishlist view.
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/wishlist/" + wishlistId));

        // Verify that the deleteWishById method was called once.
        verify(wishListService, times(1)).deleteWishById(wishId);
    }

    @Test
    public void testEditWish() throws Exception {
        // Arrange: Prepare the wishlist ID, wish ID, and the wish to be edited.
        int wishlistId = 1;
        int wishId = 1;
        Wishitem wish = new Wishitem("Wish 1", "Description", "url", wishlistId);
        when(wishListService.getWishById(wishId)).thenReturn(wish);

        // Act: Perform a GET request to edit the wish with the provided IDs.
        mockMvc.perform(get("/wishlist/{wishlistId}/edit-wish/{wishId}", wishlistId, wishId))

                // Assert: Verify the response status is OK, the view name is "edit-wish",
                // and the model contains the correct wish and wishlist ID attributes.
                .andExpect(status().isOk())
                .andExpect(view().name("edit-wish"))
                .andExpect(model().attribute("wish", wish))
                .andExpect(model().attribute("wishlistId", wishlistId));
    }

    @Test
    public void testUpdateWish() throws Exception {
        // Arrange: Prepare the updated wish object.
        int wishlistId = 1;
        int wishId = 1;
        Wishitem wish = new Wishitem("Updated Wish", "Updated Description", "url", wishlistId);
        wish.setWishItemId(wishId); // Ensure the ID is set.

        // Act: Perform a POST request to update the wish with the provided data.
        mockMvc.perform(post("/wishlist/{wishlistId}/edit-wish/{wishId}", wishlistId, wishId)
                        .flashAttr("wish", wish))

                // Assert: Verify the response status is a redirect, and it redirects to the wishlist view.
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/wishlist/" + wishlistId));

        // Verify that the updateWishItem method was called once.
        verify(wishListService, times(1)).updateWishItem(wish);
    }

    @Test
    public void testEditWishlist() throws Exception {
        // Arrange: Prepare the wishlist ID and wishlist object to be edited.
        int wishlistId = 1;
        Wishlist wishlist = new Wishlist("Wishlist 1", wishlistId, 1);
        when(wishListService.getWishlistById(wishlistId)).thenReturn(wishlist);

        // Act: Perform a GET request to edit the wishlist with the provided ID.
        mockMvc.perform(get("/wishlist/{id}/edit", wishlistId))

                // Assert: Verify the response status is OK, the view name is "edit-wishlist",
                // and the model contains the correct wishlist and wishlist ID attributes.
                .andExpect(status().isOk())
                .andExpect(view().name("edit-wishlist"))
                .andExpect(model().attribute("wishlist", wishlist))
                .andExpect(model().attribute("wishlistId", wishlistId));
    }

    @Test
    public void testUpdateWishlist() throws Exception {
        // Arrange: Prepare the updated wishlist object.
        int wishlistId = 1;
        Wishlist wishlist = new Wishlist("Updated Wishlist", wishlistId, 1);

        // Act: Perform a POST request to update the wishlist with the provided data.
        mockMvc.perform(post("/wishlist/{id}/edit", wishlistId)
                        .flashAttr("wishlist", wishlist))

                // Assert: Verify the response status is a redirect, and it redirects to the "/wishlist" page.
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/wishlist"));

        // Verify that the updateWishlist method was called once.
        verify(wishListService, times(1)).updateWishlist(wishlist);
    }
}
