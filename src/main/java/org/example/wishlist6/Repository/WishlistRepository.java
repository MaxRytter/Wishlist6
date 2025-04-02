package org.example.wishlist6.Repository;

import org.example.wishlist6.Module.User;
import org.example.wishlist6.Module.Wishlist;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class WishlistRepository {

    private final JdbcTemplate jdbcTemplate;

    public WishlistRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(User user) {
        String sql = "INSERT INTO users (userName, userEmail, passwordHash) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, user.getUserName(), user.getUserEmail(), user.getPasswordHash());
        // evt print her "bruger registreret med email: " + user.getUserEmail()
    }

    public void save(Wishlist wishlist) {
        String sql = "INSERT INTO wishlist (wishListName) VALUES (?)";
        jdbcTemplate.update(sql, wishlist.getWishListName());
    }
    public void addWish(String wishlistName, String wishItemName, String wishItemDesc) {
        String sql = "INSERT INTO wishlist (wishlist_name, wish_item_name, wish_item_desc) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, wishlistName, wishItemName, wishItemDesc);
    }



}
