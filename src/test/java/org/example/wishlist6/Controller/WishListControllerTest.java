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
        List<Wishlist> wishlists = Arrays.asList(
                new Wishlist("Wishlist 1", 1),
                new Wishlist("Wishlist 2", 2)
        );
        when(wishListService.getAllWishlists()).thenReturn(wishlists);

        mockMvc.perform(get("/wishlist"))
                .andExpect(status().isOk())
                .andExpect(view().name("wishlist"))
                .andExpect(model().attributeExists("wishlists"))
                .andExpect(model().attribute("wishlists", wishlists));
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
                        .param("wishListName", wishlist.getWishListName()))
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
