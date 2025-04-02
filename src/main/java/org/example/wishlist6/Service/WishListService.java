package org.example.wishlist6.Service;

import org.example.wishlist6.Module.Wishlist;
import org.example.wishlist6.Repository.WishListRepository;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Service
 public class WishListService {

    @Autowired
    public WishListRepository wishlistRepository;

    public void addWishlist(Wishlist wishlist) {
        wishlistRepository.save(wishlist);
    }
}
