package org.example.wishlist6.Repository;

import org.example.wishlist6.Module.User;
import org.example.wishlist6.Module.Wishitem;
import org.example.wishlist6.Module.Wishlist;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class WishlistRepository {

    private final JdbcTemplate jdbcTemplate;

    public WishlistRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Wishlist wishlist) {
        String sql = "INSERT INTO wishlist (wishListName) VALUES (?)";
        jdbcTemplate.update(sql, wishlist.getWishListName());
    }
    public void addWish(String wishlistName, String wishItemName, String wishItemDesc) {
        String sql = "INSERT INTO wishlist (wishlist_name, wish_item_name, wish_item_desc) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, wishlistName, wishItemName, wishItemDesc);
    }
    public void save(User user) {
        String sql = "INSERT INTO users (userName, userEmail, passwordHash) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, user.getUserName(), user.getUserEmail(), user.getPasswordHash());
        // evt print her "bruger registreret med email: " + user.getUserEmail()
    }
    public Wishitem getWishById(int id) {
        String sql = "SELECT * FROM wishitems WHERE wishItemID = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new RowMapper<Wishitem>() {
            @Override
            public Wishitem mapRow(java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
                Wishitem wishItem = new Wishitem();
                wishItem.setWishItemID(rs.getInt("wishItemID"));
                wishItem.setWishItemName(rs.getString("wishItemName"));
                wishItem.setWishItemDescription(rs.getString("wishItemDescription"));
                wishItem.setWishItemPrice(rs.getDouble("wishItemPrice"));
                wishItem.setLinkToStore(rs.getString("linkToStore"));
                return wishItem;
            }
        });
    }

    public void updateWish(int id, Wishitem wishItem) {
        String sql = "UPDATE wishitems SET wishItemName = ?, wishItemDescription = ?, wishItemPrice = ?, linkToStore = ? WHERE wishItemID = ?";
        jdbcTemplate.update(sql, wishItem.getWishItemName(), wishItem.getWishItemDescription(),
                wishItem.getWishItemPrice(), wishItem.getLinkToStore(), id);
    }


    public void removeWish(int id) {
        String sql = "DELETE FROM wishitems WHERE wishItemID = ?";
        jdbcTemplate.update(sql, id);
    }
}