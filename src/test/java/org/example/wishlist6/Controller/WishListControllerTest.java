package org.example.wishlist6.Controller;

import org.example.wishlist6.Module.Wishlist;
import org.example.wishlist6.Module.Wishitem;
import org.example.wishlist6.Service.WishListService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
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
        // Arrange: Create a mock user and set the URL.
        MvcResult result = mockMvc.perform(get("/wishlist/create")
                        .with(user("testuser").password("password").roles("USER")))
                .andReturn();

        // Act: Perform the request and capture the result.
        System.out.println("Response Status: " + result.getResponse().getStatus());
        System.out.println("Redirected to: " + result.getResponse().getHeader("Location"));

        // Assert: Validate the response (just log the output here).
    }

    @Test
    public void testAddWishlist() throws Exception {
        // Arrange: Set up wishlist name to be passed to the controller.
        String wishListName = "New Wishlist";

        // Act: Perform the POST request to create a new wishlist.
        mockMvc.perform(post("/wishlist/create")
                        .param("wishListName", wishListName))

                // Assert: Verify the redirection status after successful creation.
                .andExpect(status().is3xxRedirection())  // Expect a redirection
                .andExpect(redirectedUrl("/"));  // Expect redirection to the root URL ("/")
    }

    @Test
    public void testViewWishlist() throws Exception {
        // Arrange: Create mock wishlist and wish items.
        int wishlistId = 1;
        Wishlist wishlist = new Wishlist("Wishlist 1", wishlistId, 1);
        List<Wishitem> wishitems = List.of(new Wishitem("Wish 1", "Description", "url", wishlistId));

        when(wishListService.getWishlistById(wishlistId)).thenReturn(wishlist);
        when(wishListService.getWishesByWishlistId(wishlistId)).thenReturn(wishitems);

        // Act: Perform GET request to view the specific wishlist.
        mockMvc.perform(get("/wishlist/{id}", wishlistId))

                // Assert: Verify that the response status is 3xx redirection if it's a redirect.
                .andExpect(status().is3xxRedirection())  // Expect a redirect if needed

                // If it's a redirect, check the redirection URL.
                .andExpect(redirectedUrl("/login"));  // Assuming redirect goes to /login (adjust based on your app logic)
    }

    @Test
    public void testDeleteWishlist() throws Exception {
        // Arrange: Set up the wishlist ID to delete.
        int wishlistId = 1;

        // Act: Perform POST request to delete the wishlist.
        mockMvc.perform(post("/wishlist/delete/{id}", wishlistId))

                // Assert: Verify that the response is a redirection after deletion.
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/wishlist"));

        // Assert: Ensure that the delete method was invoked once for the given wishlistId.
        verify(wishListService, times(1)).deleteWishlistById(wishlistId);
    }

    @Test
    public void testDeleteWish() throws Exception {
        // Arrange: Set up wishlist ID and wish ID for deletion.
        int wishlistId = 1;
        int wishId = 1;

        // Act: Perform GET request to delete a specific wish.
        mockMvc.perform(get("/wishlist/{wishlistId}/delete-wish/{wishId}", wishlistId, wishId))

                // Assert: Verify that the response is a redirection after deletion.
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/wishlist/" + wishlistId));

        // Assert: Verify that the delete wish method was called once for the given wishId.
        verify(wishListService, times(1)).deleteWishById(wishId);
    }

    @Test
    public void testEditWish() throws Exception {
        // Arrange:
        // Create an authentication token for a mock user
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                "user", "password", AuthorityUtils.createAuthorityList("USER")
        );
        // Set the authentication context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Mock the WishListService to return a valid Wishitem
        Wishitem wish = new Wishitem("Wish 1", "Description", "url", 1);
        when(wishListService.getWishById(1)).thenReturn(wish);  // Mock the service to return this wish for the given ID.

        // Act:
        // Perform the GET request to edit the wish.
        MvcResult result = mockMvc.perform(get("/wishlist/1/edit-wish/1"))
                .andExpect(status().is3xxRedirection())  // Expect a redirection (302) if the user is not authorized
                .andExpect(redirectedUrl("/login"))  // Expect a redirect to the login page if the user is not authenticated
                .andReturn();

        // Assert:
        // Check if the response redirected correctly
        assertThat(result.getResponse().getStatus()).isEqualTo(302);
        assertThat(result.getResponse().getHeader("Location")).contains("/login");
    }

    @Test
    public void testUpdateWish() throws Exception {
        // Arrange: Create the initial wish that we will update
        int wishlistId = 1;
        int wishId = 1;
        Wishitem wish = new Wishitem("Updated Wish", "Updated Description", "url", wishlistId);

        // Mock the WishListService to do nothing when updateWishItem is called (since it's a void method)
        doNothing().when(wishListService).updateWishItem(any(Wishitem.class)); // Mocking void method

        // Act: Perform POST request to update the wish. Using flashAttr to pass the updated wish.
        MvcResult result = mockMvc.perform(post("/wishlist/{wishlistId}/edit-wish/{wishId}", wishlistId, wishId)
                        .flashAttr("wish", wish))  // Ensure the wish object is passed as a flash attribute
                .andExpect(status().is3xxRedirection())  // Expect redirection after the update
                .andExpect(redirectedUrl("/wishlist/" + wishlistId))  // Ensure it redirects to the updated wishlist
                .andReturn();

        // Assert: Verify the redirection is correct
        assertThat(result.getResponse().getStatus()).isEqualTo(302);  // Expecting a redirect status
        assertThat(result.getResponse().getHeader("Location")).contains("/wishlist/" + wishlistId);  // Verify the redirection URL

        // Capture the argument passed to updateWishItem() to verify it's the correct wish
        ArgumentCaptor<Wishitem> captor = ArgumentCaptor.forClass(Wishitem.class);
        verify(wishListService, times(1)).updateWishItem(captor.capture());  // Capture the argument

        // Verify that the captured wish item is the same as the one we expected
        Wishitem capturedWish = captor.getValue();
        assertThat(capturedWish.getWishItemName()).isEqualTo(wish.getWishItemName());  // Check the wish name
        assertThat(capturedWish.getWishItemDescription()).isEqualTo(wish.getWishItemDescription());  // Check description
        assertThat(capturedWish.getWishUrl()).isEqualTo(wish.getWishUrl());  // Check URL
        assertThat(capturedWish.getWishlistId()).isEqualTo(wish.getWishlistId());  // Check wishlist ID

        // Ensure the updated wish is actually being passed to the model.
        assertThat(Objects.requireNonNull(result.getModelAndView()).getModel().get("wish")).isEqualTo(wish);
    }


    @Test
    public void testEditWishlist() throws Exception {
        // Arrange: Create a mock wishlist data to be edited.
        int wishlistId = 1;
        Wishlist wishlist = new Wishlist("Wishlist 1", wishlistId, 1);
        when(wishListService.getWishlistById(wishlistId)).thenReturn(wishlist);

        // Act: Perform GET request to edit the specific wishlist.
        mockMvc.perform(get("/wishlist/{id}/edit", wishlistId))

                // Assert: Verify that the response status, view name, and model attributes are correct.
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

        // Act: Perform POST request to update the wishlist.
        mockMvc.perform(post("/wishlist/{id}/edit", wishlistId)
                        .flashAttr("wishlist", wishlist))

                // Assert: Verify that the response is a redirection after updating.
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/wishlist"));

        // Assert: Ensure that the update wishlist method was called once for the given wishlist.
        verify(wishListService, times(1)).updateWishlist(wishlist);
    }
}
