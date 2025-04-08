package org.example.wishlist6.Repository;

import org.example.wishlist6.Module.Wishitem;
import org.example.wishlist6.Module.Wishlist;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

public class WishListRepositoryTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private WishListRepository wishListRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveWish() {
        // Arrange
        Wishitem wishitem = new Wishitem("Wish Item 1", "Description", "url", 1);

        // Act
        wishListRepository.saveWish(wishitem);

        // Assert
        verify(jdbcTemplate, times(1)).update(
                anyString(), // Match any string (SQL query)
                any(),       // Match any object for wish_name
                any(),       // Match any object for wish_description
                any(),       // Match any object for wish_url
                any()        // Match any object for wishlist_id
        );
    }

    @Test
    public void testDeleteWishlistById() {
        // Arrange
        int wishlistId = 1;

        // Act
        wishListRepository.deleteWishlistById(wishlistId);

        // Assert
        verify(jdbcTemplate, times(1)).update(
                anyString(), // Match any string (SQL query)
                eq(wishlistId) // Match the wishlistId specifically
        );
    }

    @Test
    public void testGetWishlistById() {
        // Arrange
        int wishlistId = 1;
        Wishlist wishlist = new Wishlist("Wishlist 1", wishlistId);

        // Act
        when(jdbcTemplate.queryForObject(
                anyString(),
                any(Object[].class),
                (RowMapper<Object>) any()
        )).thenReturn(wishlist);

        Wishlist result = wishListRepository.getWishlistById(wishlistId);

        // Assert
        verify(jdbcTemplate, times(1)).queryForObject(
                anyString(),
                any(Object[].class),
                (RowMapper<Object>) any()
        );
        assert(result != null);
        assert(result.getWishListName().equals("Wishlist 1"));
    }

    @Test
    public void testGetWishesByWishlistId() {
        // Arrange
        int wishlistId = 1;
        Wishitem wishitem = new Wishitem("Wish Item 1", "Description", "url", wishlistId);
        when(jdbcTemplate.query(
                anyString(),
                any(Object[].class),
                (ResultSetExtractor<Object>) any()
        )).thenReturn(List.of(wishitem));

        // Act
        List<Wishitem> result = wishListRepository.getWishesByWishlistId(wishlistId);

        // Assert
        verify(jdbcTemplate, times(1)).query(
                anyString(),
                any(Object[].class),
                (ResultSetExtractor<Object>) any()
        );
        assert(result.size() == 1);
        assert(result.get(0).getWishItemName().equals("Wish Item 1"));
    }

    @Test
    public void testDeleteWishById() {
        // Arrange
        int wishId = 1;

        // Act
        wishListRepository.deleteWishById(wishId);

        // Assert
        verify(jdbcTemplate, times(1)).update(
                anyString(), // Match any string (SQL query)
                eq(wishId) // Match the wishId specifically
        );
    }

    @Test
    public void testUpdateWishInfo() {
        // Arrange
        Wishitem wishitem = new Wishitem("Updated Wish", "Updated Description", "updated_url", 1);
        wishitem.setWishItemId(1);

        // Act
        wishListRepository.updateWishInfo(wishitem);

        // Assert
        verify(jdbcTemplate, times(1)).update(
                anyString(), // Match any string (SQL query)
                eq(wishitem.getWishItemName()), // Match the wish_name
                eq(wishitem.getWishItemDescription()), // Match the wish_description
                eq(wishitem.getWishUrl()), // Match the wish_url
                eq(wishitem.getWishItemId()) // Match the wish_id
        );
    }

    @Test
    public void testUpdateWishlist() {
        // Arrange
        Wishlist wishlist = new Wishlist("Updated Wishlist", 1);

        // Act
        wishListRepository.updateWishlist(wishlist);

        // Assert
        verify(jdbcTemplate, times(1)).update(
                anyString(), // Match any string (SQL query)
                eq(wishlist.getWishListName()), // Match the wishlist_name
                eq(wishlist.getWishListID()) // Match the wishlist_id
        );
    }

}
