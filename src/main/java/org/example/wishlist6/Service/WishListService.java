package org.example.wishlist6.Service;

import org.example.wishlist6.Module.Wishlist;
import org.example.wishlist6.Module.Wishitem;
import org.example.wishlist6.Repository.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WishListService {

    @Autowired
    private WishlistRepository wishlistRepository;


    public void addWishlist(Wishlist wishlist) {
        wishlistRepository.save(wishlist);
    }

    public Wishitem getWishById(int id) {
        return wishlistRepository.getWishById(id);
    }

    public void addWish(String wishlistName, String wishItemName, String wishItemDesc) {
    }

    public void updateWish(int id, Wishitem wishItem) {
        wishlistRepository.updateWish(id, wishItem);
    }

    public void removeWish(int id) {
        wishlistRepository.removeWish(id);
    }


}