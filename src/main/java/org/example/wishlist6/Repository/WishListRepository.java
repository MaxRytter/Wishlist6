package org.example.wishlist6.Repository;

import org.example.wishlist6.Module.User;
import org.example.wishlist6.Module.Wishitem;
import org.example.wishlist6.Module.Wishlist;
import org.example.wishlist6.Rowmappers.WishlistRowMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class WishListRepository {

    private final JdbcTemplate jdbcTemplate;

    public WishListRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public List<Wishlist> getAllWishlists() {
        String sql = "SELECT * FROM wishlist";

        RowMapper<Wishlist> rowMapper = new WishlistRowMapper();

        return jdbcTemplate.query(sql, rowMapper);
    }
    public int addWishlist(Wishlist wishlist) {
        String sql = "INSERT INTO wishlist (wishlist_name) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, wishlist.getWishListName());
            return ps;
        }, keyHolder);
        return keyHolder.getKey().intValue();
    }


    public void saveWishlist(Wishlist wishlist) {
        String sql = "INSERT INTO wishlist (wishlist_name VALUES (?)";
        jdbcTemplate.update(sql, wishlist.getWishListName());
    }
    public void addWish(String wishlistName, String wishItemName, String wishItemDesc) {
        String sql = "INSERT INTO wishlist (wishlist_name, wish_item_name, wish_item_desc) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, wishlistName, wishItemName, wishItemDesc);
    }
    public void saveUser(User user) {
        String sql = "INSERT INTO users (user_name, user_email, user_password) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, user.getUserName(), user.getUserEmail(), user.getUserPassword());
        // evt print her "bruger registreret med email: " + user.getUserEmail()
    }
    public Wishitem getWishById(int id) {
        String sql = "SELECT * FROM wishitems WHERE wish_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{id}, new RowMapper<Wishitem>() {
                @Override
                public Wishitem mapRow(java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
                    Wishitem wishItem = new Wishitem();
                    wishItem.setWishItemName(rs.getString("wishItemName"));
                    wishItem.setWishItemDescription(rs.getString("wishItemDescription"));
                    wishItem.setWishItemPrice(rs.getDouble("wishItemPrice"));
                    wishItem.setLinkToStore(rs.getString("linkToStore"));
                    return wishItem;
                }
            });
        } catch (EmptyResultDataAccessException e) {
            //Error handling
            System.out.println("Wishitem with ID " + id + " not found.");
            return null;
        }
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