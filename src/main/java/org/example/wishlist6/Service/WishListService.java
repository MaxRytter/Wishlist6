package org.example.wishlist6.Service;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.example.wishlist6.Rowmappers.WishlistRowMapper;
import org.example.wishlist6.Module.Wishitem;
import org.example.wishlist6.Module.Wishlist;
import org.example.wishlist6.Repository.WishListRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@org.springframework.stereotype.Service
 public class WishListService {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    public WishListRepository wishlistRepository;

    public void addWishlist(Wishlist wishlist, int userId) {
        wishlist.setUserId(userId);
        wishlistRepository.addWishlist(wishlist);
    }

    public void addWish(String wishlistName, String wishItemName, String wishItemDescription) {
    }

    /**
    public List<Wishlist> getAllWishlists() {
        return wishlistRepository.getAllWishlists();
    }
**/

public List<Wishlist> getWishlistsByUserId(int userId) {
    String sql = "SELECT * FROM wishlist WHERE user_id = ?";
    RowMapper<Wishlist> rowMapper = new WishlistRowMapper();
    return jdbcTemplate.query(sql, new Object[]{userId}, rowMapper);
}


    public void updateWishlist(Wishlist wishlist) {
        wishlistRepository.updateWishlist(wishlist);
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
    public void deleteWishById(int wishId) {
        wishlistRepository.deleteWishById(wishId);
    }
    public Wishitem getWishById(int wishId) {
        return wishlistRepository.findWishById(wishId);
    }


    public void updateWishItem(Wishitem wish) {
        wishlistRepository.updateWishInfo(wish);
    }




}
