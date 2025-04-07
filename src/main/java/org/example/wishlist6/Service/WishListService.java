package org.example.wishlist6.Service;

import org.example.wishlist6.Module.Wishitem;
import org.example.wishlist6.Module.Wishlist;
import org.example.wishlist6.Repository.WishListRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@org.springframework.stereotype.Service
 public class WishListService {

    @Autowired
    public WishListRepository wishlistRepository;

    public void addWishlist(Wishlist wishlist) {
        wishlistRepository.save(wishlist);
    }

    public void addWish(String wishlistName, String wishItemName, String wishItemDescription) {
    }

    public List<Wishlist> getAllWishlists() {
        return wishlistRepository.findAll();
    }

    public void updateWish(int id, Wishitem wishItem) {
    }

    public void saveWishToWishlist(int wishlistId, Wishitem wish) {
        wishlistRepository.saveWish(wishlistId, wish);
    }

}
