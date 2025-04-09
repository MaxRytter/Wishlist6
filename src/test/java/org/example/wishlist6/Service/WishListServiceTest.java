//package org.example.wishlist6.Service;
//
//import org.example.wishlist6.Module.Wishitem;
//import org.example.wishlist6.Module.Wishlist;
//import org.example.wishlist6.Repository.WishListRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.util.List;
//
//import static org.mockito.Mockito.*;
//
//public class WishListServiceTest {
//
//    @Mock
//    private WishListRepository wishListRepository;
//
//    @InjectMocks
//    private WishListService wishListService;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    public void testAddWishlist() {
//        // Arrange
//        Wishlist wishlist = new Wishlist("New Wishlist", 1);
//        when(wishListRepository.addWishlist(wishlist)).thenReturn(1);
//
//        // Act
//        wishListService.addWishlist(wishlist);
//
//        // Assert
//        verify(wishListRepository, times(1)).addWishlist(wishlist);
//    }
//
//    @Test
//    public void testGetAllWishlists() {
//        // Arrange
//        List<Wishlist> expectedWishlists = List.of(
//                new Wishlist("Wishlist 1", 1),
//                new Wishlist("Wishlist 2", 2)
//        );
//        when(wishListRepository.getAllWishlists()).thenReturn(expectedWishlists);
//
//        // Act
//        List<Wishlist> wishlists = wishListService.getAllWishlists();
//
//        // Assert
//        verify(wishListRepository, times(1)).getAllWishlists();
//        assert(wishlists.equals(expectedWishlists));
//    }
//
//    @Test
//    public void testSaveWishToWishlist() {
//        // Arrange
//        Wishitem wish = new Wishitem("Wish Item", "Description", "url", 1);
//
//        // Act
//        wishListService.saveWishToWishlist(1, wish);
//
//        // Assert
//        verify(wishListRepository, times(1)).saveWish(wish);
//    }
//
//    @Test
//    public void testDeleteWishlistById() {
//        // Arrange
//        int wishlistId = 1;
//
//        // Act
//        wishListService.deleteWishlistById(wishlistId);
//
//        // Assert
//        verify(wishListRepository, times(1)).deleteWishlistById(wishlistId);
//    }
//}
