package org.example.wishlist6.Repository;

import org.example.wishlist6.Module.User;
import org.example.wishlist6.Module.Wishitem;
import org.example.wishlist6.Module.Wishlist;
import org.example.wishlist6.Rowmappers.WishlistRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Repository
public class WishlistRepository {

    private final JdbcTemplate jdbcTemplate;

    public WishlistRepository(JdbcTemplate jdbcTemplate) {
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
        String sql = "INSERT INTO wishlist (wishList_name VALUES (?)";
        jdbcTemplate.update(sql, wishlist.getWishListName());
    }
    public void saveWish(int wishlistId, Wishitem wishItem) {
        String sql = "INSERT INTO wish (wish_name, wish_description, wish_url, /* wish_price,*/ wishlist_id) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                wishItem.getWishItemName(),
                wishItem.getWishItemDescription(),
                wishItem.getWishUrl(),
                // wishItem.getWishItemPrice(),
                wishlistId);
    }

    public void saveUser(User user) {
        String sql = "INSERT INTO users (user_name, user_email, user_password) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, user.getUserName(), user.getUserEmail(), user.getUserPassword());
        // evt print her "bruger registreret med email: " + user.getUserEmail()
    }
    public Wishitem getWishById(int id) {
        String sql = "SELECT * FROM wishitems WHERE wish_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new RowMapper<Wishitem>() {
            @Override
            public Wishitem mapRow(ResultSet rs, int rowNum) throws SQLException {
                Wishitem wishItem = new Wishitem();
                wishItem.setWishItemId((rs.getInt("wishItemID")));
                wishItem.setWishItemName(rs.getString("wishItemName"));
                wishItem.setWishItemDescription(rs.getString("wishItemDescription"));
                wishItem.setWishItemPrice(rs.getDouble("wishItemPrice"));
                wishItem.setWishUrl(rs.getString("linkToStore"));
                return wishItem;
            }
        });
    }

    public void updateWish(int id, Wishitem wishItem) {
        String sql = "UPDATE wishitems SET wishItemName = ?, wishItemDescription = ?, wishItemPrice = ?, linkToStore = ? WHERE wishItemID = ?";
        jdbcTemplate.update(sql, wishItem.getWishItemName(), wishItem.getWishItemDescription(),
                wishItem.getWishItemPrice(), wishItem.getWishUrl(), id);
    }


    public void removeWish(int id) {
        String sql = "DELETE FROM wishitems WHERE wishItemID = ?";
        jdbcTemplate.update(sql, id);
    }


    public Wishlist getWishlistById(int id) {
        String sql = "SELECT * FROM wishlist WHERE wishlist_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new WishlistRowMapper());
    }

    public List<Wishitem> getWishesByWishlistId(int wishlistId) {
        String sql = "SELECT * FROM wish WHERE wishlist_id = ?";
        return jdbcTemplate.query(sql, new Object[]{wishlistId}, new RowMapper<Wishitem>() {
            @Override
            public Wishitem mapRow(ResultSet rs, int rowNum) throws SQLException {
                Wishitem item = new Wishitem();
                item.setWishItemId(rs.getInt("wish_id"));
                item.setWishItemName(rs.getString("wish_name"));
                item.setWishItemDescription(rs.getString("wish_description"));
               // item.setWishItemPrice(rs.getDouble("wish_price"));
                item.setWishUrl(rs.getString("wish_url"));
                return item;
            }
        });
    }
}