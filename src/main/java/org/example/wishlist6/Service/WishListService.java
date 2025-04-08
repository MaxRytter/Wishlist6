package org.example.wishlist6.Service;

import org.example.wishlist6.Module.User;
import org.example.wishlist6.Module.Wishlist;
import org.example.wishlist6.Module.Wishitem;
import org.example.wishlist6.Repository.WishListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class WishListService {

    @Autowired
    private WishListRepository wishlistRepository;


    public void addWishlist(Wishlist wishlist) {
        wishlistRepository.addWishlist(wishlist);
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


    public List<Wishlist> getAllWishlists() {
        return wishlistRepository.getAllWishlists();
    }


    public void saveUser(User user) {
        wishlistRepository.saveUser(user);
    }

    public void saveWish(int wishlistId, Wishitem wishItem) {
        wishlistRepository.saveWish(wishlistId, wishItem);
    }


    public Wishlist getWishlistById(int id) {
        return wishlistRepository.getWishlistById(id);
    }

    public List<Wishitem> getWishesByWishlistId(int wishlistId) {
        return wishlistRepository.getWishesByWishlistId(wishlistId);
    }

}
