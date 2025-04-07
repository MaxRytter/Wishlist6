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
        wishlistRepository.addWishlist(wishlist);
    }

    public void addWish(String wishlistName, String wishItemName, String wishItemDescription) {
    }

    public List<Wishlist> getAllWishlists() {
        return wishlistRepository.getAllWishlists();
    }

    public void updateWish(int id, Wishitem wishItem) {
    }

    public void saveWishToWishlist(int wishlistId, Wishitem wish) {
        wish.setWishlistId(wishlistId);
        wishlistRepository.saveWish(wish);
    }
    public void deleteWishlistById(int id) {
        wishlistRepository.deleteWishlistById(id);
    }
    public Wishlist getWishlistById(int id) {
        return wishlistRepository.getWishlistById(id);
    }

    public List<Wishitem> getWishesByWishlistId(int wishlistId) {
        return wishlistRepository.getWishesByWishlistId(wishlistId);
    }

}
